package com.example.myapplication.base.mvi.command

import io.reactivex.ObservableTransformer

interface Middleware<C : Command, CR : CommandResult> : ObservableTransformer<C, CR>