package com.example.storyapp.ui.fragment.stories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.Story
import com.example.storyapp.databinding.FragmentStoriesBinding
import com.example.storyapp.ui.fragment.stories.adapter.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@AndroidEntryPoint
class StoriesFragment : Fragment() {

    private val viewModel: StoriesViewModel by viewModels()
    private lateinit var binding: FragmentStoriesBinding
    private lateinit var storyAdapter: StoryAdapter
    private var itemSize: Int = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoriesBinding.inflate(inflater, container , false)
        setActionButton()
        setRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setActionButton() {
        binding.fabAddStory.setOnClickListener {
            val toAddStoryFragment =
                StoriesFragmentDirections.actionStoriesFragmentToAddStoryFragment()
            findNavController().navigate(toAddStoryFragment)
        }
        binding.btMap.setOnClickListener {
            val destination = StoriesFragmentDirections.actionStoriesFragmentToMapsFragment(itemSize)
            findNavController().navigate(destination)
        }
    }

    private fun setRecyclerView() {
        binding.rvStories.layoutManager = LinearLayoutManager(requireContext())
        storyAdapter = StoryAdapter(requireContext())
        binding.rvStories.adapter = storyAdapter
        storyAdapter.addLoadStateListener { loadState ->
            when(loadState.refresh) {
                is LoadState.Loading -> {
                    // Handle loading state
                }
                is LoadState.Error -> {
                    // Handle error state
                }
                is LoadState.NotLoading -> {
                    // Handle success state
                    itemSize = storyAdapter.itemCount
                }

                else -> {}
            }
        }    }

    private fun observeViewModel() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {

            Log.d("handleStoryResult", "handleStoryResult: test")
            handleStoryResult(it)
        }
    }
    private fun handleStoryResult(pagingStory: PagingData<Story>) {
        lifecycleScope.launch {
            storyAdapter.submitData(pagingStory)
            val snapshot = storyAdapter.snapshot()
            Log.d("handleStoryResult", "handleStoryResult: ${snapshot.size}")
        }
    }
}