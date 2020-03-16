package com.example.myapplication.base.mvi

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class Store<Event, InputAction, InternalAction, State>(
	private val foregroundScheduler: Scheduler,
	private val backgroundScheduler: Scheduler,
	private val eventMapper: EventMapper<Event, InputAction>,
	private val middlewareAssembler: MiddlewareAssembler<InputAction, InternalAction, State>,
	private val reducer: Reducer<State, InternalAction>,
	private val directActionProcessor: ActionProcessor<InputAction>? = null,
	private val sideEffectProcessor: ActionProcessor<InternalAction>? = null
) {

	private val inputActions = PublishSubject.create<InputAction>()
	private val states = BehaviorSubject.createDefault(reducer.internalState)

	private val lifeCycleSubscriptions = CompositeDisposable()
	private val processActionsSubscriptions = CompositeDisposable()

	private var attached = false

	init {
		launch()
	}

	fun dispatchEvent(event: Observable<Event>) {
		if (attached) {
			lifeCycleSubscriptions.add(
				event.observeOn(foregroundScheduler).subscribe { inputActions.onNext(eventMapper.convert(it)) }
			)
		}
	}

	fun attach(view: View<State>) {
		lifeCycleSubscriptions.add(
			states.observeOn(foregroundScheduler).subscribe { view.render(it) }
		)
		attached = true
	}

	fun detach() {
		lifeCycleSubscriptions.dispose()
		attached = false
	}

	// TODO: 16.03.20 Think of creating of StoreProcessor for better performance.
	fun launch() {

		val inputActionsSource = inputActions.replay(1).refCount()
		val internalActionsSource = inputActionsSource.publish { middlewareAssembler.assembleMiddlewares(it, states.value!!) }
			.replay(1)
			.refCount()
			.subscribeOn(backgroundScheduler)

		directActionProcessor?.run {
			processActionsSubscriptions.add(
				inputActionsSource.observeOn(foregroundScheduler).subscribe { processAction(it) }
			)
		}

		sideEffectProcessor?.run {
			processActionsSubscriptions.add(
				internalActionsSource.observeOn(foregroundScheduler).subscribe { processAction(it) }
			)
		}

		val initialState = states.value!!
		processActionsSubscriptions.add(
			internalActionsSource.scan(initialState, { state, internalAction -> reducer.reduce(state, internalAction) })
				.distinctUntilChanged()
				.subscribe { states.onNext(it) }
		)
	}

	fun finish() {
		processActionsSubscriptions.dispose()
	}

	protected fun dispatchAction(inputAction: InputAction) {
		inputActions.onNext(inputAction)
	}
}