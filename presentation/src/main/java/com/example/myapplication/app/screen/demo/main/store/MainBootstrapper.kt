package com.example.myapplication.app.screen.demo.main.store

import com.example.myapplication.app.screen.demo.main.CountryMiddleware
import com.example.myapplication.base.mvi.Bootstrapper
import com.example.myapplication.base.mvi.command.Command
import javax.inject.Inject

class MainBootstrapper @Inject constructor(): Bootstrapper {
	override val bootstrapCommands: List<Command>
		get() = listOf(CountryMiddleware.Input)
}