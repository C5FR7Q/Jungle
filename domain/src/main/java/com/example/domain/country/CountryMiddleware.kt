package com.example.domain.country

import io.reactivex.Observable

interface CountryMiddleware {
	val countries: Observable<List<Country>>
}