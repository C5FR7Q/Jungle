package com.example.domain.country

import io.reactivex.Observable

interface CountryRepository {
	val countries: Observable<List<Country>>
}