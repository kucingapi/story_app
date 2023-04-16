package com.example.storyapp.ui.fragment.stories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.stories.ResponseStories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {
    val resultLiveData: MutableLiveData<Resource<ResponseStories>> by lazy {
        MutableLiveData<Resource<ResponseStories>>()
    }
    fun getStories(){
        viewModelScope.launch {
            dataRepository.getStories().collect {
                resultLiveData.postValue(it)
            }
        }
    }
}