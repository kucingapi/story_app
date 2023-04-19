package com.example.storyapp.ui.fragment.stories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.Story
import com.example.storyapp.databinding.FragmentStoriesBinding
import com.example.storyapp.ui.fragment.stories.adapter.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoriesFragment : Fragment() {

    private val viewModel: StoriesViewModel by viewModels()
    private lateinit var binding: FragmentStoriesBinding
    private val stories = listOf<Story>()
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoriesBinding.inflate(inflater, container , false)
        setRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.rvStories.layoutManager = LinearLayoutManager(requireContext())
        storyAdapter = StoryAdapter(requireContext(),stories)
        binding.rvStories.adapter = storyAdapter
    }

    private fun observeViewModel() {
        viewModel.getStories()
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            handleStoryResult(it)
        }
    }
    private fun handleStoryResult(status: Resource<ResponseStories>) {
        when (status) {
            is Resource.Success -> status.data?.let {
                if(it.error){
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    return
                }
                storyAdapter.postList(it.stories)
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }
            is Resource.DataError -> {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
        }
    }
}