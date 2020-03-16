package com.example.myapplication.base.mvi

interface Reducer<State, InternalAction> {
	val initialState: State
	fun reduce(state: State, internalAction: InternalAction): State
}