package com.rickykuang.homies.activities.main.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.rickykuang.homies.activities.main.fragments.EventsFragment
import com.rickykuang.homies.activities.main.fragments.MessagesFragment
import com.rickykuang.homies.activities.main.fragments.ShoppingListFragment

class MainPagerAdapter(fm : FragmentManager) :  FragmentPagerAdapter(fm) {

    private val MESSAGES = 0
    private val EVENTS = 1
    private val SHOPPING_LIST = 2

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        val fragment : Fragment? = null
//        fragment = MessagesFragment()
//        fragment.arguments = Bundle().apply {
//            putInt(PAGE, position+1)
//        }
//        return fragment
        when(position) {
            MESSAGES -> return MessagesFragment()
            EVENTS -> return EventsFragment()
            SHOPPING_LIST -> return ShoppingListFragment()
        }
        return fragment!!
    }

}
