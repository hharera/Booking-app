package com.englizya.route.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.englizya.route.ExternalRouteFragment
import com.englizya.route.InternalRouteFragment

class PagerAdapter (fm : FragmentActivity , private val tabCount : Int)
    :FragmentStateAdapter(fm){
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
      return when (position){
          0 -> InternalRouteFragment()
          1 -> ExternalRouteFragment()
          else -> {InternalRouteFragment()}
      }
    }

}