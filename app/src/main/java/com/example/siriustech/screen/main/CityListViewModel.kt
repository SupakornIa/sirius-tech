package com.example.siriustech.screen.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.siriustech.base.BaseViewModel
import com.example.siriustech.base.MutableConsumableLiveEvent
import com.example.siriustech.domain.GetCityUseCase
import com.example.siriustech.domain.entity.City
import com.example.siriustech.screen.main.model.CityListUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val getCityUseCase: GetCityUseCase
) : BaseViewModel() {

    val pagingDataLiveData = MediatorLiveData<PagingData<CityListUi.CityUi>>()
    val queryLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val searchEfficiencyTimeConsumeDisplayLiveData = MutableLiveData<String>()

    val onClickCityLiveEvent = MutableConsumableLiveEvent<Pair<Double?, Double?>>()

    fun search(query: String) {
        if (queryLiveData.value == query) return

        queryLiveData.value = query
        viewModelScope.launch { fetchCities(query) }
    }

    private suspend fun fetchCities(query: String) {
        isLoadingLiveData.value = true
        getCityUseCase.searchEfficiencyTimeConsume = { recordsFound, timeConsumeMillis ->
            searchEfficiencyTimeConsumeDisplayLiveData.value =
                "$recordsFound records found in $timeConsumeMillis milliseconds."
        }
        getCityUseCase.execute(GetCityUseCase.Input(query = query))
            .onSuccess(::onFetchSuccess)
            .onFailure(::onFetchFailure)
    }

    private fun onFetchFailure(throwable: Throwable) {
        isLoadingLiveData.value = false
        // Implement for some errors could happen
    }


    private fun onFetchSuccess(pagingFlow: Flow<PagingData<City>>) {
        isLoadingLiveData.value = false
        val liveData = pagingFlow.map { source ->
            source.mapToUi()
        }
            .cachedIn(viewModelScope)
            .asLiveData(Dispatchers.Main)

        pagingDataLiveData.apply {
            removeSource(liveData)
            addSource(liveData) { value = it }
        }
    }

    private fun PagingData<City>.mapToUi(): PagingData<CityListUi.CityUi> {
        return map {
            CityListUi.CityUi(
                id = it.id,
                name = it.name,
                country = it.country,
                latitude = it.coord?.lat,
                longitude = it.coord?.lon,
                onClick = {
                    onClickCityLiveEvent.value = it.coord?.lat to it.coord?.lon
                }
            )
        }
    }
}