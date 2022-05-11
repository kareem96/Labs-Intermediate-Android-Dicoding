package com.kareemdev.dicodingstory.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.databinding.FragmentHomeStoryBinding
import com.kareemdev.dicodingstory.databinding.ItemStoryBinding
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.domain.viewmodel.StoryViewModel
import com.kareemdev.dicodingstory.presentation.adapter.ItemClick
import com.kareemdev.dicodingstory.presentation.adapter.LoadingStateAdapter
import com.kareemdev.dicodingstory.presentation.adapter.StoryAdapter
import com.kareemdev.dicodingstory.presentation.adapter.StoryPagingAdapter
import com.kareemdev.dicodingstory.presentation.detail.DetailFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [HomeStoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeStoryFragment : Fragment(), ItemClick{
    private lateinit var binding: FragmentHomeStoryBinding
    private val storyViewModel by sharedViewModel<StoryViewModel>()

    private lateinit var storyViewAdapter: StoryAdapter
    private lateinit var storyPagingAdapter: StoryPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeStoryBinding.inflate(layoutInflater)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        storyViewAdapter = StoryAdapter(this)
        storyPagingAdapter = StoryPagingAdapter(this)
        binding.swipe.setOnRefreshListener {
            getDataPaging(storyPagingAdapter)
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeStoryFragment_to_addStoryFragment)
        }

        getDataPaging(storyPagingAdapter)

        return binding.root
    }

    private fun getDataPaging(storyPagingAdapter: StoryPagingAdapter) {
        binding.rv.adapter = storyPagingAdapter.withLoadStateFooter(LoadingStateAdapter(retry = { storyPagingAdapter.retry()}))
        storyViewModel.getListStories().observe(viewLifecycleOwner){
            storyPagingAdapter.submitData(lifecycle, it)
        }
    }

    private fun getData() {
        storyViewModel.storyViewState.observe(viewLifecycleOwner) {

            when (it) {
                is StateHandler.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is StateHandler.Success -> {
                    binding.swipe.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                    it.data?.listStory?.let { stories ->
                        if (storyViewModel.updateStory()) {
                            binding.rv.smoothScrollToPosition(0)
                        }
                        val adapter = StoryAdapter(this)
                        adapter.setData(ArrayList(stories))
                        storyViewAdapter = adapter
                        binding.rv.adapter = storyViewAdapter


                    }
                }
                is StateHandler.Error -> {
                    binding.swipe.isRefreshing = false
                    binding.progressBar.visibility = View.GONE

                }
                else -> {

                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        storyViewModel.getStories("1")
    }
    override fun onClick(binding: ItemStoryBinding, story: ListStoryItem) {
        val bundle = bundleOf(DetailFragment.DATA to story)
        val extras = FragmentNavigatorExtras(
            binding.storyImageView to "${story.id}image",
            binding.tvDesc to "${story.id}desc",
            binding.tvCreatedDate to "${story.id}date",
            binding.nameTextView to "${story.id}name",
        )
        findNavController().navigate(R.id.detailFragment2, bundle, null, extras)
    }


}