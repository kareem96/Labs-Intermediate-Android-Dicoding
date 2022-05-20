package com.udinus.storyapp.ui.detail

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.udinus.storyapp.R
import com.udinus.storyapp.data.local.entity.Story
import com.udinus.storyapp.databinding.ActivityDetailBinding
import com.udinus.storyapp.utils.setLocalDateFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    companion object{
        const val TOKEN_DETAIL = "TOKEN_DETAIL"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportPostponeEnterTransition()

        val story = intent.getParcelableExtra<Story>(TOKEN_DETAIL)
        parseStoryData(story)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun parseStoryData(story: Story?){
        if(story != null){
            binding.apply {
                toolbar.title = story.name
                tvStoryUsername.text = story.name
                tvStoryDescription.text = story.description
                tvStoryDate.setLocalDateFormat(story.createdAt)

                Glide.with(this@DetailActivity)
                    .load(story.photoUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            supportStartPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            supportStartPostponedEnterTransition()
                            return false
                        }

                    })
                    .placeholder(R.drawable.image_loading_placeholder)
                    .error(R.drawable.image_load_error)
                    .into(ivStoryImage)
            }
        }
    }
}