package com.example.data

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitApi<T> {
	companion object {
		val SCHEDULER = Schedulers.computation()
	}

	val baseUrl: String
	val apiClass: Class<T>
	val api: T
		get() = Retrofit.Builder()
			.baseUrl(baseUrl)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build().create(apiClass)

}
