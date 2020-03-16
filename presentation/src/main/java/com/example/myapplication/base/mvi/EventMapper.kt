package com.example.myapplication.base.mvi

interface EventMapper<Event, InputAction> {
	fun convert(event: Event): InputAction
}