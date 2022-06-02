package com.crm.siska.ui.leads.my_leads

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.crm.siska.ui.leads.my_leads.aktif.MyAktifFragment
import com.crm.siska.ui.leads.my_leads.arsip.MyArsipFragment
import com.crm.siska.ui.leads.my_leads.hot.MyHotFragment

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
                fragment = MyAktifFragment()
                return fragment
            }
            1 -> {
                fragment = MyArsipFragment()
                return fragment
            }
            2 -> {
                fragment = MyHotFragment()
                return fragment
            }
            else -> {
                fragment = MyAktifFragment()
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