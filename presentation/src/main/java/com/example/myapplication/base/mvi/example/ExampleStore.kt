package com.example.myapplication.base.mvi.example

import com.example.myapplication.base.mvi.Reducer
import com.example.myapplication.base.mvi.Store
import io.reactivex.Observable

class ExampleStore(
	private val intMid: IntMiddleware,
	private val floatMid: FloatMiddleware
) : Store<Number, String>() {
	override fun createReducer() = ExampleReducer()

	override fun Observable<Number>.splitByMiddleware() = listOf(
		bind(intMid),
		bind(floatMid)
	)

	class ExampleReducer : Reducer<String> {
		override val internalState = ""

		override fun reduce(state: String, internalAction: Any): String {
			return "a"
		}
	}
}