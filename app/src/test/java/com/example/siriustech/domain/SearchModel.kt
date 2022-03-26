package com.example.siriustech.domain

/**
 * [SearchModel] can be used in place of [com.example.siriustech.domain.entity.City]
 * since the Search algorithm use [com.example.siriustech.domain.entity.City.searchName]
 * for calculation.
 */
data class SearchModel(
    val name: String
) : BinarySearchModel {

    override val searchName: String get() = name.lowercase()
}