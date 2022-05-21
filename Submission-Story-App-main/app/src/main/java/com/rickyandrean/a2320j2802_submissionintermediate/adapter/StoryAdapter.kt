package com.rickyandrean.a2320j2802_submissionintermediate.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.core.util.Pair
import com.rickyandrean.a2320j2802_submissionintermediate.databinding.ItemRowStoryBinding
import com.rickyandrean.a2320j2802_submissionintermediate.model.ListStoryItem
import com.rickyandrean.a2320j2802_submissionintermediate.ui.detail.DetailActivity

class StoryAdapter(private val stories: ArrayList<ListStoryItem>) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    inner class StoryViewHolder(var binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        with(holder.binding) {
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

    override fun getItemCount(): Int = stories.size
}