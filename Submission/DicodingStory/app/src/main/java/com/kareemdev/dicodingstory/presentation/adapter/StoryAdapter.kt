package com.kareemdev.dicodingstory.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.databinding.ItemStoryBinding
import com.kareemdev.dicodingstory.presentation.detail.DetailFragment
import com.kareemdev.dicodingstory.utils.limitWords
import com.kareemdev.dicodingstory.utils.withDateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StoryAdapter internal constructor(private var itemClickCallback: ItemClick): RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    private var stories: ArrayList<ListStoryItem> = arrayListOf()
    fun setData(stories: ArrayList<ListStoryItem>) {
        this.stories.clear()
        this.stories = stories
    }

    inner class StoryViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(position: Int) {
            val story = stories[position]
            binding.storyImageView.transitionName = "${story.id}image"
            binding.nameTextView.transitionName = "${story.id}name"
            binding.tvDesc.transitionName = "${story.id}desc"
            binding.tvCreatedDate.transitionName = "${story.id}date"
            binding.nameTextView.text = story.name
            binding.tvDesc.text = story.description?.limitWords()
            binding.tvCreatedDate.text = story.createdAt?.let { it1 ->
                SimpleDateFormat(
                    binding.root.context.getString(R.string.isoFormat),
                    Locale.getDefault()
                ).parse(
                    it1
                )?.let { it2 ->
                    SimpleDateFormat("dd MMMM, HH:mm", Locale.getDefault()).format(it2)
                }
            }
            Glide.with(itemView)
                .load(stories[position].photoUrl)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
                .into(binding.storyImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickCallback.onClick(holder.binding, stories[position])
        }
        holder.binding(position)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

}