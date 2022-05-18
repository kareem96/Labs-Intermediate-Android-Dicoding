package com.kareemdev.latihanadvancedtesting.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.kareemdev.latihanadvancedtesting.R
import com.kareemdev.latihanadvancedtesting.data.local.entity.NewsEntity
import com.kareemdev.latihanadvancedtesting.databinding.ActivityNewsDetailBinding
import com.kareemdev.latihanadvancedtesting.databinding.FragmentNewsBinding
import com.kareemdev.latihanadvancedtesting.ui.ViewModelFactory

class ActivityNewsDetail : AppCompatActivity() {
    companion object{
        const val  NEWS_DATA = "data"
    }

    private lateinit var newsDetail: NewsEntity
    private lateinit var binding: ActivityNewsDetailBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: NewsDetailViewModel by viewModels { factory }
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsDetail = intent.getParcelableExtra<NewsEntity>(NEWS_DATA) as NewsEntity

        supportActionBar?.title = newsDetail.title
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(newsDetail.url.toString())

        viewModel.setNewsData(newsDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        viewModel.bookmarkStatus.observe(this) { status ->
            setStateBookmark(status)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_bookmark){
            viewModel.changeBookmark(newsDetail)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setStateBookmark(status: Boolean) {
        if(menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if(status){
            menuItem?.icon= ContextCompat.getDrawable(this, R.drawable.ic_bookmark_white)
        }else{
            menuItem?.icon= ContextCompat.getDrawable(this, R.drawable.ic_bookmarked_white)
        }
    }
}