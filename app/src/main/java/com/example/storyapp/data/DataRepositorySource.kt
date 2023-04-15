package com.example.storyapp.data

import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    fun doRegister(requestRegister: RequestRegister): MutableLiveData<Resource<ResponseRegister>>
}