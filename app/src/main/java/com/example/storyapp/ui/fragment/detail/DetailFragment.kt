package com.example.storyapp.ui.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.story.ResponseDetail
import com.example.storyapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        getDetail()
        observeViewModel()
        return binding.root
    }

    private fun getDetail() {
        id = DetailFragmentArgs.fromBundle(arguments as Bundle).id
        viewModel.getDetail(id)
    }

    private fun observeViewModel() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            handleDetailResult(it)
        }
    }

    private fun handleDetailResult(status: Resource<ResponseDetail>) {
        when (status) {
            is Resource.Success -> status.data?.let {
                if(it.error){
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    return
                }
                with(it.story){
                    binding.tvItemName.text = name
                    binding.tvItemDescription.text = description
                }
                Glide.with(this).load(it.story.photoUrl).into(binding.ivItemPhoto)
            }
            is Resource.DataError -> {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
        }
    }


}


