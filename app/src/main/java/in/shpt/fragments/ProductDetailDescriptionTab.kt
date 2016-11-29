package `in`.shpt.fragments

import `in`.shpt.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mcxiaoke.koi.ext.find
import org.json.JSONObject

/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailDescriptionTab(var result: JSONObject) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater?.inflate(R.layout.product_detail_tab_3, container, false)

        var productDetailDescription = view!!.find<TextView>(R.id.productDetailDescription)
        productDetailDescription.text = "\t\t\t\t" + Html.fromHtml(result.optString("description"))

        return view
    }
}
