package com.example.snphmsapo.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class AdapterFragment(activity: FragmentActivity):FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> FragmentOrder()
            1-> FragmentProduct()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}