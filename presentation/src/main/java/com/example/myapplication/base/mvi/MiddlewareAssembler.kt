package com.example.myapplication.base.mvi

import io.reactivex.Observable

abstract class MiddlewareAssembler<InputAction, InternalAction, State> {
	fun assembleMiddlewares(inputActions: Observable<InputAction>, lastState: State): Observable<InternalAction> =
		Observable.merge(inputActions.splitByMiddleware(lastState))

	/**
	 * should return
	 * listOf(bind({Middleware1}), bind({Middleware2}, etc.))
	 * */
	protected abstract fun Observable<InputAction>.splitByMiddleware(lastState: State): List<Observable<out InternalAction>>

	protected inline fun <reified Input : InputAction, Output : InternalAction> Observable<InputAction>.bind(middleware: Middleware<Input, Output>): Observable<Output> =
		ofType(Input::class.java).compose(middleware)

	protected inline fun <reified Input : InputAction, Output : InternalAction> Observable<InputAction>.bind(
		state: State,
		middleware: StateBasedMiddleware<Input, Output, State>
	): Observable<Output> =
		ofType(Input::class.java).map { state to it }.compose(middleware)
}