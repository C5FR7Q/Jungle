package com.example.domain.country

import io.reactivex.Observable

interface GetCountriesInteractor {
	fun execute(): Observable<List<Country>>
}