package com.farizdev.dicodingstoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farizdev.dicodingstoryapp.data.model.Story
import com.farizdev.dicodingstoryapp.databinding.ItemContentBinding

class StoryListAdapter (private val stories: ArrayList<Story>): RecyclerView.Adapter<StoryListAdapter.MyViewHolder>(){
    inner class MyViewHolder (var binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding){
            Glide.with(holder.itemView.context)
                .load(stories[position].photoUrl)
                .into(ivStory)
            tvName.text = stories[position].name
            tvDescription.text = stories[position].description
        }

    }

    override fun getItemCount(): Int {
        return stories.size
    }
}