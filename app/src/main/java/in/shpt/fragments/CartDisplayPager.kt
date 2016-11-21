package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.adapter.BannerAdapter
import `in`.shpt.config.Config
import `in`.shpt.ext.getCart
import `in`.shpt.models.BannerModel
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.os.AsyncTaskCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cart_pager.view.*
import org.json.JSONArray

/**
 * Created by poovarasanv on 14/11/16.
 */

class CartDisplayPager : Fragment() {
    var cartCount: Int = 0
    lateinit var cartPager: ViewPager
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val message: BannerModel = arguments.getParcelable(EXTRA_MESSAGE)
        val v = inflater!!.inflate(R.layout.cart_pager, container, false)

        cartPager = v.cartPager

        AsyncTaskCompat.executeParallel(CartLoader(), null)
        return v;
    }

    inner class CartLoader : AsyncTask<Void, Void, JSONArray>() {
        override fun doInBackground(vararg p0: Void?): JSONArray? {
            return activity.getCart()
        }

        override fun onPostExecute(result: JSONArray?) {
            cartCount = result!!.length()


            var cartData: MutableList<BannerModel> = arrayListOf()
            for (i in 0..cartCount - 1) {
                cartData.add(BannerModel(
                        result.optJSONObject(i).optString("product_id").toInt(),
                        result.optJSONObject(i).optString("name"),
                        result.optJSONObject(i).optInt("price").toString() + ".0000",
                        "",
                        Config.IMAGE_PATH + result.optJSONObject(i).optString("image")
                ))
            }

            cartPager.adapter = BannerAdapter(activity.supportFragmentManager, cartData)
            super.onPostExecute(result)
        }
    }

    companion object {
        val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        fun newInstance(message: BannerModel): Banner {
            val f = Banner()
            val bdl = Bundle(1)
            bdl.putParcelable(EXTRA_MESSAGE, message)
            f.arguments = bdl
            return f
        }
    }
}
