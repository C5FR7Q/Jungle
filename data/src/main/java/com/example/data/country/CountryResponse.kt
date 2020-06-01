package com.example.data.country

import com.google.gson.annotations.SerializedName

data class CountryResponse(
	@SerializedName("name")
	val name: String
)