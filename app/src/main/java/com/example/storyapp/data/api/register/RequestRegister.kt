package com.example.storyapp.data.api.register

import com.google.gson.annotations.SerializedName

data class RequestRegister (
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)