package com.example.myapplication.base.mvi

import com.example.myapplication.base.mvi.command.Command

interface Bootstrapper {
	val bootstrapCommands: List<Command>
}