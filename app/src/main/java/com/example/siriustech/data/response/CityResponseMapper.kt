package com.example.siriustech.data.response

import com.example.siriustech.data.Mapper
import com.example.siriustech.domain.entity.City
import com.example.siriustech.domain.entity.CityList
import com.example.siriustech.domain.entity.Coord
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityResponseMapper @Inject constructor() : Mapper<List<CityResponse>, CityList> {

    override fun map(input: List<CityResponse>): CityList {
        return input.map {
            City(
                id = it.id,
                name = it.name.orEmpty(),
                country = it.country.orEmpty(),
                coord = Coord(
                    lat = it.coord?.lat,
                    lon = it.coord?.lon
                )
            )
        }.let(::CityList)
    }

}