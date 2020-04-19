package com.example.myapplication.app.screen.demo.main

import com.example.domain.country.Country

data class MainState(
	val loading: Boolean = false,
	val countries: List<Country> = emptyList()
)