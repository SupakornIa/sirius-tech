package com.example.siriustech.domain.entity

import com.example.siriustech.domain.BinarySearchModel

data class City(
    val id: Int?,
    val name: String,
    val country: String,
    val coord: Coord?,
    override val searchName: String
) : BinarySearchModel