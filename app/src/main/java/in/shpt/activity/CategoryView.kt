package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.CategoryProductListAdapter
import `in`.shpt.ext.getCategortProducts
import `in`.shpt.widget.SimpleDividerItemDecoration
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.FooterAdapter
import com.mikepenz.fastadapter_extensions.items.ProgressItem
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_category_view.*
import org.json.JSONObject


class CategoryView : AppCompatActivity() {

    var categoryId: String = ""
    lateinit var fastAdapter: FastItemAdapter<CategoryProductListAdapter>
    lateinit var footerAdapter: FooterAdapter<ProgressItem>

    var page: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_view)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        categoryId = intent.getStringExtra("CATEGORYID")

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        productList.layoutManager = llm
        productList.addItemDecoration(SimpleDividerItemDecoration(applicationContext))
        fastAdapter = FastItemAdapter()
        footerAdapter = FooterAdapter()

        productList.itemAnimator = DefaultItemAnimator()

        productList.adapter = footerAdapter.wrap(fastAdapter);



        AsyncTaskCompat.executeParallel(CategoryProductLoader(), null)
        productList.addOnScrollListener(object : EndlessRecyclerOnScrollListener(footerAdapter) {
            override fun onLoadMore(currentPage: Int) {
                footerAdapter.add(ProgressItem().withEnabled(false))
                page += 1
                AsyncTaskCompat.executeParallel(CategoryProductLoader(), null)

            }
        })
    }

    inner class CategoryProductLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getCategortProducts(categoryId, page.toString())
        }

        override fun onPostExecute(result: JSONObject?) {


            supportActionBar!!.title = result!!.optString("heading_title")

            var products = result.optJSONArray("products")

            for (i in 0..products.length() - 1) {
                fastAdapter.add(CategoryProductListAdapter(
                        products.optJSONObject(i).optString("thumb"),
                        products.optJSONObject(i).optString("name"),
                        products.optJSONObject(i).optString("price"),
                        products.optJSONObject(i).optString("description"),
                        products.optJSONObject(i).optString("product_id").toInt(),
                        this@CategoryView
                ))
            }
            footerAdapter.clear()
            super.onPostExecute(result)
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
}
