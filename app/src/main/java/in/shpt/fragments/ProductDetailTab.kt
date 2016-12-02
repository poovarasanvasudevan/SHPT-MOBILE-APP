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
 * @author poovarasanv
 * @project SHPT
 * @on 29/11/16 at 5:39 PM
 */

class ProductDetailTab(var result: JSONObject) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.product_detail_tab_1, container, false)
        val imagePager = view!!.find<ViewPager>(R.id.imagePager)
        val productDetailName = view.find<TextView>(R.id.productDetailName)

        val imageList = ArrayList<String>()
        val images: JSONArray = result.optJSONArray("images")
        imageList.add(result.optString("popup"))
        for (i in 0..images.length() - 1) {
            imageList.add(images.optJSONObject(i).optString("popup"))
        }

        val imageAdapter = ImagePagerAdapter(activity.supportFragmentManager, imageList)
        imagePager.adapter = imageAdapter

        productDetailName.text = result.optString("heading_title")

        return view
    }
}
