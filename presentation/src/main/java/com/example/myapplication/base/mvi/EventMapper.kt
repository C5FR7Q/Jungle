package com.example.myapplication.base.mvi

import com.example.myapplication.base.mvi.command.Command

interface EventMapper<Event> {
	fun convert(event: Event): Command
}