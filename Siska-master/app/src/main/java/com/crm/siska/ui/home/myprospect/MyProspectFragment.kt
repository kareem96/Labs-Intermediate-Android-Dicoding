package com.crm.siska.ui.home.myprospect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crm.siska.R
import kotlinx.android.synthetic.main.fragment_my_aktif.*
import kotlinx.android.synthetic.main.fragment_my_prospect.*

class MyProspectFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_prospect, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )

        viewPager.adapter = sectionPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        arrowBack.setOnClickListener {
            this.activity?.finish()
        }

    }
}

