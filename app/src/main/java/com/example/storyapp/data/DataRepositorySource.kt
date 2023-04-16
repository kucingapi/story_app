package com.example.storyapp.data

import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun doRegister(requestRegister: RequestRegister): Flow<Resource<ResponseRegister>>
    suspend fun doLogin(requestLogin: RequestLogin): Flow<Resource<ResponseLogin>>
    fun saveToken(token: String)
}