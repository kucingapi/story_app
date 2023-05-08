package com.example.storyapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.storyapp.data.DataRepositorySource
import com.example.storyapp.data.api.ApiConfig
import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.local.LoginInformation
import com.example.storyapp.data.local.LoginInformation.Companion.SHARED_PREFERENCES_LOGIN_INFORMATION
import com.example.storyapp.data.paging.StoryPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLoginInformation(@ApplicationContext context: Context, sharedPreferences: SharedPreferences): LoginInformation {
        return LoginInformation(context, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiConfig.apiService
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_LOGIN_INFORMATION, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideStoryPagingSource(dataRepository: DataRepositorySource): StoryPagingSource {
        return StoryPagingSource(dataRepository)
    }


    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }
}