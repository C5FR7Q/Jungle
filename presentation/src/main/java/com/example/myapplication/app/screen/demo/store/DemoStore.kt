package com.example.myapplication.app.screen.demo.store

import com.example.myapplication.app.screen.demo.DemoAction
import com.example.myapplication.app.screen.demo.DemoEvent
import com.example.myapplication.app.screen.demo.DemoEventMapper
import com.example.myapplication.app.screen.demo.DemoState
import com.example.myapplication.base.mvi.Store
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class DemoStore @Inject constructor(
	@Named("foregroundScheduler") foregroundScheduler: Scheduler,
	@Named("backgroundScheduler") backgroundScheduler: Scheduler,
	eventMapper: DemoEventMapper,
	actionProducer: DemoActionProducer,
	bootstrapper: DemoBootstrapper,
	commandExecutor: DemoCommandExecutor,
	reducer: DemoReducer,
	commandProducer: DemoCommandProducer
) : Store<DemoEvent, DemoState, DemoAction>(
	foregroundScheduler = foregroundScheduler,
	backgroundScheduler = backgroundScheduler,
	eventMapper = eventMapper,
	actionProducer = actionProducer,
	bootstrapper = bootstrapper,
	commandExecutor = commandExecutor,
	reducer = reducer,
	commandProducer = commandProducer
)