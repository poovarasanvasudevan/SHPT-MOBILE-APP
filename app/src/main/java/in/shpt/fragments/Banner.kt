package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.ext.currencyFormat
import `in`.shpt.ext.limitsTo
import `in`.shpt.ext.loadUrl
import `in`.shpt.models.BannerModel
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcxiaoke.koi.ext.onClick
import kotlinx.android.synthetic.main.banner.view.*

/**
 * Created by poovarasanv on 14/11/16.
 */

class Banner : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val message: BannerModel = arguments.getParcelable(EXTRA_MESSAGE)
        val v = inflater!!.inflate(R.layout.banner, container, false)
        v.productName.text = message.productName
        v.productPrice.text = message.price.currencyFormat()
        v.productShortDescription.text = message.desc.limitsTo(50)
        v.productImage.loadUrl(message.image)

        v.productBannerLayout.onClick {
            //toast("You Clicked ${message.productId}")
            var productDetailIntent: Intent = Intent(activity, ProductDetail::class.java)
            productDetailIntent.putExtra("PRODUCTID", message.productId)
            startActivity(productDetailIntent)

        }
        return v;
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
