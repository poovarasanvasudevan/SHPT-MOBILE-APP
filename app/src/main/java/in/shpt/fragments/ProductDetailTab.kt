package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.adapter.ImagePagerAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mcxiaoke.koi.ext.find
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailTab(var result: JSONObject) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater?.inflate(R.layout.product_detail_tab_1, container, false)
        var imagePager = view!!.find<ViewPager>(R.id.imagePager)
        var productDetailName = view!!.find<TextView>(R.id.productDetailName)

        var imageList = ArrayList<String>()
        var images: JSONArray = result.optJSONArray("images")
        imageList.add(result.optString("thumb"))
        for (i in 0..images.length() - 1) {
            imageList.add(images.optJSONObject(i).optString("thumb"))
        }

        imageList.add(result.optString("thumb"))
        var imageAdapter = ImagePagerAdapter(activity.supportFragmentManager, imageList)
        imagePager.adapter = imageAdapter

        productDetailName.text = result.optString("heading_title")

        return view
    }
}
