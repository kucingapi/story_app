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
//        sharedPref.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
//            Log.d("AuthActivitySharedPref", "$key ${sharedPreferences.all}")
//        }
        with (sharedPref.edit()) {
            putString(KEY_PREFERENCES_LOGIN_INFORMATION, token)
            commit()
        }
    }

//    fun getToken(): String{
//        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_LOGIN_INFORMATION, Context.MODE_PRIVATE)
//        return sharedPref.getString(KEY_PREFERENCES_LOGIN_INFORMATION, "") ?: ""
//    }

}