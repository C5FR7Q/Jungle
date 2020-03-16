package com.example.data.country

import io.reactivex.Observable
import retrofit2.http.GET

interface CountryApi {
	@GET("all")
	fun getAll(): Observable<List<CountryResponse>>
}