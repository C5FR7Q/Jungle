package com.example.myapplication.app.screen.main

import com.example.domain.country.GetCountriesInteractor
import com.example.myapplication.base.mvi.Middleware
import io.reactivex.Observable
import io.reactivex.ObservableSource
import javax.inject.Inject

class CountryMiddleware @Inject constructor(private val getCountriesInteractor: GetCountriesInteractor) :
	Middleware<MainStore.InputAction.Load, MainStore.InternalAction> {
	override fun apply(upstream: Observable<MainStore.InputAction.Load>): ObservableSource<MainStore.InternalAction> {
		return upstream.switchMap {
			getCountriesInteractor.countries
				.map<MainStore.InternalAction> { MainStore.InternalAction.Loaded(it) }
				.onErrorReturn { MainStore.InternalAction.Failed(it.message ?: "Can't load countries") }
				.startWith(MainStore.InternalAction.Loading)
		}
	}
}