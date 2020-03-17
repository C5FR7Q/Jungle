package com.example.myapplication.base.mvi

interface MviView<State, Action> {
	fun render(state: State) {}
	fun processAction(action: Action) {}
}