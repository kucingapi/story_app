package com.example.storyapp.ui.fragment.stories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {
    fun getStoryPaging(): LiveData<PagingData<Story>> =
        dataRepository.getStoriesPaging().cachedIn(viewModelScope)

}