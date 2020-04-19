package com.example.myapplication.app.screen.demo.main

import com.example.myapplication.base.mvi.EventMapper
import javax.inject.Inject

class MainEventMapper @Inject constructor() : EventMapper<MainEvent> {
	override fun convert(event: MainEvent) =
		when (event) {
			is MainEvent.Load -> CountryMiddleware.Input
		}
}