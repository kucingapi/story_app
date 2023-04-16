package com.example.storyapp.ui.fragment.stories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.storyapp.databinding.FragmentStoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoriesFragment : Fragment() {

    private val viewModel: StoriesViewModel by viewModels()
    private lateinit var binding: FragmentStoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoriesBinding.inflate(inflater, container , false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.getStories()
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            Log.d("StoriesFragmentVMObserve", "observeViewModel: $it")
        }
    }
}