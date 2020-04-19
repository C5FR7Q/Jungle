package com.example.myapplication.app.screen.demo.main.store

import com.example.myapplication.app.screen.demo.main.CountryMiddleware
import com.example.myapplication.app.screen.demo.main.MainState
import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandExecutor
import io.reactivex.Observable
import javax.inject.Inject

class MainCommandExecutor @Inject constructor(private val countryMiddleware: CountryMiddleware) : CommandExecutor<MainState>() {
	override fun Observable<Command>.splitByMiddleware(state: Observable<MainState>) = listOf(
		bind(countryMiddleware)
	)
}