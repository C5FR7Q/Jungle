package com.github.c5fr7q.jungle

import com.github.c5fr7q.jungle.command.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

abstract class Store<Event, State, Action>(
	private val foregroundScheduler: Scheduler,
	private val backgroundScheduler: Scheduler
) {

	private val commands = PublishSubject.create<Command>()
	private val states = BehaviorSubject.create<State>()
	private val actions = PublishSubject.create<Action>()

	private val lifeCycleSubscriptions = CompositeDisposable()
	private val processCommandsSubscriptions = CompositeDisposable()

	private var attached = false

	protected open val initialState: State
		get() = throw NotImplementedException()
	protected open val bootstrapCommands = emptyList<Command>()
	protected open val statefulMiddlewares = emptyList<StatefulMiddleware<*, State>>()
	protected open val middlewares = emptyList<Middleware<*>>()

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
					} catch (ignored: NotImplementedException) {
					}
				}
			)
		}
	}

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

	fun launch() {
		val bootstrapCommandsSource = Observable.fromIterable(bootstrapCommands)
		val commandSourceSubject: ReplaySubject<Command> = ReplaySubject.create()
		commands.let { bootstrapCommandsSource.mergeWith(it) ?: it }
			.subscribeOn(backgroundScheduler)
			.subscribe(commandSourceSubject)

		processCommandsSubscriptions.add(
			commandSourceSubject.subscribe { command ->
				produceAction(command)?.let { actions.onNext(it) }
			}
		)

		val commandResultSourceSubject: ReplaySubject<CommandResult> = ReplaySubject.create()
		Observable.merge(
			executeCommands(commandSourceSubject, states),
			commandSourceSubject.ofType(CommandCommandResult::class.java)
		).subscribe(commandResultSourceSubject)

		processCommandsSubscriptions.add(
			commandResultSourceSubject.subscribe { commandResult ->
				produceCommand(commandResult)?.let { commands.onNext(it) }
			}
		)

		try {
			if (!states.hasValue()) {
				states.onNext(initialState)
			}
			val initState = states.value!!
			processCommandsSubscriptions.add(
				commandResultSourceSubject.scan(initState, { state, commandResult -> reduceCommandResult(state, commandResult) })
					.distinctUntilChanged()
					.subscribe { states.onNext(it) }
			)
		} catch (ignored: NotImplementedException) {
		}
	}

	fun finish() {
		processCommandsSubscriptions.dispose()
	}

	protected open fun convertEvent(event: Event): Command = throw NotImplementedException()
	protected open fun produceAction(command: Command): Action? = null
	protected open fun produceCommand(commandResult: CommandResult): Command? = null
	protected open fun reduceCommandResult(state: State, commandResult: CommandResult): State = state

	private fun executeCommands(commands: Observable<Command>, state: Observable<State>): Observable<CommandResult> =
		commands.publish { commandSource ->
			statefulMiddlewares.forEach { it.attachState(state) }
			Observable.merge(mutableListOf<Observable<CommandResult>>().apply {
				addAll(statefulMiddlewares.map { commandSource.compose(it) })
				addAll(middlewares.map { commandSource.compose(it) })
			})
		}
}
