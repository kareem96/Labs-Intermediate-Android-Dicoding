package com.kareemdev.dicodingstory.presentation.detail

import android.os.Bundle
import android.transition.ChangeBounds
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.databinding.FragmentDetailBinding
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    companion object {
        const val DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 500
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 500
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)

        arguments?.getParcelable<ListStoryItem>(DATA)?.let {
            binding.imageView.transitionName = "${it.id}image"
            binding.nameTextView.transitionName = "${it.id}name"
            binding.createdDate.transitionName = "${it.id}date"
            binding.descTextView.transitionName = "${it.id}desc"
            binding.nameTextView.text = it.name
            binding.nameTextView.text = it.description
            binding.createdDate.text =
                it.createdAt?.let { it1 ->
                    SimpleDateFormat(getString(R.string.isoFormat), Locale.getDefault()).parse(
                        it1
                    )?.let { it2 ->
                        SimpleDateFormat("dd MMMM, HH:mm", Locale.getDefault()).format(it2)
                    }
                }
            Glide.with(binding.root)
                .load(it.photoUrl)
                .apply(RequestOptions().transform(RoundedCorners(15)))
                .into(binding.imageView)
        }

        return binding.root
    }


}