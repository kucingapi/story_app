package com.example.storyapp.ui.fragment.stories

import android.os.Bundle
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
        return binding.root
    }
}