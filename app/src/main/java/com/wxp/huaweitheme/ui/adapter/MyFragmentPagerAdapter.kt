package com.wxp.huaweitheme.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/03
 *     desc   : viewpager2 adapter
 * </pre>
 */
class MyFragmentPagerAdapter constructor(fragmentActivity: FragmentActivity
                                         , fragments:List<Fragment>):FragmentStateAdapter(fragmentActivity) {

    private var fragments:List<Fragment>? = null

    init {
        this.fragments = fragments
    }

    override fun getItemCount(): Int {
        return fragments?.size!!
    }

    override fun createFragment(position: Int): Fragment {
        return fragments?.get(position)!!
    }
}