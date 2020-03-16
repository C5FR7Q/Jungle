package com.example.myapplication.app.screen.main

import com.example.domain.country.Country

data class MainState(
	private val loading: Boolean = false,
	private val countries: List<Country> = emptyList(),
	private val errorMessage: String = ""
)