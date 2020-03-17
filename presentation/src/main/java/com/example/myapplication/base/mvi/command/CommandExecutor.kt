package com.example.myapplication.base.mvi.command

import io.reactivex.Observable

interface CommandExecutor {
	fun execute(command: Command): Observable<CommandResult>
}