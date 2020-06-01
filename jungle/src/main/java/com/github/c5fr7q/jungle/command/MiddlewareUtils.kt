package com.github.c5fr7q.jungle.command

import io.reactivex.Observable

fun <T : Command> Observable<T>.doWithNothing(function: () -> Unit): Observable<CommandResult> =
	doOnNext { function.invoke() }.map { CommandResult.Nothing }