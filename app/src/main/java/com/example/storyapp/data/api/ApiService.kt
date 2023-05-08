package com.example.storyapp.data.api

import com.example.storyapp.data.api.login.RequestLogin
import com.example.storyapp.data.api.login.ResponseLogin
import com.example.storyapp.data.api.register.RequestRegister
import com.example.storyapp.data.api.register.ResponseRegister
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.ResponseDetail
import com.example.storyapp.data.api.story.ResponsePostStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int = 0
    ): Call<ResponseStories>

    @GET("stories/{id}")
    fun getStoryById(
        @Header("Authorization") token: String,
        @Path("id") id:String,
    ): Call<ResponseDetail>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ResponsePostStory>
}