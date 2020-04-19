package com.example.myapplication.app.screen.demo.store

import com.example.myapplication.app.screen.demo.DemoAction
import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.producer.ActionProducer
import javax.inject.Inject

class DemoActionProducer @Inject constructor() : ActionProducer<DemoAction> {
	override fun produce(input: Command) = when (input) {
		is Input.Error -> DemoAction.ShowError(input.error)
		else -> null
	}

	sealed class Input : Command {
		data class Error(val error: String) : Input()
	}
}