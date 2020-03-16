package com.example.myapplication.app.screen.main

import com.example.myapplication.base.mvi.EventMapper

class MainEventMapper : EventMapper<MainEvent, MainStore.InputAction> {
	override fun convert(event: MainEvent) =
		when (event) {
			is MainEvent.Load -> MainStore.InputAction.Load
		}
}