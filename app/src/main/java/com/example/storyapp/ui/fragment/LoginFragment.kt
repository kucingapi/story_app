package com.example.storyapp.ui.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    companion object {
        private const val ANIMATION_TIME = 200L
        private const val START_DELAY = 800L
    }

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
        val hero = binding.heroImage.animateAlpha()
        val email = binding.emailAddressEditText.animateAlpha()
        val password = binding.passwordEditText.animateAlpha()
        val registerButton = binding.registerButton.animateAlpha()
        val loginButton = binding.loginButton.animateAlpha()
        AnimatorSet().apply {
            playSequentially(hero, email, password, loginButton, registerButton)
            startDelay = START_DELAY
            start()
        }
    }

    private fun View.animateAlpha(): ObjectAnimator {
        return ObjectAnimator.ofFloat(this, View.ALPHA, 1f).setDuration(ANIMATION_TIME)
    }
}