package com.example.myapplication.app.screen.demo

import com.example.domain.country.Country

data class DemoState(
	val loading: Boolean = false,
	val countries: List<Country> = emptyList()
)