package com.example.storyapp.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.storyapp.data.local.LoginInformation
import com.example.storyapp.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    @Inject
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLoggedIn()
    }

    private fun checkLoggedIn() {
        val token = sharedPref.getString(LoginInformation.KEY_PREFERENCES_LOGIN_INFORMATION, "")
        Log.d("token", "$token")
        token?.let {
            if(token.isEmpty())
                return
            val intent = Intent(this, StoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}