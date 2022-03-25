package com.example.siriustech.domain

import com.example.siriustech.base.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCityUseCase @Inject constructor(
) : BaseUseCase<Unit, Unit>() {
    override suspend fun create(input: Unit) {

    }
}