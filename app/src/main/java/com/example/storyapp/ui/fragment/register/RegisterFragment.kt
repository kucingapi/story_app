package com.example.storyapp.ui.fragment.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.register.ResponseRegister
import com.example.storyapp.databinding.FragmentRegisterBinding
import com.example.storyapp.util.AnimatorUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var name = ""
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setInputAction()
        setAction()
        observeViewModel()
        playAnimation()
        return binding.root
    }


    private fun setInputAction() {
        binding.editTextName.addTextChangedListener {
            name = it.toString()
        }
        binding.editTextEmailAddress.addTextChangedListener {
            email = it.toString()
        }
        binding.editTextPassword.addTextChangedListener {
            password = it.toString()
        }
    }


    private fun setAction(){
        binding.registerButton.setOnClickListener {
            val error = binding.editTextPassword.error
            if(error != null || password.isEmpty()){
                Toast.makeText(requireContext(), "Minimal Character is 8", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.register()
            Log.d("setActionRegister", "setAction: $name $email $password")
        }
        binding.loginButton.setOnClickListener {
            val destination = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(destination)
        }
    }

    private fun observeViewModel() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            handleRegisterResult(it)
        }
    }

    private fun handleRegisterResult(status: Resource<ResponseRegister>) {
        when (status) {
            is Resource.Success -> status.data?.let {
                if(it.error){
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    return
                }
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }
            is Resource.DataError -> {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
        }
    }


    private fun playAnimation() {
        AnimatorUtil.playSequentialAlphaAnimation(
            binding.heroImage,
            binding.editTextName,
            binding.editTextEmailAddress,
            binding.editTextPassword,
            binding.registerButton,
            binding.loginButton,
        )
    }
}