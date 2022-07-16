package com.englizya.route

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.englizya.common.base.BaseFragment
import com.englizya.route.adapter.PagerAdapter
import com.englizya.route.databinding.FragmentRouteBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouteFragment : BaseFragment() {

    private lateinit var binding: FragmentRouteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        changeStatusBarColor(R.color.grey_100)

        binding = FragmentRouteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabBar()
        setUpListeners()
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.internalRoutes)
                1 -> tab.text = getString(R.string.externalRoutes)

            }
        }.attach()
    }

    private fun setUpTabBar() {
        val pagerAdapter = activity?.let { PagerAdapter(it, binding.tabLayout.tabCount) }!!
        binding.viewPager.adapter = pagerAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        }
        )

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                binding.viewPager.currentItem = tab.position
                Log.d("onTabSelected", "onTabSelected : $position")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("onTabUnselected", "onTabUnselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("onTabReselected", "onTabReselected")

            }

        })

    }

    private fun setUpListeners() {
        binding.tabLayout.setOnClickListener(View.OnClickListener {
            Log.d("Clicked", "Clicked")
            binding.viewPager.currentItem = 1

        })

        binding.back.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })

    }


}