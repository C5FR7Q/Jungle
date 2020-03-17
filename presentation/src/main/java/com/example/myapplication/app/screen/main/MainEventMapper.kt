package com.example.myapplication.app.screen.main

import com.example.myapplication.base.mvi.EventMapper
import javax.inject.Inject

class MainEventMapper @Inject constructor() : EventMapper<MainEvent, MainStore.InputAction> {
	override fun convert(event: MainEvent) =
		when (event) {
			is MainEvent.Load -> MainStore.InputAction.Load
		}
}