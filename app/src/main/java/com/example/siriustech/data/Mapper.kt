package com.example.siriustech.data

interface Mapper<E, D> {
    fun map(input: E): D
}