package com.example.storyapp.data.api.story

import com.google.gson.annotations.SerializedName

data class ResponsePostStory(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
)