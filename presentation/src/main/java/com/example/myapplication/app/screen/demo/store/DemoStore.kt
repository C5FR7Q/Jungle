package com.example.myapplication.app.screen.demo.store

import com.example.myapplication.app.screen.demo.CountryMiddleware
import com.example.myapplication.app.screen.demo.DemoAction
import com.example.myapplication.app.screen.demo.DemoEvent
import com.example.myapplication.app.screen.demo.DemoState
import com.example.myapplication.base.mvi.Store
import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandResult
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class DemoStore @Inject constructor(
	@Named("foregroundScheduler") foregroundScheduler: Scheduler,
	@Named("backgroundScheduler") backgroundScheduler: Scheduler,
	private val countryMiddleware: CountryMiddleware
) : Store<DemoEvent, DemoState, DemoAction>(
	foregroundScheduler = foregroundScheduler,
	backgroundScheduler = backgroundScheduler
) {
	override val initialState = DemoState()
	override val bootstrapCommands = listOf(CountryMiddleware.Input)

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

	override fun Observable<Command>.splitByMiddleware(state: Observable<DemoState>): List<Observable<CommandResult>> =
		listOf(bind(countryMiddleware))

	sealed class ProduceActionCommand : Command {
		data class Error(val error: String) : ProduceActionCommand()
	}
}