package com.example.storyapp.ui.fragment.stories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.data.api.story.Story
import com.example.storyapp.databinding.ItemStoryBinding

class StoryAdapter(private val context: Context,private var stories: List<Story>) :
    RecyclerView.Adapter<StoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemBinding =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(context,itemBinding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.bind(story.name, story.photoUrl)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    fun postList(newList: List<Story>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return stories.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return stories[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return stories[oldItemPosition] == newList[newItemPosition]
            }
        })

        stories = newList
        diffResult.dispatchUpdatesTo(this)
    }
}