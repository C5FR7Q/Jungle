package com.example.domain.country

import io.reactivex.Observable
import javax.inject.Inject

class CountryMiddlewareImpl @Inject constructor(private val countryRepository: CountryRepository) : CountryMiddleware {
	override val countries: Observable<List<Country>>
		get() = countryRepository.countries
}