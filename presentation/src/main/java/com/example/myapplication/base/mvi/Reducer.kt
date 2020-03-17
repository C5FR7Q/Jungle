package com.example.myapplication.base.mvi

import com.example.myapplication.base.mvi.command.CommandResult

interface Reducer<State> {
	val initialState: State
	fun reduce(state: State, commandResult: CommandResult): State
}