package `in`.shpt.adapter

import `in`.shpt.fragments.ProductDetailAttributeTab
import `in`.shpt.fragments.ProductDetailDescriptionTab
import `in`.shpt.fragments.ProductDetailTab
import `in`.shpt.fragments.ProductDetailTableOfIndex
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import org.json.JSONObject


/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailPagertAdapter(fm: FragmentManager, internal var isCorpus: Boolean, internal var isPrerelease: Boolean, internal var result: JSONObject, internal var tabCount: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        if (result.optString("table_of_index").trim().isBlank()) {
            when (position) {
                0 -> return ProductDetailTab(result, isCorpus, isPrerelease)
                1 -> return ProductDetailAttributeTab(result)
                2 -> return ProductDetailDescriptionTab(result)
                else -> return null
            }
        } else {
            when (position) {
                0 -> return ProductDetailTab(result, isCorpus, isPrerelease)
                1 -> return ProductDetailAttributeTab(result)
                2 -> return ProductDetailDescriptionTab(result)
                3 -> return ProductDetailTableOfIndex(result)
                else -> return null
            }
        }
    }

    override fun getItemPosition(obj: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence {

        if (result.optString("table_of_index").trim().isBlank()) {
            when (position) {
                0 -> return "Detail"
                1 -> return "Specs"
                2 -> return "Description"
                else -> return ""
            }
        } else {
            when (position) {
                0 -> return "Detail"
                1 -> return "Specs"
                2 -> return "Description"
                3 -> return "Table Of Contents"
                else -> return ""
            }
        }

    }

    override fun getCount(): Int {
        return tabCount
    }
}
