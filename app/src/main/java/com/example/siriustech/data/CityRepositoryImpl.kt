package com.example.siriustech.data

import com.example.siriustech.data.response.CityResponseMapper
import com.example.siriustech.domain.entity.CityList
import com.example.siriustech.service.CityService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepositoryImpl @Inject constructor(
    private val service: CityService,
    private val cityResponseMapper: CityResponseMapper
) : CityRepository {

    override suspend fun getCities(): CityList {
        return cityResponseMapper.map(service.getCities())
    }

}