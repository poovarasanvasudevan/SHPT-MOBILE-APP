package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.ProductSearchAdapter
import `in`.shpt.adapter.ProductSearchHeadingAdapter
import `in`.shpt.adapter.RecentProductSearchAdapter
import `in`.shpt.config.Config
import `in`.shpt.ext.init
import `in`.shpt.ext.log
import `in`.shpt.ext.searchProducts
import `in`.shpt.ext.theme
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.onTextChange
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.FooterAdapter
import com.mikepenz.fastadapter.adapters.HeaderAdapter
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_product_search_result.*
import org.json.JSONArray


class ProductSearchResult : AppCompatActivity() {

    lateinit var productSearchAdapter: FastItemAdapter<ProductSearchAdapter>
    lateinit var recentproductSearchAdapter: FooterAdapter<RecentProductSearchAdapter>
    lateinit var productSearchHeaderAdapter: HeaderAdapter<ProductSearchHeadingAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_product_search_result)
        init(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        productSearchAdapter = FastItemAdapter()
        productSearchHeaderAdapter = HeaderAdapter()
        recentproductSearchAdapter = FooterAdapter()


        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        product_search_list.layoutManager = llm
        product_search_list.itemAnimator = DefaultItemAnimator()
        product_search_list.adapter = productSearchHeaderAdapter.wrap(recentproductSearchAdapter.wrap(productSearchAdapter))


        loadRecent()


        search_product.onTextChange { text, start, before, count ->

            productSearchHeaderAdapter.clear()
            recentproductSearchAdapter.clear()
            productSearchAdapter.clear()
            product_result.visibility = View.GONE

            if (text.length > 2) {

                searchProductsFun(text.toString())
            } else {

                loadRecent()
            }
        }


    }


    fun loadRecent() {


        val query = ParseQuery.getQuery<ParseObject>(Config.RECENTPRODUCT_CLASS)
        query.fromLocalDatastore()
        query.addDescendingOrder("created")
        query.findInBackground { mutableList, parseException ->
            run {
                if (parseException == null && mutableList.size > 0) {
                    productSearchHeaderAdapter.add(ProductSearchHeadingAdapter(
                            "Recent Searches"
                    ))

                    var dis = mutableList.distinctBy {
                        it.get("product_id")
                    }
                    (0..dis.size - 1)
                            .forEach {
                                recentproductSearchAdapter.add(RecentProductSearchAdapter(
                                        mutableList[it].getInt("product_id").toString(),
                                        mutableList[it].getString("product_name"),
                                        this@ProductSearchResult
                                ))
                            }
                }
            }
        }
    }

    fun searchProductsFun(term: String) {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(SearchProducts(), term)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SearchProducts : AsyncTask<String, Void, JSONArray>() {
        override fun doInBackground(vararg p0: String?): JSONArray {
            return searchProducts(p0[0] as String)
        }

        override fun onPostExecute(result: JSONArray?) {
            //optJson and add to list


            if (result!!.length() > 0) {

                productSearchHeaderAdapter.add(ProductSearchHeadingAdapter("Search Result"))
                (0..result.length() - 1)
                        .filter { it != result.length() - 1 }
                        .forEach {

                            var href = result.optJSONObject(it).optString("href")
                            var productId = href.substring(href.indexOf("product_id="), href.length).replace("product_id=", "")
                            productSearchAdapter.add(ProductSearchAdapter(
                                    //result.optJSONObject(i).optString("href").substring(result.optJSONObject(i).optString("href").indexOf("product_id=", result.optJSONObject(i).optString("href").length)).replace("product_id=", ""),
                                    productId,
                                    result.optJSONObject(it).optString("name"),
                                    this@ProductSearchResult
                            ))
                        }
            } else {


                productSearchHeaderAdapter.clear()
                recentproductSearchAdapter.clear()
                productSearchAdapter.clear()

                product_result.visibility = View.VISIBLE
                loadRecent()
            }
            log(result.toString())
            super.onPostExecute(result)
        }
    }
}
