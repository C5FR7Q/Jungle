package com.example.myapplication.base.mvi

import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandExecutor
import com.example.myapplication.base.mvi.producer.ActionProducer
import com.example.myapplication.base.mvi.producer.CommandProducer
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class Store<Event, State, Action>(
	private val foregroundScheduler: Scheduler,
	private val backgroundScheduler: Scheduler,
	private val eventMapper: EventMapper<Event>,
	private val actionProducer: ActionProducer<Action>? = null,
	private val commandExecutor: CommandExecutor? = null,
	private val reducer: Reducer<State>? = null,
	private val commandProducer: CommandProducer? = null
) {

	private val commands = PublishSubject.create<Command>()
	private val states = BehaviorSubject.create<State>()
	private val actions = PublishSubject.create<Action>()

	private val lifeCycleSubscriptions = CompositeDisposable()
	private val processCommandsSubscriptions = CompositeDisposable()

	private var attached = false

	init {
		reducer?.let { states.onNext(it.initialState) }
		launch()
	}

	fun dispatchEvent(event: Observable<Event>) {
		if (attached) {
			lifeCycleSubscriptions.add(
				event.observeOn(foregroundScheduler).subscribe { commands.onNext(eventMapper.convert(it)) }
			)
		}
	}

	fun attach(view: MviView<State, Action>) {
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
	}

	// TODO: 16.03.20 Think of creating of StoreProcessor for better performance.
	fun launch() {

		val commandSource = commands.subscribeOn(backgroundScheduler).replay(1).refCount()
/*
		val commandResultSource = commandSource.publish { middlewareAssembler.assembleMiddlewares(it, states.value!!) }
			.replay(1)
			.refCount()
*/

		if (actionProducer != null) {
			processCommandsSubscriptions.add(
				commandSource.subscribe { command ->
					actionProducer.produce(command)?.let { actions.onNext(it) }
				}
			)
		}

		if (commandExecutor != null) {
			val commandResultSource = commandExecutor.execute(commandSource).replay(1).refCount()

			if (commandProducer != null) {
				processCommandsSubscriptions.add(
					commandResultSource.subscribe { commandResult ->
						commandProducer.produce(commandResult)?.let { commands.onNext(it) }
					}
				)
			}

			if (reducer != null) {
				val initialState = states.value!!
				processCommandsSubscriptions.add(
					commandResultSource.scan(initialState, { state, internalAction -> reducer.reduce(state, internalAction) })
						.distinctUntilChanged()
						.subscribe { states.onNext(it) }
				)
			}

		}
	}

	fun finish() {
		processCommandsSubscriptions.dispose()
	}
}