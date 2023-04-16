package com.example.storyapp.ui.fragment.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.DataRepositorySource
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val dataRepository: DataRepositorySource) : ViewModel() {
    val resultLiveData: MutableLiveData<Resource<ResponseRegister>> by lazy {
        MutableLiveData<Resource<ResponseRegister>>()
    }

    fun register(){
        viewModelScope.launch {
            val requestRegister =
                RequestRegister("novel bafagih", "novelbafagih09@gmail.com", "123456789")
            dataRepository.doRegister(requestRegister).collect {
                resultLiveData.postValue(it)
            }
        }
    }
}