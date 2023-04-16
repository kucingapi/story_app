package com.example.storyapp.ui.fragment.login

import android.content.Intent
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
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.databinding.FragmentLoginBinding
import com.example.storyapp.ui.activity.StoryActivity
import com.example.storyapp.util.AnimatorUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        setAction()
        setInputAction()
        observeViewModel()
        playAnimation()
        return binding.root
    }

    private fun setAction() {
        binding.loginButton.setOnClickListener {
            val error = binding.passwordEditText.error
            if(error != null || password.isEmpty()){
                Toast.makeText(requireContext(), "Minimal Character is 8", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login()
            Log.d("setActionRegister", "setAction: $email $password")
        }
        binding.registerButton.setOnClickListener {
            val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(destination)
        }
    }

    private fun setInputAction() {
        binding.emailAddressEditText.addTextChangedListener {
            email = it.toString()
        }
        binding.passwordEditText.addTextChangedListener {
            password = it.toString()
        }
    }

    private fun observeViewModel() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            handleLoginResult(it)
        }
    }

    private fun handleLoginResult(status: Resource<ResponseLogin>) {
        when (status) {
            is Resource.Success -> status.data?.let {
                if(it.error){
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireActivity(), StoryActivity::class.java))
                requireActivity().finish()
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
            binding.emailAddressEditText,
            binding.passwordEditText,
            binding.loginButton,
            binding.registerButton
        )
    }

}