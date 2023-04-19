package com.example.storyapp.ui.fragment.stories.adapter

import android.content.Context
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.ui.fragment.stories.StoriesFragmentDirections

class StoryViewHolder(private val context: Context, private val itemBinding: ItemStoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(name: String, imageUrl: String, id: String) {
        Glide.with(context).load(imageUrl).into(itemBinding.ivItemPhoto)
        itemBinding.tvItemName.text = name
        itemBinding.itemContainer.setOnClickListener {
            val toDetailUserFragment =
                StoriesFragmentDirections.actionStoriesFragmentToDetailFragment(id)
            it.findNavController().navigate(toDetailUserFragment)
        }
    }
}

