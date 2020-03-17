package com.example.myapplication.base.mvi.command

import io.reactivex.Observable

abstract class UpdateStateMiddleware<Common> : Middleware<Common, Common>
		where Common : Command,
			  Common : CommandResult {
	override fun apply(upstream: Observable<Common>) = upstream
}