package com.rickyandrean.a2320j2802_submissionintermediate.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.rickyandrean.a2320j2802_submissionintermediate.databinding.ActivityDetailBinding
import com.rickyandrean.a2320j2802_submissionintermediate.model.ListStoryItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        showData()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showData() {
        val story = intent.getParcelableExtra<ListStoryItem>(STORY) as ListStoryItem

        Glide.with(applicationContext)
            .load(story.photoUrl)
            .into(binding.ivDetailStory)
        binding.tvDetailNameValue.text = story.name
        binding.tvDetailDescriptionValue.text = story.description
    }

    companion object {
        const val STORY = "story"
    }
}