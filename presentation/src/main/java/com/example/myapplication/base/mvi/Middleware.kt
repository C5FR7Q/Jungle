package com.example.myapplication.base.mvi

import io.reactivex.ObservableTransformer

interface Middleware<InputAction, InternalAction> : ObservableTransformer<InputAction, InternalAction>

interface StateBasedMiddleware<InputAction, InternalAction, State> : Middleware<Pair<State, InputAction>, InternalAction>
