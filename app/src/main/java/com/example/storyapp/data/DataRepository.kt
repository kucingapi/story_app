package com.example.storyapp.data

import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.ResponseDetail
import com.example.storyapp.data.api.story.ResponsePostStory
import com.example.storyapp.data.local.LoginInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.awaitResponse
import java.io.File
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService, private val loginInformation: LoginInformation) : DataRepositorySource {
    override suspend fun postStory(description: String, fileImage: File): Flow<Resource<ResponsePostStory>> {
        return flow {
            emit(Resource.Loading())
            val bearerToken = "Bearer ${loginInformation.getToken()}"
            val description = description.toRequestBody("text/plain".toMediaType())

            val requestImageFile = fileImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", fileImage.name, requestImageFile)
            val response = apiService.postStory(bearerToken, imageMultipart, description)
            response.awaitResponse().run {
                if(isSuccessful)
                    emit(Resource.Success(body()!!))
                else
                    emit(Resource.DataError(code()))
            }

        }
    }

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

    override suspend fun getStoryById(id: String): Flow<Resource<ResponseDetail>> {
        return flow{
            emit(Resource.Loading())
            val bearerToken = "Bearer ${loginInformation.getToken()}"
            val response = apiService.getStoryById(bearerToken, id)
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

    override fun deleteToken() {
        loginInformation.deleteToken()
    }

    override fun saveToken(token: String) {
        loginInformation.saveToken(token)
    }
}