package com.example.siriustech.module

import com.example.siriustech.service.CityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideCityService(
        retrofit: Retrofit
    ): CityService = retrofit.create(CityService::class.java)

}