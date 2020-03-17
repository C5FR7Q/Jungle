package com.example.domain.country

import io.reactivex.Observable
import javax.inject.Inject

class GetCountriesInteractorImpl @Inject constructor(private val countryRepository: CountryRepository) : GetCountriesInteractor {
	override val countries: Observable<List<Country>>
		get() = countryRepository.countries
}