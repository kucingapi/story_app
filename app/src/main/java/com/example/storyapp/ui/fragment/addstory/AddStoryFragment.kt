package com.example.storyapp.ui.fragment.addstory

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.example.storyapp.databinding.FragmentAddStoryBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AddStoryFragment : Fragment() {
    private lateinit var binding: FragmentAddStoryBinding
    private val viewModel: AddStoryViewModel by viewModels()
    private lateinit var currentPhotoPath: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val myFile = File(currentPhotoPath)

        myFile.let { file ->
            viewModel.rotateFile(file, true)
            binding.ivItemPhoto.setImageBitmap(BitmapFactory.decodeFile(file.path))
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = viewModel.uriToFile(uri, this)
                myFile?.let{
                    binding.ivItemPhoto.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        setUpAction()
        return binding.root
    }

    private fun setUpAction() {
        binding.buttonAddPhotoCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.buttonAddPhotoGallery.setOnClickListener {
            startGallery()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        viewModel.createCustomTempFile(this)?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.storyapp.ui.fragment.addstory",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
}