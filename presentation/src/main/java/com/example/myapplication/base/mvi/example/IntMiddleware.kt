package com.example.myapplication.base.mvi.example

import com.example.myapplication.base.mvi.Middleware
import io.reactivex.Observable

class IntMiddleware : Middleware<Int, Int> {
	override fun apply(upstream: Observable<Int>) = Observable.just(1)
}
