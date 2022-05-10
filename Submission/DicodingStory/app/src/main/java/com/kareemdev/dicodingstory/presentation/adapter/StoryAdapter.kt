package com.kareemdev.dicodingstory.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.databinding.ItemStoryBinding
import com.kareemdev.dicodingstory.presentation.detail.DetailActivity
import com.kareemdev.dicodingstory.utils.withDateFormat

class StoryAdapter internal constructor(
    private var itemClickCallback: ItemClick
): RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    private var stories: ArrayList<ListStoryItem> = arrayListOf()
    fun setData(stories: ArrayList<ListStoryItem>){
        this.stories.clear()
        this.stories=stories
    }


    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
    inner class StoryViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            /*binding.root.setOnClickListener {
                itemClickCallback?.itemClicked(story)
            }*/
            val story = stories[position]
            binding.apply {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .apply(RequestOptions())
                    .into(storyImageView)
                nameTextView.text = story.name
                descTextView.text = itemView.context.getString(R.string.user_created_at, story.createdAt?.withDateFormat())

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("story", story)
                }
            }
        }
    }

    /*fun setOnItemClickCallback(ItemClickCallback: OnItemClickCallback){
        this.itemClickCallback = ItemClickCallback
    }*/

    interface OnItemClickCallback{
        fun itemClicked(story: ListStoryItem)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        /*val data = getItem(position)
        if(data != null){
            holder.bind(data)
        }*/
        holder.itemView.setOnClickListener {
            itemClickCallback.onClick(holder.binding, stories[position])
        }
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun getItemCount(): Int = stories.size

}