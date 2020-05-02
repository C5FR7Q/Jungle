package com.example.myapplication.base.mvi.command

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

abstract class StatefulMiddleware<Input : Command, State> : ObservableTransformer<Command, CommandResult> {
	private var state: Observable<State> = Observable.empty()

	override fun apply(upstream: Observable<Command>) = upstream.ofType(inputType)
		.compose { transform(it.withLatestFrom(state, BiFunction<Input, State, Pair<Input, State>> { t1, t2 -> t1 to t2 })) }

	abstract val inputType: Class<Input>

	abstract fun transform(upstream: Observable<Pair<Input, State>>): ObservableSource<CommandResult>

	fun attachState(state: Observable<State>) {
		this.state = state
	}
}