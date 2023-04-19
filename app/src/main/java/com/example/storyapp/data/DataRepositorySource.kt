package com.example.storyapp.data

import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.ResponseDetail
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun getStories(): Flow<Resource<ResponseStories>>
    suspend fun getStoryById(id: String): Flow<Resource<ResponseDetail>>
    suspend fun doRegister(requestRegister: RequestRegister): Flow<Resource<ResponseRegister>>
    suspend fun doLogin(requestLogin: RequestLogin): Flow<Resource<ResponseLogin>>
    fun saveToken(token: String)
}