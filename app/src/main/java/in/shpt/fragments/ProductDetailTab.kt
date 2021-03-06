package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.adapter.ImagePagerAdapter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mcxiaoke.koi.ext.find
import com.veinhorn.tagview.TagView
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


/**
 * Created by poovarasanv on 29/11/16.
 * This Fragment Shows Details of Products such as
 * <ul>
 *  <li> Images </li>
 *  <li> ProductName </li>
 *  <li> Is Free Shipping </li>
 *  <li> Is Corpus Label </li>
 *  <li> Price </li>
 *  <li> Extra Parse Data </li>
 * </ul>
 * @author poovarasanv
 * @project SHPT
 * @on 29/11/16 at 5:39 PM
 */

class ProductDetailTab(var result: JSONObject, var corpus: Boolean, var isPrerelease: Boolean) : Fragment() {


    var tagsetFlag = false
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.product_detail_tab_1, container, false)
        val imagePager = view!!.find<ViewPager>(R.id.imagePager)
        val productDetailName = view.find<TextView>(R.id.productDetailName)
        val productDetailCost = view.find<TextView>(R.id.productDetailCost)
        val free_shipping = view.find<TextView>(R.id.free_shipping)
        val prebooking = view.find<TextView>(R.id.prebooking)
        val badges = view.find<ViewGroup>(R.id.badges)


        val imageList = ArrayList<String>()
        val images: JSONArray? = result.optJSONArray("images")
        imageList.add(result.optString("popup"))
        if (images != null) {
            (0..images.length() - 1).mapTo(imageList) { images.optJSONObject(it).optString("popup") }
        }

        val imageAdapter = ImagePagerAdapter(activity.supportFragmentManager, imageList)
        imagePager.adapter = imageAdapter


        var price: Spannable = SpannableString(result.optString("price"))
        price.setSpan(ForegroundColorSpan(Color.BLUE), 0, result.optString("price").length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        if (result.opt("special") is Boolean) {
            // price = products.optJSONObject(i).optString("price") as Spannable
        } else {
            price.setSpan(StrikethroughSpan(), 0, result.optString("price").length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            val discount = SpannableString(result.optString("special"))
            discount.setSpan(ForegroundColorSpan(Color.RED), 0, result.optString("special").length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            price = SpannableString(TextUtils.concat(price, SpannableString("  "), discount))


            tagsetFlag = true
        }
        productDetailName.text = result.optString("heading_title")
        productDetailCost.text = SpannableString(TextUtils.concat("Price : ", price))


        if (corpus) {

            val corpusTag: TagView = TagView(context, null)

            val param: ViewGroup.MarginLayoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            param.setMargins(2, 2, 2, 2)
            corpusTag.layoutParams = param
            corpusTag.text = "Corpus"
            corpusTag.tagType = TagView.MODERN
            corpusTag.tagColor = context.resources.getColor(R.color.md_red_400)
            badges.addView(corpusTag)
        }

        if (isPrerelease) {

            val corpusTag: TagView = TagView(context, null)

            val param: ViewGroup.MarginLayoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            param.setMargins(2, 2, 2, 2)
            corpusTag.layoutParams = param
            corpusTag.text = "Pre-Booking"
            corpusTag.tagType = TagView.MODERN
            corpusTag.tagColor = context.resources.getColor(R.color.md_teal_400)
            badges.addView(corpusTag)

            prebooking.text = "(Pre-Booking Opens)"
            prebooking.visibility = View.VISIBLE
        }

        if (result.opt("free_shipping") is String) {
            free_shipping.text = "${result.optString("text_free_shipping")} ${result.optString("free_shipping_date")}"
            free_shipping.visibility = View.VISIBLE
        }

        if (result.optJSONArray("categories").length() > 0) {


            var s: ArrayList<String> = arrayListOf()
            for (i in 0..result.optJSONArray("categories").length() - 1) {

                s.add(result.optJSONArray("categories").optJSONObject(i).optString("name"))

            }

            val x = Comparator<String> { o1, o2 ->
                if (o1.length > o2.length)
                    return@Comparator 1

                if (o2.length > o1.length)
                    return@Comparator -1

                0
            }
            Collections.sort(s, x)

            for (i in 0..s.size - 1) {
                val rnd = Random()
                val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

                val corpusTag: TagView = TagView(context, null)
                val param: ViewGroup.MarginLayoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                param.setMargins(2, 2, 2, 2)

                corpusTag.layoutParams = param
                corpusTag.text = s[i]
                corpusTag.tagType = TagView.MODERN_TRAPEZIUM
                corpusTag.tagColor = color
                badges.addView(corpusTag)
            }


        }

        return view
    }
}
//