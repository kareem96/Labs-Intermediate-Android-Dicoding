package com.kareemdev.latihanadvancedtesting.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareemdev.latihanadvancedtesting.R
import com.kareemdev.latihanadvancedtesting.data.Result
import com.kareemdev.latihanadvancedtesting.ui.detail.ActivityNewsDetail
import com.kareemdev.latihanadvancedtesting.databinding.FragmentNewsBinding
import com.kareemdev.latihanadvancedtesting.ui.ViewModelFactory
import com.kareemdev.latihanadvancedtesting.ui.adapter.NewsAdapter


class NewsFragment : Fragment() {
    private var tabName:String? = null
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: NewsViewModel by viewModels {
            factory
        }

        val newsAdapter = NewsAdapter { news ->
            val intent = Intent(activity, ActivityNewsDetail::class.java)
            intent.putExtra(ActivityNewsDetail.NEWS_DATA, news)
            startActivity(intent)
        }

        if (tabName == TAB_NEWS) {
            viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            binding?.viewError?.root?.visibility = View.VISIBLE
                            binding?.viewError?.tvError?.text = getString(R.string.something_error)
                        }
                    }
                }
            }
        } else if (tabName == TAB_BOOKMARK) {
            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
                newsAdapter.submitList(bookmarkedNews)
                binding?.progressBar?.visibility = View.GONE
                binding?.viewError?.tvError?.text = getString(R.string.no_data)
                binding?.viewError?.root?.visibility =
                    if (bookmarkedNews.isNotEmpty()) View.GONE else View.VISIBLE
            }
        }

        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}