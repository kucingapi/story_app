package com.example.storyapp.data.api.stories

import com.example.storyapp.data.api.story.Story
import com.google.gson.annotations.SerializedName

data class ResponseStories (
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("listStory") val stories: List<Story>
)