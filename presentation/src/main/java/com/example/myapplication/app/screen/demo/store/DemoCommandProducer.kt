package com.example.myapplication.app.screen.demo.store

import com.example.myapplication.app.screen.demo.CountryMiddleware
import com.example.myapplication.base.mvi.command.CommandResult
import com.example.myapplication.base.mvi.producer.CommandProducer
import javax.inject.Inject

class DemoCommandProducer @Inject constructor() : CommandProducer {
	override fun produce(input: CommandResult) = when (input) {
		is CountryMiddleware.Output.Failed -> DemoStore.ProduceActionCommand.Error(input.error)
		else -> null
	}
}