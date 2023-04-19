package com.example.storyapp.ui.fragment.addstory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentAddStoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStoryFragment : Fragment() {
    private lateinit var binding: FragmentAddStoryBinding
    private val viewModel: AddStoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}