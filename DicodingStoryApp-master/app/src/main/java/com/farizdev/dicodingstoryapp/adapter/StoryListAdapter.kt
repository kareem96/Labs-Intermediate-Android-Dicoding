package com.farizdev.dicodingstoryapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farizdev.dicodingstoryapp.data.model.Story
import com.farizdev.dicodingstoryapp.databinding.ItemContentBinding
import com.farizdev.dicodingstoryapp.ui.detail.DetailActivity

class StoryListAdapter (private val stories: ArrayList<Story>): RecyclerView.Adapter<StoryListAdapter.MyViewHolder>(){
    inner class MyViewHolder (var binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root){}

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

        with(holder.itemView) {
            setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(holder.binding.ivStory, "photo"),
                        Pair(holder.binding.tvName, "name"),
                        Pair(holder.binding.tvDescription, "description"),
                    )

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.STORY, stories[position])
                context.startActivity(intent, optionsCompat.toBundle())
            }
        }

    }

    override fun getItemCount(): Int {
        return stories.size
    }
}