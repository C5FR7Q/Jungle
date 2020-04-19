package com.example.myapplication.app.screen.demo

import com.example.domain.country.Country
import com.example.domain.country.GetCountriesInteractor
import com.example.myapplication.base.mvi.command.Command
import com.example.myapplication.base.mvi.command.CommandResult
import com.example.myapplication.base.mvi.command.Middleware
import io.reactivex.Observable
import io.reactivex.ObservableSource
import javax.inject.Inject

class CountryMiddleware @Inject constructor(private val getCountriesInteractor: GetCountriesInteractor) :
	Middleware<CountryMiddleware.Input, CommandResult> {
	override fun apply(upstream: Observable<Input>): ObservableSource<CommandResult> {
		return upstream.switchMap {
			getCountriesInteractor.countries
				.map<Output> {
					Output.Loaded(
						it
					)
				}
				.onErrorReturn {
					Output.Failed(
						it.message ?: "Can't load countries"
					)
				}
				.startWith(Output.Loading)
		}
	}

	object Input : Command

	sealed class Output : CommandResult {
		object Loading : Output()
		data class Loaded(val countries: List<Country>) : Output()
		data class Failed(val error: String) : Output()
	}
}