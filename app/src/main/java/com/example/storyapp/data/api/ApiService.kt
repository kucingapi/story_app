package com.example.storyapp.data.api

import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun register(@Body requestRegister: RequestRegister): Call<ResponseRegister>
}