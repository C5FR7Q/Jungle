package com.example.data.country

import com.example.data.RetrofitApi
import com.example.data.RetrofitApi.Companion.SCHEDULER
import com.example.domain.country.Country
import com.example.domain.country.CountryRepository
import io.reactivex.Observable
import javax.inject.Inject
import kotlin.random.Random

class CountryRepositoryImpl @Inject constructor() : CountryRepository, RetrofitApi<CountryApi> {

	private val mapper = CountryMapper()

	override val baseUrl = "https://restcountries.eu/rest/v2/"
	override val apiClass = CountryApi::class.java

	override val countries: Observable<List<Country>>
		get() = api.getAll()
			.flatMap { responses ->
				Observable.fromIterable(responses)
					.map { mapper.convert(it) }
					.toList()
					.toObservable()
			}
			.map { if (Random.nextBoolean()) it else throw Exception("can't load") }
			.subscribeOn(SCHEDULER)
}