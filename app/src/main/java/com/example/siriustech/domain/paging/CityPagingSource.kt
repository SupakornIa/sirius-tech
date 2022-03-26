package com.example.siriustech.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.siriustech.domain.BinarySearchUtil
import com.example.siriustech.domain.GetCityUseCase
import com.example.siriustech.domain.entity.City
import com.example.siriustech.domain.entity.CityList
import com.example.siriustech.domain.isOutOfRange

class CityPagingSource constructor(
    private val searchEfficiencyTimeConsume: ((Int, Double) -> Unit)?,
    private val cityList: CityList,
    private val query: String
) : PagingSource<Int, City>() {

    var currentQuery: String? = null


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        val position = params.key ?: STARTING_PAGE_INDEX

        // Start counting time  for searching algorithm
        val startTimeCountMillis = System.currentTimeMillis()

        val indexRange = BinarySearchUtil.findRange(
            query = query,
            binarySearches = cityList.cities
        )

        val totalRecordFound = when (indexRange.isOutOfRange()) {
            true -> 0
            else -> indexRange.second - indexRange.first + 1
        }

        val pageRange = BinarySearchUtil.findPageRange(
            currentPage = position,
            pageSize = params.loadSize,
            range = indexRange
        )

        val cities = when (pageRange.isOutOfRange()) {
            true -> emptyList()
            else -> {
                val startVirtualIndex = (position - 1) * params.loadSize
                (pageRange.first..pageRange.second).mapIndexed { index, searchIndex ->
                    val city = cityList.cities[searchIndex]
                    val virtualIndex = (index + 1) + startVirtualIndex
                    city.copy(
                        id = city.id,
                        name = "$virtualIndex. [$searchIndex] ${city.name}",
                        country = city.country,
                        coord = city.coord
                    )
                }
            }
        }

        val totalTimeConsumeMillis = System.currentTimeMillis() - startTimeCountMillis

        //End counting time for searching algorithm
        if (query != currentQuery) {
            searchEfficiencyTimeConsume?.invoke(totalRecordFound, totalTimeConsumeMillis / 1000.0)
        }

        val nextKey = if (cities.isEmpty()) {
            null
        } else {
            // initial load size = 3 * NETWORK_PAGE_SIZE
            // ensure we're not requesting duplicating items, at the 2nd request
            position + (params.loadSize / GetCityUseCase.PAGE_SIZE)
        }

        currentQuery = query

        return LoadResult.Page(
            data = cities,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = nextKey
        )
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, City>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}