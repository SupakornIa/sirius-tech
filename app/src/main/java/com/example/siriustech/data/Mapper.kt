package com.example.siriustech.data

interface Mapper<E, D> {
    suspend fun map(input: E): D
}