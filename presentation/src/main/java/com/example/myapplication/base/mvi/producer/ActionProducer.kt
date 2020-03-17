package com.example.myapplication.base.mvi.producer

import com.example.myapplication.base.mvi.command.Command

interface ActionProducer<Action> : Producer<Command, Action>