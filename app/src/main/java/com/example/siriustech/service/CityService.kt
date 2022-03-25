package com.example.siriustech.service

import com.example.siriustech.data.response.CityResponse
import retrofit2.http.GET

interface CityService {
    @GET("cities.json")
    suspend fun getCities(): List<CityResponse>
}