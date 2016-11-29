package `in`.shpt.adapter

import `in`.shpt.fragments.ImagePager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by poovarasanv on 14/11/16.
 */

class ImagePagerAdapter : FragmentStatePagerAdapter {
    internal var bannerModels: List<String>? = null

    constructor(fm: FragmentManager) : super(fm) {
    }

    constructor(fm: FragmentManager, bannerModels: List<String>) : super(fm) {
        this.bannerModels = bannerModels
    }

    override fun getItem(position: Int): Fragment {
        return ImagePager.newInstance(bannerModels!![position])
    }

    override fun getCount(): Int {
        return bannerModels!!.size
    }
}
