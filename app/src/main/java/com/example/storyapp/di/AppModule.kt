package com.example.storyapp.di

import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.DataRepositorySource
import com.example.storyapp.data.api.ApiConfig
import com.example.storyapp.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    @Named("api_service")
    fun provideApiService(): ApiService {
        return ApiConfig.apiService
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    fun provideDataRepository(): DataRepositorySource {
        return DataRepository()
    }
}