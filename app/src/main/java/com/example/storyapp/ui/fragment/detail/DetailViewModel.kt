package com.example.storyapp.ui.fragment.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.story.ResponseDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {
    val resultLiveData: MutableLiveData<Resource<ResponseDetail>> by lazy {
        MutableLiveData<Resource<ResponseDetail>>()
    }

    fun getDetail(id: String){
        viewModelScope.launch {
            dataRepository.getStoryById(id).collect{
                resultLiveData.postValue(it)
            }
        }
    }

}