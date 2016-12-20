package `in`.shpt.fragments

import `in`.shpt.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mcxiaoke.koi.ext.find
import org.json.JSONObject

/**
 * Created by poovarasanv on 20/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 20/12/16 at 1:16 PM
 */

class ProductDetailTableOfIndex(var result: JSONObject) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.product_detail_tab_4, container, false)

        val web = view!!.find<WebView>(R.id.tableOfContentsView)
        web.setWebChromeClient(WebChromeClient())
        web.setWebViewClient(WebViewClient())
        web.settings.javaScriptEnabled = true
        var webViewContents ="<html><body style='text-align:justify;'>${result.optString("table_of_index")}</body></html>"
        web.loadData(webViewContents, "text/html; charset=utf-8", "UTF-8")
        return view
    }
}
