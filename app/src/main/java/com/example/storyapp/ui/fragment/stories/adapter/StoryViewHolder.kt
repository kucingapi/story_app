package com.example.storyapp.ui.fragment.stories.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ItemStoryBinding

class StoryViewHolder(private val context: Context, private val itemBinding: ItemStoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(name: String, imageUrl: String) {
        Glide.with(context).load(imageUrl).into(itemBinding.ivItemPhoto);
        itemBinding.tvItemName.text = name
    }
}

