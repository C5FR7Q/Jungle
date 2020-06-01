package com.github.c5fr7q.jungle.command

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

abstract class StatefulMiddleware<Input : Command, State> : ObservableTransformer<Command, CommandResult> {
	private var state: Observable<State> = Observable.empty()

	override fun apply(upstream: Observable<Command>) = upstream.ofType(inputType)
		.compose { transform(it.withLatestFrom(state, BiFunction<Input, State, CommandState> { t1, t2 -> CommandState(t1, t2) })) }

	abstract val inputType: Class<Input>

	abstract fun transform(upstream: Observable<CommandState>): ObservableSource<CommandResult>

	fun attachState(state: Observable<State>) {
		this.state = state
	}

	inner class CommandState(val command: Input, val state: State)
}