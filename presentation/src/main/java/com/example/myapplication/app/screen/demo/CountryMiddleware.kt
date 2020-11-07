package com.example.myapplication.app.screen.demo

import com.example.domain.country.Country
import com.example.domain.country.GetCountriesInteractor
import com.github.c5fr7q.jungle.command.Command
import com.github.c5fr7q.jungle.command.CommandResult
import com.github.c5fr7q.jungle.command.Middleware
import io.reactivex.Observable
import io.reactivex.ObservableSource
import javax.inject.Inject

class CountryMiddleware @Inject constructor(
	private val getCountriesInteractor: GetCountriesInteractor
) : Middleware<CountryMiddleware.Input>() {
	override fun transform(upstream: Observable<Input>): ObservableSource<CommandResult> =
		upstream.switchMap {
			getCountriesInteractor.execute()
				.map<Output> { Output.Loaded(it) }
				.onErrorReturn { Output.Failed(it.message ?: "Can't load countries") }
				.startWith(Output.Loading)
		}

	object Input : Command

	sealed class Output : CommandResult {
		object Loading : Output()
		data class Loaded(val countries: List<Country>) : Output()
		data class Failed(val error: String) : Output()
	}
}