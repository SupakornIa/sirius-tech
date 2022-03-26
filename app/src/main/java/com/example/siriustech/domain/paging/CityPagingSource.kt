package com.example.siriustech.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.siriustech.domain.GetCityUseCase
import com.example.siriustech.domain.entity.City
import com.example.siriustech.domain.entity.CityList
import com.example.siriustech.domain.entity.Coord
import kotlinx.coroutines.delay

class CityPagingSource constructor(
    private val searchEfficiencyTimeConsume: ((Int, Double) -> Unit)?,
    private val cityList: CityList,
    private val query: String
) : PagingSource<Int, City>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        val position = params.key ?: STARTING_PAGE_INDEX

        val startTimeCountMillis = System.currentTimeMillis()
        var result = (0 until if (position == 1) 100 * 3 - 30 else 100).map {
            City(
                id = it,
                name = "$position city $it ${params.loadSize}",
                country = "US",
                Coord(0.0, 0.0),
                searchName = "it it it"
            )
        }

        if (position == 15) result = emptyList()

        delay(10)
        val totalTimeConsumeMillis = System.currentTimeMillis() - startTimeCountMillis

        searchEfficiencyTimeConsume?.invoke(300, totalTimeConsumeMillis / 1000.0)

        val nextKey = if (result.isEmpty()) {
            null
        } else {
            // initial load size = 3 * NETWORK_PAGE_SIZE
            // ensure we're not requesting duplicating items, at the 2nd request
            position + (params.loadSize / GetCityUseCase.PAGE_SIZE)
        }

        return LoadResult.Page(
            data = result,
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
}