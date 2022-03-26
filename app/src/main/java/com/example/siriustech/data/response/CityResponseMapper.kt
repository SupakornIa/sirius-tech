package com.example.siriustech.data.response

import com.example.siriustech.data.Mapper
import com.example.siriustech.domain.entity.City
import com.example.siriustech.domain.entity.CityList
import com.example.siriustech.domain.entity.Coord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityResponseMapper @Inject constructor() : Mapper<List<CityResponse>, CityList> {

    override suspend fun map(input: List<CityResponse>): CityList =
        withContext(Dispatchers.Default) {
            input.map {
                City(
                    id = it.id,
                    name = it.name.orEmpty(),
                    country = it.country.orEmpty(),
                    searchName = "${it.name.orEmpty()} ${it.country.orEmpty()}".lowercase(),
                    coord = Coord(
                        lat = it.coord?.lat,
                        lon = it.coord?.lon
                    )
                )
            }.let { CityList(it.sortedBy(City::searchName)) }
        }

}