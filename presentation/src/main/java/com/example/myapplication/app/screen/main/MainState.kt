package com.example.myapplication.app.screen.main

import com.example.domain.country.Country

sealed class MainState {
	object Idle : MainState()
	object Loading : MainState()
	class Success(val countries: List<Country>) : MainState()
	class Fail(val error: String) : MainState()
}