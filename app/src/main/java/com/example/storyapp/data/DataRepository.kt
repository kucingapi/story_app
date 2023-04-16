package com.example.storyapp.data

import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.local.LoginInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService, private val loginInformation: LoginInformation) : DataRepositorySource {
    override suspend fun getStories(): Flow<Resource<ResponseStories>> {
        return flow{
            emit(Resource.Loading())
            val bearerToken = "Bearer ${loginInformation.getToken()}"
            val response = apiService.getStories(token = bearerToken)
            response.awaitResponse().run {
                if(isSuccessful)
                    emit(Resource.Success(body()!!))
                else
                    emit(Resource.DataError(code()))
            }
        }
    }

    override suspend fun doRegister(requestRegister: RequestRegister): Flow<Resource<ResponseRegister>> {
        return flow {
            emit(Resource.Loading())
            val response = apiService.register(requestRegister)
            response.awaitResponse().run {
                if(isSuccessful)
                    emit(Resource.Success(body()!!))
                else
                    emit(Resource.DataError(code()))
            }
        }
    }

    override suspend fun doLogin(requestLogin: RequestLogin): Flow<Resource<ResponseLogin>> {
        return flow {
            emit(Resource.Loading())
            val response = apiService.login(requestLogin)
            response.awaitResponse().run {
                if(isSuccessful)
                    emit(Resource.Success(body()!!))
                else
                    emit(Resource.DataError(code()))
            }
        }
    }

    override fun saveToken(token: String) {
        loginInformation.saveToken(token)
    }
}