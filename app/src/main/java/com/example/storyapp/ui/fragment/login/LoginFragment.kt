package com.example.storyapp.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.storyapp.databinding.FragmentLoginBinding
import com.example.storyapp.util.AnimatorUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        setAction()
        playAnimation()
        return binding.root
    }

    private fun setAction() {
        binding.registerButton.setOnClickListener {
            val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(destination)
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