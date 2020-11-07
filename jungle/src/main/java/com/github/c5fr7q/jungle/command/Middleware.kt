package com.github.c5fr7q.jungle.command

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import java.lang.reflect.ParameterizedType

abstract class Middleware<Input : Command> : ObservableTransformer<Command, CommandResult> {
	private val inputType: Class<Input> get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<Input>

	override fun apply(upstream: Observable<Command>) = upstream.ofType(inputType).compose { transform(it) }

	abstract fun transform(upstream: Observable<Input>): ObservableSource<CommandResult>
}