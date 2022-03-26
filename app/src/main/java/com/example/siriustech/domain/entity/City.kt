package com.example.siriustech.domain.entity

data class City(
    val id: Int?,
    val name: String,
    val country: String,
    val coord: Coord?,
    val searchName: String
)