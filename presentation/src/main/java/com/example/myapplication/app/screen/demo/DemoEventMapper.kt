package com.example.myapplication.app.screen.demo

import com.example.myapplication.base.mvi.EventMapper
import javax.inject.Inject

class DemoEventMapper @Inject constructor() : EventMapper<DemoEvent> {
	override fun convert(event: DemoEvent) =
		when (event) {
			is DemoEvent.Load -> CountryMiddleware.Input
		}
}