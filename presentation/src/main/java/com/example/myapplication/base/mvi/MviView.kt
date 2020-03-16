package com.example.myapplication.base.mvi

interface MviView<State> {
	fun render(state: State)
}