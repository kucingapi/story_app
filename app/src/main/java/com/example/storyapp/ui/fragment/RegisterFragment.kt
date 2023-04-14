package com.example.storyapp.ui.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.storyapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    companion object {
        private const val ANIMATION_TIME = 200L
        private const val START_DELAY = 800L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setAction()
        playAnimation()
        return binding.root
    }

    private fun setAction(){
        binding.loginButton.setOnClickListener {
            val destination = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(destination)
        }
    }

    private fun playAnimation() {
        val hero = binding.heroImage.animateAlpha()
        val name = binding.editTextName.animateAlpha()
        val email = binding.editTextEmailAddress.animateAlpha()
        val password = binding.editTextPassword.animateAlpha()
        val registerButton = binding.registerButton.animateAlpha()
        val loginButton = binding.loginButton.animateAlpha()
        AnimatorSet().apply {
            playSequentially(hero, name, email, password, registerButton, loginButton)
            startDelay = START_DELAY
            start()
        }
    }
    private fun View.animateAlpha(): ObjectAnimator {
        return ObjectAnimator.ofFloat(this, View.ALPHA, 1f).setDuration(ANIMATION_TIME)
    }
}