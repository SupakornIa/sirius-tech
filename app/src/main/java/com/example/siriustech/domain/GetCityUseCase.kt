package com.example.siriustech.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.siriustech.base.BaseUseCase
import com.example.siriustech.data.CityRepository
import com.example.siriustech.domain.entity.City
import com.example.siriustech.domain.paging.CityPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) : BaseUseCase<GetCityUseCase.Input, Flow<PagingData<City>>>() {

    var searchEfficiencyTimeConsume: ((Int, Double) -> Unit)? = null

    override suspend fun create(input: Input): Flow<PagingData<City>> {
        val cityList = cityRepository.getCities()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PRE_FETCH_DISTANCE
            ),
            pagingSourceFactory = {
                CityPagingSource(
                    cityList = cityList,
                    query = input.query,
                    searchEfficiencyTimeConsume = searchEfficiencyTimeConsume
                )
            }
        ).flow
    }

    data class Input(
        val query: String
    )

    companion object {
        const val PRE_FETCH_DISTANCE = 40
        const val PAGE_SIZE = 100
    }
}