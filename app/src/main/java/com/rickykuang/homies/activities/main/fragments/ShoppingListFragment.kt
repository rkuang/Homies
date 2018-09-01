package com.rickykuang.homies.activities.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rickykuang.homies.R

class ShoppingListFragment : Fragment() {

    val TAG = "Messages Fragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        return v
    }
}