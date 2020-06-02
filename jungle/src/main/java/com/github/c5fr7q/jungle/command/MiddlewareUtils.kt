package com.github.c5fr7q.jungle.command

import io.reactivex.Observable

fun <T : Command> Observable<T>.doWithNothing(function: (T) -> Unit): Observable<CommandResult> =
	doOnNext { function.invoke(it) }.map { CommandResult.Nothing }