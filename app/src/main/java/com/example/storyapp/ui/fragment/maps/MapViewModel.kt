package com.example.storyapp.ui.fragment.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.DataRepositorySource
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.stories.ResponseStories
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
): ViewModel(){
    val resultLiveData: MutableLiveData<Resource<ResponseStories>> by lazy {
        MutableLiveData<Resource<ResponseStories>>()
    }

    fun getMapStory() {
        viewModelScope.launch {
            dataRepository.getStories(1).collect {
                resultLiveData.postValue(it)
            }
        }
    }
}