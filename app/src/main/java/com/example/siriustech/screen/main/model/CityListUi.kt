package com.example.siriustech.screen.main.model

import com.example.siriustech.domain.entity.City

data class CityListUi(
    val cities: List<City>
) {
    data class CityUi(
        val id: Int?,
        val name: String,
        val country: String,
        val latitude: Double?,
        val longitude: Double?,
        val onClick: (() -> Unit)
    ) {
        val displayCityName
            get() = "$name, $country"
    }
}