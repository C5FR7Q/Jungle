package com.example.myapplication.app.screen.demo.store

import com.example.myapplication.app.screen.demo.CountryMiddleware
import com.example.myapplication.app.screen.demo.DemoState
import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandExecutor
import io.reactivex.Observable
import javax.inject.Inject

class DemoCommandExecutor @Inject constructor(private val countryMiddleware: CountryMiddleware) : CommandExecutor<DemoState>() {
	override fun Observable<Command>.splitByMiddleware(state: Observable<DemoState>) = listOf(
		bind(countryMiddleware)
	)
}