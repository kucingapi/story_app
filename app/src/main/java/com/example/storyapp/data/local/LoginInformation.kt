package com.example.storyapp.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class LoginInformation @Inject constructor(val context: Context, val sharedPref: SharedPreferences) {
    companion object {
        const val SHARED_PREFERENCES_LOGIN_INFORMATION = "LOGIN_INFORMATION"
        const val KEY_PREFERENCES_LOGIN_INFORMATION = "LOGIN_INFORMATION"
    }

    fun saveToken(token: String) {
        with (sharedPref.edit()) {
            putString(KEY_PREFERENCES_LOGIN_INFORMATION, token)
            commit()
        }
    }

    fun deleteToken(){
        with(sharedPref.edit()) {
            remove(KEY_PREFERENCES_LOGIN_INFORMATION)
            commit()
        }
    }

    fun getToken(): String{
        return sharedPref.getString(KEY_PREFERENCES_LOGIN_INFORMATION, "") ?: ""
    }

}