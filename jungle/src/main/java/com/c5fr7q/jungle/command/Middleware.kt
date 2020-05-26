package com.c5fr7q.jungle.command

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

abstract class Middleware<Input : Command> : ObservableTransformer<Command, CommandResult> {
	override fun apply(upstream: Observable<Command>) = upstream.ofType(inputType).compose { transform(it) }

	abstract val inputType: Class<Input>

	abstract fun transform(upstream: Observable<Input>): ObservableSource<CommandResult>
}