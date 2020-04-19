package com.example.myapplication.app.screen.demo.store

import com.example.myapplication.app.screen.demo.CountryMiddleware
import com.example.myapplication.app.screen.demo.DemoState
import com.example.myapplication.base.mvi.Reducer
import com.example.myapplication.base.mvi.command.CommandResult
import javax.inject.Inject

class DemoReducer @Inject constructor() : Reducer<DemoState> {
	override val initialState = DemoState()

	override fun reduce(state: DemoState, commandResult: CommandResult) = when (commandResult) {
		is CountryMiddleware.Output.Loading -> state.copy(loading = true)
		is CountryMiddleware.Output.Loaded -> state.copy(loading = false, countries = commandResult.countries)
		is CountryMiddleware.Output.Failed -> state.copy(loading = false)
		else -> state
	}
}