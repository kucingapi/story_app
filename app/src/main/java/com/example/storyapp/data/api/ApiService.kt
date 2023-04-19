package com.example.storyapp.data.api

import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.ResponseDetail
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    fun register(@Body requestRegister: RequestRegister): Call<ResponseRegister>

    @POST("login")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLogin>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("location") location: Int = 0
    ): Call<ResponseStories>

    @GET("stories/{id}")
    fun getStoryById(
        @Header("Authorization") token: String,
        @Path("id") id:String,
    ): Call<ResponseDetail>
}