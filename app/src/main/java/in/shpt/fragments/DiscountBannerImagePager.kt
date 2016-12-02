package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.ext.loadUrl
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.onClick
import org.json.JSONObject


/**
 * Created by poovarasanv on 14/11/16.
 */

class DiscountBannerImagePager : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val message: JSONObject = JSONObject(arguments.getString(EXTRA_MESSAGE))
        val v = inflater!!.inflate(R.layout.home_discount_banner, container, false)

        Log.i("ImageData", message.toString())
        v.find<ImageView>(R.id.banner_image).loadUrl(message.optString("image"), 1)

        if (message.optString("product_id", "NULL") != "NULL") {
            v.find<ImageView>(R.id.banner_image).onClick {
                var intent: Intent = Intent(activity, ProductDetail::class.java)
                intent.putExtra("PRODUCTID", message.optString("product_id").toInt())
                startActivity(intent)
            }
        }
        return v;
    }


    companion object {
        val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        fun newInstance(message: String): DiscountBannerImagePager {
            val f = DiscountBannerImagePager()
            val bdl = Bundle(1)
            bdl.putString(EXTRA_MESSAGE, message)
            f.arguments = bdl
            return f
        }
    }
}
