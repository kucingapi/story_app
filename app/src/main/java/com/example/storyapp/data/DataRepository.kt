package com.example.storyapp.data

import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.api.ApiConfig.apiService
import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DataRepository : DataRepositorySource {
    override fun doRegister(requestRegister: RequestRegister): MutableLiveData<Resource<ResponseRegister>> {
        val resultLiveData = MutableLiveData<Resource<ResponseRegister>>()
        val client = apiService.register(requestRegister)
        client.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                if (response.isSuccessful) {
                    resultLiveData.postValue(Resource.Success(response.body() as ResponseRegister))
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                resultLiveData.postValue(Resource.DataError(t.hashCode()))
            }
        })
        return resultLiveData
    }
}