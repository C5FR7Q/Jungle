package com.example.myapplication.base.mvi

interface View<State> {
	fun render(state: State)
}