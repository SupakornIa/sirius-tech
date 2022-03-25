package com.example.siriustech.data

import com.example.siriustech.domain.entity.CityList

interface CityRepository {
    suspend fun getCities(): CityList
}