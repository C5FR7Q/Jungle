package com.example.myapplication.app.screen.main.store

import com.example.myapplication.app.screen.main.CountryMiddleware
import com.example.myapplication.app.screen.main.MainState
import com.example.myapplication.base.mvi.Reducer
import com.example.myapplication.base.mvi.command.CommandResult
import javax.inject.Inject

class MainReducer @Inject constructor() : Reducer<MainState> {
	override val initialState = MainState()

	override fun reduce(state: MainState, commandResult: CommandResult) = when (commandResult) {
		is CountryMiddleware.Output.Loading -> state.copy(loading = true)
		is CountryMiddleware.Output.Loaded -> state.copy(loading = false, countries = commandResult.countries)
		is CountryMiddleware.Output.Failed -> state.copy(loading = false)
		else -> state
	}
}