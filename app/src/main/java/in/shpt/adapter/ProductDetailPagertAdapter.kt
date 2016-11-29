package `in`.shpt.adapter

import `in`.shpt.fragments.ProductDetailAttributeTab
import `in`.shpt.fragments.ProductDetailDescriptionTab
import `in`.shpt.fragments.ProductDetailTab
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import org.json.JSONObject

/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailPagertAdapter(fm: FragmentManager, internal var result: JSONObject, internal var tabCount: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return ProductDetailTab(result)
            1 -> return ProductDetailAttributeTab(result)
            2 -> return ProductDetailDescriptionTab(result)
            else -> return null
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Detail"
            1 -> return "Specs"
            2 -> return "Description"
            else -> return ""
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}
