package com.example.myapplication.base.mvi

interface ActionProcessor<Action> {
	fun processAction(action: Action)
}