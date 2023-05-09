package com.example.storyapp.util

import com.example.storyapp.data.api.story.Story

object DataDummy {
    fun generateDummyStories(): List<Story> {
        val storyList = ArrayList<Story>()
        for (i in 0..10) {
            storyList.add(
                Story(
                    i.toString(),
                    "Title",
                    "Story Desc",
                    "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                    "2022-02-22T22:22:22Z",
                    null,
                    null
                )
            )
        }
        return storyList
    }
}