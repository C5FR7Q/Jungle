package com.example.myapplication.app.screen.demo.store

import com.example.myapplication.app.screen.demo.CountryMiddleware
import com.example.myapplication.app.screen.demo.DemoAction
import com.example.myapplication.app.screen.demo.DemoEvent
import com.example.myapplication.app.screen.demo.DemoState
import com.example.myapplication.base.mvi.Store
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class DemoStore @Inject constructor(
	@Named("foregroundScheduler") foregroundScheduler: Scheduler,
	@Named("backgroundScheduler") backgroundScheduler: Scheduler,
	actionProducer: DemoActionProducer,
	commandExecutor: DemoCommandExecutor,
	reducer: DemoReducer,
	commandProducer: DemoCommandProducer
) : Store<DemoEvent, DemoState, DemoAction>(
	foregroundScheduler = foregroundScheduler,
	backgroundScheduler = backgroundScheduler,
	actionProducer = actionProducer,
	commandExecutor = commandExecutor,
	reducer = reducer,
	commandProducer = commandProducer
) {
	override fun convertEvent(event: DemoEvent) =
		when (event) {
			is DemoEvent.Load -> CountryMiddleware.Input
		}

	override val bootstrapCommands = listOf(CountryMiddleware.Input)
}