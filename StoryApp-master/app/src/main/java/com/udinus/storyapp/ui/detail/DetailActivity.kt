package com.udinus.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udinus.storyapp.R

class DetailActivity : AppCompatActivity() {

    companion object{
        const val TOKEN_DETAIL = "TOKEN_DETAIL"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}