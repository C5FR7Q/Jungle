package com.example.myapplication.app.screen.main

import com.example.myapplication.base.mvi.EventMapper
import com.example.myapplication.base.mvi.MiddlewareAssembler
import com.example.myapplication.base.mvi.Reducer
import com.example.myapplication.base.mvi.Store
import io.reactivex.Scheduler
import javax.inject.Inject

class MainStore @Inject constructor(
	foregroundScheduler: Scheduler,
	backgroundScheduler: Scheduler,
	eventMapper: EventMapper<MainEvent, InputAction>
) : Store<MainEvent, MainStore.InputAction, MainStore.InternalAction, MainState>(
	foregroundScheduler,
	backgroundScheduler,
	eventMapper
) {

	sealed class InputAction
	sealed class InternalAction

	override fun createMiddlewareAssembler(): MiddlewareAssembler<InputAction, InternalAction, MainState> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun createReducer(): Reducer<MainState, InternalAction> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}