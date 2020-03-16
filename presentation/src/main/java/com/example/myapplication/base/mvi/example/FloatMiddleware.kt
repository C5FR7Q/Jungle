package com.example.myapplication.base.mvi.example

import com.example.myapplication.base.mvi.Middleware
import io.reactivex.Observable

class FloatMiddleware : Middleware<Float, String> {
	override fun apply(events: Observable<Float>) = Observable.just("a")
}
