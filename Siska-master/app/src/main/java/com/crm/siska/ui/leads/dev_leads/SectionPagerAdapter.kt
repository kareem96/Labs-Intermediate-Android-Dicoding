package com.crm.siska.ui.leads.dev_leads

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.crm.siska.ui.leads.dev_leads.aktif.DevAktifFragment
import com.crm.siska.ui.leads.dev_leads.arsip.DevArsipFragment
import com.crm.siska.ui.leads.dev_leads.hot.DevHotFragment

class SectionPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Aktif"
            1 -> "Arsip"
            2 -> "Hot"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment
        return when(position) {
            0 -> {
                fragment = DevAktifFragment()
                return fragment
            }
            1 -> {
                fragment = DevArsipFragment()
                return fragment
            }
            2 -> {
                fragment = DevHotFragment()
                return fragment
            }
            else -> {
                fragment = DevAktifFragment()
                return fragment
            }
        }
    }

//    fun setData(newTasteListParms : ArrayList<Data>?, popularListParms : ArrayList<Data>?, recomendedListParms : ArrayList<Data>?) {
//        newTasteList = newTasteListParms
//        popularList = popularListParms
//        recommendedList = recomendedListParms
//
//    }
}