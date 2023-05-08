package com.example.storyapp.data

import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.ResponseDetail
import com.example.storyapp.data.api.story.ResponsePostStory
import kotlinx.coroutines.flow.Flow
import java.io.File

interface DataRepositorySource {
    suspend fun postStory(description: String, fileImage: File): Flow<Resource<ResponsePostStory>>
    suspend fun getStories(location: Int = 0): Flow<Resource<ResponseStories>>
    suspend fun getStoryById(id: String): Flow<Resource<ResponseDetail>>
    suspend fun doRegister(requestRegister: RequestRegister): Flow<Resource<ResponseRegister>>
    suspend fun doLogin(requestLogin: RequestLogin): Flow<Resource<ResponseLogin>>
    fun saveToken(token: String)
    fun deleteToken()
}