package com.example.myapplication.app.screen.main.store

import com.example.myapplication.app.screen.main.MainAction
import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.producer.ActionProducer
import javax.inject.Inject

class MainActionProducer @Inject constructor() : ActionProducer<MainAction> {
	override fun produce(input: Command) = when (input) {
		is Input.Error -> MainAction.ShowError(input.error)
		else -> null
	}

	sealed class Input : Command {
		data class Error(val error: String) : Input()
	}
}