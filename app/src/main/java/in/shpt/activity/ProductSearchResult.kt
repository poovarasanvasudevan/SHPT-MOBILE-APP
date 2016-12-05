package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.init
import `in`.shpt.ext.searchProducts
import `in`.shpt.ext.theme
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.onTextChange
import kotlinx.android.synthetic.main.activity_product_search_result.*
import org.json.JSONArray


class ProductSearchResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_product_search_result)
        init(this)


        backButton.onClick {
            finish()
        }

        productSearch.onTextChange { text, start, before, count ->
            if (text.length > 2) {

            } else {

            }
        }
    }


    inner class SearchProducts : AsyncTask<String, Void, JSONArray>() {
        override fun doInBackground(vararg p0: String?): JSONArray {
            return searchProducts(p0[0] as String)
        }

        override fun onPostExecute(result: JSONArray?) {
            //optJson and add to list

            super.onPostExecute(result)
        }
    }
}
