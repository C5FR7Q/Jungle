package com.example.myapplication.base.mvi.producer

import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandResult

interface CommandProducer : Producer<CommandResult, Command>