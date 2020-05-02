package com.example.myapplication.base.mvi

import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandCommandResult
import com.example.myapplication.base.mvi.command.CommandExecutor
import com.example.myapplication.base.mvi.command.CommandResult
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class Store<Event, State, Action>(
	private val foregroundScheduler: Scheduler,
	private val backgroundScheduler: Scheduler,
	private val commandExecutor: CommandExecutor<State>? = null
) {

	private val commands = PublishSubject.create<Command>()
	private val states = BehaviorSubject.create<State>()
	private val actions = PublishSubject.create<Action>()

	private val lifeCycleSubscriptions = CompositeDisposable()
	private val processCommandsSubscriptions = CompositeDisposable()

	private var attached = false

	init {
		try {
			@Suppress("LeakingThis")
			states.onNext(initialState)
		} catch (ignored: NotImplementedError) {
		}
	}

	fun dispatchEvent(event: Event) {
		dispatchEventSource(Observable.just(event))
	}

	fun dispatchEventSource(eventSource: Observable<Event>) {
		if (attached) {
			lifeCycleSubscriptions.add(
				eventSource.observeOn(foregroundScheduler).subscribe { event ->
					try {
						val command = convertEvent(event)
						commands.onNext(command)
					} catch (ignored: NotImplementedError) {
					}
				}
			)
		}
	}

	open fun convertEvent(event: Event): Command = TODO("Not used")

	fun attach(view: MviView<State, Action>) {
		launch()
		lifeCycleSubscriptions.add(
			states.observeOn(foregroundScheduler).subscribe { view.render(it) }
		)
		lifeCycleSubscriptions.add(
			actions.observeOn(foregroundScheduler).subscribe { view.processAction(it) }
		)
		attached = true
	}

	fun detach() {
		lifeCycleSubscriptions.dispose()
		attached = false
		finish()
	}

	open val bootstrapCommands: List<Command> = TODO("Not used")

	open fun produceAction(command: Command): Action? = null

	open fun produceCommand(commandResult: CommandResult): Command? = null

	open val initialState: State = TODO("Not used")

	open fun reduceCommandResult(state: State, commandResult: CommandResult): State = state

	fun launch() {
		val bootstrapCommandsSource = try {
			Observable.fromIterable(bootstrapCommands)
		} catch (ignored: NotImplementedError) {
			null
		}
		val commandSource = commands.let { bootstrapCommandsSource?.mergeWith(it) ?: it }
			.subscribeOn(backgroundScheduler)
			.replay(1)
			.refCount()

		processCommandsSubscriptions.add(
			commandSource.subscribe { command ->
				produceAction(command)?.let { actions.onNext(it) }
			}
		)

		if (commandExecutor != null) {
			val commandResultSource = Observable.merge(
				commandExecutor.execute(commandSource, states),
				commandSource.ofType(CommandCommandResult::class.java)
			)
				.replay(1)
				.refCount()

			processCommandsSubscriptions.add(
				commandResultSource.subscribe { commandResult ->
					produceCommand(commandResult)?.let { commands.onNext(it) }
				}
			)

			val initialState = states.value!!
			processCommandsSubscriptions.add(
				commandResultSource.scan(initialState, { state, commandResult -> reduceCommandResult(state, commandResult) })
					.distinctUntilChanged()
					.subscribe { states.onNext(it) }
			)
		}
	}

	fun finish() {
		processCommandsSubscriptions.dispose()
	}
}