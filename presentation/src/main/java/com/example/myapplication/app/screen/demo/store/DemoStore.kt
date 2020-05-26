package com.example.myapplication.app.screen.demo.store

import com.github.c5fr7q.jungle.Store
import com.github.c5fr7q.jungle.command.Command
import com.github.c5fr7q.jungle.command.CommandResult
import com.example.myapplication.app.screen.demo.CountryMiddleware
import com.example.myapplication.app.screen.demo.DemoAction
import com.example.myapplication.app.screen.demo.DemoEvent
import com.example.myapplication.app.screen.demo.DemoState
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class DemoStore @Inject constructor(
	@Named("foregroundScheduler") foregroundScheduler: Scheduler,
	@Named("backgroundScheduler") backgroundScheduler: Scheduler,
	countryMiddleware: CountryMiddleware
) : Store<DemoEvent, DemoState, DemoAction>(
	foregroundScheduler = foregroundScheduler,
	backgroundScheduler = backgroundScheduler
) {
	override val initialState = DemoState()
	override val bootstrapCommands = listOf(CountryMiddleware.Input)
	override val middlewares = listOf(countryMiddleware)

	override fun convertEvent(event: DemoEvent) =
		when (event) {
			is DemoEvent.Load -> CountryMiddleware.Input
		}

	override fun produceAction(command: Command) = when (command) {
		is ProduceActionCommand.Error -> DemoAction.ShowError(command.error)
		else -> null
	}

	override fun produceCommand(commandResult: CommandResult) = when (commandResult) {
		is CountryMiddleware.Output.Failed -> ProduceActionCommand.Error(commandResult.error)
		else -> null
	}

	override fun reduceCommandResult(state: DemoState, commandResult: CommandResult) = when (commandResult) {
		is CountryMiddleware.Output.Loading -> state.copy(loading = true)
		is CountryMiddleware.Output.Loaded -> state.copy(loading = false, countries = commandResult.countries)
		is CountryMiddleware.Output.Failed -> state.copy(loading = false)
		else -> state
	}

	sealed class ProduceActionCommand : Command {
		data class Error(val error: String) : ProduceActionCommand()
	}
}