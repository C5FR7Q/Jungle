package com.example.myapplication.app.screen.demo.main.store

import com.example.myapplication.app.screen.demo.main.CountryMiddleware
import com.example.myapplication.base.mvi.command.CommandResult
import com.example.myapplication.base.mvi.producer.CommandProducer
import javax.inject.Inject

class MainCommandProducer @Inject constructor() : CommandProducer {
	override fun produce(input: CommandResult) = when (input) {
		is CountryMiddleware.Output.Failed -> MainActionProducer.Input.Error(input.error)
		else -> null
	}
}