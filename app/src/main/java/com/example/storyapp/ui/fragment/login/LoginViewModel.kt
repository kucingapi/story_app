package com.example.storyapp.ui.fragment.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.DataRepositorySource
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
): ViewModel() {
    val resultLiveData: MutableLiveData<Resource<ResponseLogin>> by lazy {
        MutableLiveData<Resource<ResponseLogin>>()
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            val requestLogin = RequestLogin( email, password)
            dataRepository.doLogin(requestLogin).collect{
                resultLiveData.postValue(it)
                it.data?.loginResult?.let { it1 ->
                    run {
                        dataRepository.saveToken(it1.token)
                    }
                }
            }
        }
    }
}