package com.osung.githubstars.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.osung.githubstars.R
import com.osung.githubstars.databinding.ActivityMainBinding
import com.osung.githubstars.view.favorite.FavoriteFragment
import com.osung.githubstars.view.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val tabTitle by lazy { resources.getStringArray(R.array.page_name) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //ViewPager2 - TabLayout 연결
        with(binding) {
            pageContainer.adapter = ViewPagerAdapter(this@MainActivity)

            TabLayoutMediator(tabLayout, pageContainer) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
        }
    }

    private inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> SearchFragment.newInstance()
                else -> FavoriteFragment.newInstance()
            }
        }
        override fun getItemCount(): Int = tabTitle.size
    }
}