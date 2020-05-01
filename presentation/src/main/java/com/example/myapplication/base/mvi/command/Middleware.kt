package com.example.myapplication.base.mvi.command

import io.reactivex.ObservableTransformer

interface Middleware<C : Command> : ObservableTransformer<C, CommandResult>

interface StatefulMiddleware<C : Command, S> : ObservableTransformer<Pair<C, S>, CommandResult>