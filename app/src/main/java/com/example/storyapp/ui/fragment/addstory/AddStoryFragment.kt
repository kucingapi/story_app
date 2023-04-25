package com.example.storyapp.ui.fragment.addstory

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.story.ResponsePostStory
import com.example.storyapp.databinding.FragmentAddStoryBinding
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddStoryFragment : Fragment() {
    private lateinit var binding: FragmentAddStoryBinding
    private val viewModel: AddStoryViewModel by viewModels()
    private lateinit var currentPhotoPath: String
    private lateinit var imageSelected: File
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private val REQUEST_CODE_PERMISSIONS = 10

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                lifecycleScope.launch {
                    val compressedImageFile =
                        Compressor.compress(requireContext(), file, Dispatchers.Main)
                    imageSelected = compressedImageFile
                }
                viewModel.rotateFile(file, true)
                binding.ivItemPhoto.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
        else{
            Toast.makeText(requireContext(), "Make sure to take the picture", Toast.LENGTH_SHORT).show()
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
                    lifecycleScope.launch {
                        val compressedImageFile = Compressor.compress(requireContext(), it, Dispatchers.Main) {
                            size(1_048_575)
                        }
                        imageSelected = compressedImageFile
                    }
                    binding.ivItemPhoto.setImageURI(uri)
                }
            }
        }
        else{
            Toast.makeText(requireContext(), "Make sure to select a picture", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        setUpAction()
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        observeViewModel()
        return binding.root
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setUpAction() {
        binding.buttonAddPhotoCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.buttonAddPhotoGallery.setOnClickListener {
            startGallery()
        }
        binding.buttonAdd.setOnClickListener {
            viewModel.postStory(binding.edAddDescription.text.toString(), imageSelected)
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

    private fun observeViewModel() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            handleResponsePost(it)
        }
    }

    private fun handleResponsePost(status: Resource<ResponsePostStory>) {
        when (status) {
            is Resource.Success -> status.data?.let {
                if(it.error){
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                val destination = AddStoryFragmentDirections.actionAddStoryFragment2ToStoriesFragment()
                findNavController().navigate(destination)
            }
            is Resource.DataError -> {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
        }
    }

}