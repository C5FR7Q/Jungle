package com.example.myapplication.base.mvi.command

import com.example.myapplication.base.mvi.Middleware
import io.reactivex.Observable

abstract class CommandExecutor {
	fun execute(commands: Observable<Command>): Observable<CommandResult> = commands.publish { Observable.merge(it.splitByMiddleware()) }

	/**
	 * should return
	 * listOf(bind({Middleware1}), bind({Middleware2}, etc.))
	 * */
	protected abstract fun Observable<Command>.splitByMiddleware(): List<Observable<CommandResult>>

	protected inline fun <reified Input : Command, Output : CommandResult> Observable<Command>.bind(middleware: Middleware<Input, Output>): Observable<Output> =
		ofType(Input::class.java).compose(middleware)

}