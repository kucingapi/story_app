package com.example.storyapp.ui.fragment.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.ApiConfig.apiService
import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


class RegisterViewModel : ViewModel() {
    val resultLiveData: MutableLiveData<Resource<ResponseRegister>> by lazy {
        MutableLiveData<Resource<ResponseRegister>>()
    }

    fun register(){
        val requestRegister = RequestRegister("novel bafagih", "novelbafagih09@gmail.com", "123456789")

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
//        dataRepository.doRegister(requestRegister).observeForever {
//            resultLiveData.postValue(it)
//        }
    }
}