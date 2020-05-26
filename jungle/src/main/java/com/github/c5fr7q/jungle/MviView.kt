package com.github.c5fr7q.jungle

interface MviView<State, Action> {
	fun render(state: State) {}
	fun processAction(action: Action) {}
}