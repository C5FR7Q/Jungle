package com.example.myapplication.base.mvi.command

import io.reactivex.Observable
import io.reactivex.functions.BiFunction

abstract class CommandExecutor<State> {
	fun execute(commands: Observable<Command>, state: Observable<State>): Observable<CommandResult> =
		commands.publish { Observable.merge(it.splitByMiddleware(state)) }

	/**
	 * should return
	 * listOf(bind({Middleware1}), bind({Middleware2}, etc.))
	 * */
	protected abstract fun Observable<Command>.splitByMiddleware(state: Observable<State>): List<Observable<CommandResult>>

	protected inline fun <reified Input : Command, Output : CommandResult> Observable<Command>.bind(
		middleware: Middleware<Input, Output>
	): Observable<Output> =
		ofType(Input::class.java).compose(middleware)

	protected inline fun <reified Input : Command, Output : CommandResult> Observable<Command>.bind(
		middleware: StatefulMiddleware<Input, State, Output>,
		state: Observable<State>
	): Observable<Output> {
		return ofType(Input::class.java)
			.withLatestFrom(state, BiFunction<Input, State, Pair<Input, State>> { t1, t2 -> t1 to t2 })
			.compose(middleware)
	}

}