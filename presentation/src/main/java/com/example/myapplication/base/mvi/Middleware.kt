package com.example.myapplication.base.mvi

import io.reactivex.ObservableTransformer

interface Middleware<Event, InternalAction> : ObservableTransformer<Event, InternalAction>

interface StateBasedMiddleware<Event, InternalAction, State> : Middleware<Pair<State, Event>, InternalAction>
