package com.example.storyapp.data

import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) : DataRepositorySource {
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
//        val resultLiveData = MutableLiveData<Resource<ResponseLogin>>()
//        val client = ApiConfig.apiService.login(requestLogin)
//        client.enqueue(object : Callback<ResponseLogin> {
//            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                if (response.isSuccessful) {
//                    resultLiveData.postValue(Resource.Success(response.body() as ResponseLogin))
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                resultLiveData.postValue(Resource.DataError(t.hashCode()))
//            }
//        })
//        return resultLiveData
    }
}