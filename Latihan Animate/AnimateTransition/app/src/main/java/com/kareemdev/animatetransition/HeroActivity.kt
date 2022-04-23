package com.kareemdev.animatetransition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kareemdev.animatetransition.databinding.ActivityHeroBinding
import com.kareemdev.animatetransition.databinding.ActivityMainBinding
import com.kareemdev.animatetransition.model.Hero

class HeroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Hero"
        setupData()
    }

    private fun setupData() {
        val hero = intent.getParcelableExtra<Hero>("Hero") as Hero
        Glide.with(applicationContext)
            .load(hero.photo)
            .circleCrop()
            .into(binding.profileImageView)
        binding.nameTextView.text = hero.name
        binding.descTextView.text = hero.description
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}