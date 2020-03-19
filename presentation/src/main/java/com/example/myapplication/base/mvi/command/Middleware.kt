package com.example.myapplication.base.mvi.command

import io.reactivex.ObservableTransformer

interface Middleware<C : Command, CR : CommandResult> : ObservableTransformer<C, CR>

interface StatefulMiddleware<C : Command, S, CR : CommandResult> : ObservableTransformer<Pair<C, S>, CR>