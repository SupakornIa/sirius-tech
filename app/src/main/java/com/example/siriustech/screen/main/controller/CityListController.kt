package com.example.siriustech.screen.main.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.siriustech.cityModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityListController @Inject constructor() : TypedEpoxyController<Unit>() {

    override fun buildModels(data: Unit?) {
        (0..20).forEach {
            cityModel {
                id(it.hashCode())
                name(it.toString())
            }
        }
    }

}