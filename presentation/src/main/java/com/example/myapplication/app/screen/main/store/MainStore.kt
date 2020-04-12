package com.example.myapplication.app.screen.main.store

import com.example.myapplication.app.screen.main.MainAction
import com.example.myapplication.app.screen.main.MainEvent
import com.example.myapplication.app.screen.main.MainEventMapper
import com.example.myapplication.app.screen.main.MainState
import com.example.myapplication.base.mvi.Store
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class MainStore @Inject constructor(
	@Named("foregroundScheduler") foregroundScheduler: Scheduler,
	@Named("backgroundScheduler") backgroundScheduler: Scheduler,
	eventMapper: MainEventMapper,
	actionProducer: MainActionProducer,
	commandExecutor: MainCommandExecutor,
	reducer: MainReducer,
	commandProducer: MainCommandProducer
) : Store<MainEvent, MainState, MainAction>(
	foregroundScheduler = foregroundScheduler,
	backgroundScheduler = backgroundScheduler,
	eventMapper = eventMapper,
	actionProducer = actionProducer,
	commandExecutor = commandExecutor,
	reducer = reducer,
	commandProducer = commandProducer
)