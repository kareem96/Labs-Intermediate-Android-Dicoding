package com.kareemdev.dicodingstory.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.databinding.ItemStoryBinding
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.utils.limitWords
import java.text.SimpleDateFormat
import java.util.*

class StoryPagingAdapter(private val itemClick: ItemClick) : PagingDataAdapter<ListStoryItem, StoryPagingAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.binding(data)

        }
    }

    inner class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(story: ListStoryItem) {
            itemView.setOnClickListener {
                itemClick.onClick(binding, story)
            }
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
                .load(story.photoUrl)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
                .into(binding.storyImageView)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}