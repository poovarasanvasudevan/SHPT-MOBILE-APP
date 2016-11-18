package `in`.shpt.adapter

import `in`.shpt.fragments.Banner
import `in`.shpt.models.BannerModel
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by poovarasanv on 14/11/16.
 */

class BannerAdapter : FragmentStatePagerAdapter {
    internal var bannerModels: List<BannerModel>? = null

    constructor(fm: FragmentManager) : super(fm) {
    }

    constructor(fm: FragmentManager, bannerModels: List<BannerModel>) : super(fm) {
        this.bannerModels = bannerModels
    }

    override fun getItem(position: Int): Fragment {
        return Banner.newInstance(bannerModels!![position])
    }

    override fun getCount(): Int {
        return bannerModels!!.size
    }
}
