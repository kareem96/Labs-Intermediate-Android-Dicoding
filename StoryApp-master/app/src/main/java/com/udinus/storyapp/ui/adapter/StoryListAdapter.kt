package com.udinus.storyapp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udinus.storyapp.data.local.entity.Story
import com.udinus.storyapp.databinding.ItemStoryBinding
import com.udinus.storyapp.ui.detail.DetailActivity
import com.udinus.storyapp.ui.detail.DetailActivity.Companion.TOKEN_DETAIL
import com.udinus.storyapp.utils.setLocalDateFormat

class StoryListAdapter : PagingDataAdapter<Story, StoryListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, story: Story){
            binding.apply {
                Glide.with(context)
                    .load(story.photoUrl)
                    .into(ivStoryImage)
                tvStoryUsername.text = story.name
                tvStoryDescription.text = story.description
                tvStoryDate.setLocalDateFormat(story.createdAt)

                root.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            root.context as Activity,
                            Pair(ivStoryImage, "image"),
                            Pair(tvStoryUsername, "username"),
                            Pair(tvStoryDescription, "description"),
                            Pair(tvStoryDate, "date"),
                        )

                    Intent(context, DetailActivity::class.java).also {
                        it.putExtra(TOKEN_DETAIL, story)
                        context.startActivity(it, optionsCompat.toBundle())
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Story> =
            object : DiffUtil.ItemCallback<Story>() {
                override fun areItemsTheSame(
                    oldItem: Story,
                    newItem: Story
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Story,
                    newItem: Story
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(holder.itemView.context, getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}