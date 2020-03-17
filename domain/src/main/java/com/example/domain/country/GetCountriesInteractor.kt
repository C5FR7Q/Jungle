package com.example.domain.country

import io.reactivex.Observable

interface GetCountriesInteractor {
	val countries: Observable<List<Country>>
}