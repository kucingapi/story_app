package com.example.storyapp.data.api.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @SerializedName("loginResult")
    val loginResult: LoginResult
)

data class LoginResult(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("token") val token: String
)