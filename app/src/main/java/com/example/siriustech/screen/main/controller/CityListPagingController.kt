package com.example.siriustech.screen.main.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.siriustech.CityModelBindingModel_
import com.example.siriustech.screen.main.model.CityListUi
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.ObsoleteCoroutinesApi
import javax.inject.Inject

@ObsoleteCoroutinesApi
@FragmentScoped
class CityListPagingController @Inject constructor() :
    PagingDataEpoxyController<CityListUi.CityUi>() {

    override fun buildItemModel(currentPosition: Int, item: CityListUi.CityUi?): EpoxyModel<*> {
        return CityModelBindingModel_().apply {
            id(item.hashCode())
            name(item?.name.orEmpty())
        }
    }

}