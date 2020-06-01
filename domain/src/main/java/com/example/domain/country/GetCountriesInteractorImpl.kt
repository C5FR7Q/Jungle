package com.example.domain.country

import javax.inject.Inject

class GetCountriesInteractorImpl @Inject constructor(private val countryRepository: CountryRepository) : GetCountriesInteractor {
	override fun execute() = countryRepository.countries
}