package com.crm.siska.ui.leads

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.crm.siska.ui.leads.all_leads.AllLeadsFragment
import com.crm.siska.ui.leads.dev_leads.DevLeadsFragment
import com.crm.siska.ui.leads.my_leads.MyLeadsFragment

class SectionPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

//    var newTasteList:ArrayList<Data>? = ArrayList()
//    var popularList:ArrayList<Data>? = ArrayList()
//    var recommendedList:ArrayList<Data>? = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "All Leads"
            1 -> "Dev Leads"
            2 -> "My Leads"
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
                fragment = AllLeadsFragment()
                return fragment
            }
            1 -> {
                fragment = DevLeadsFragment()
                return fragment
            }
            2 -> {
                fragment = MyLeadsFragment()
                return fragment
            }
            else -> {
                fragment = AllLeadsFragment()
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