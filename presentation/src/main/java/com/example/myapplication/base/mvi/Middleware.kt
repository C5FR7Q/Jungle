package com.example.myapplication.base.mvi

import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandResult
import io.reactivex.ObservableTransformer

interface Middleware<C : Command, CR : CommandResult> : ObservableTransformer<C, CR>