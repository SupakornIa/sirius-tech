package com.example.siriustech.module

import com.example.siriustech.data.CityRepository
import com.example.siriustech.data.CityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideCityRepository(
        cityRepository: CityRepositoryImpl
    ): CityRepository

}