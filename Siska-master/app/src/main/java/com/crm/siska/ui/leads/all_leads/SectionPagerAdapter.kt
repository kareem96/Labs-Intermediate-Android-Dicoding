package com.crm.siska.ui.leads.all_leads

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.crm.siska.ui.leads.all_leads.aktif.AllAktifFragment
import com.crm.siska.ui.leads.all_leads.arsip.AllArsipFragment
import com.crm.siska.ui.leads.all_leads.hot.AllHotFragment

class SectionPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

//    var newTasteList:ArrayList<Data>? = ArrayList()
//    var popularList:ArrayList<Data>? = ArrayList()
//    var recommendedList:ArrayList<Data>? = ArrayList()

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
                fragment = AllAktifFragment()
                return fragment
            }
            1 -> {
                fragment = AllArsipFragment()
                return fragment
            }
            2 -> {
                fragment = AllHotFragment()
                return fragment
            }
            else -> {
                fragment = AllAktifFragment()
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