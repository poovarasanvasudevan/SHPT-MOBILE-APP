package `in`.shpt.fragments

import `in`.shpt.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONObject

/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailAttributeTab(var result: JSONObject) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater?.inflate(R.layout.product_detail_tab_2, container, false)
        return view
    }
}
