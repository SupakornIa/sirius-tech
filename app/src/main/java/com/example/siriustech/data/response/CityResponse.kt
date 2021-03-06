package com.example.siriustech.data.response

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("_id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
) {
    data class Coord(
        @SerializedName("lat")
        val lat: Double?,
        @SerializedName("lon")
        val lon: Double?
    )
}