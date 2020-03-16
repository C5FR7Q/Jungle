package com.example.data.country

import com.example.domain.country.Country

class CountryMapper {
	fun convert(response: CountryResponse) =
		response.run {
			Country(name)
		}
}