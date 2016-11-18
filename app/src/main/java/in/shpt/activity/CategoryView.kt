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
import kotlinx.android.synthetic.main.activity_category_view.*
import org.json.JSONObject

class CategoryView : AppCompatActivity() {

    var categoryId: String = ""
    lateinit var fastAdapter: FastItemAdapter<CategoryProductListAdapter>
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
        productList.itemAnimator = DefaultItemAnimator()

        productList.adapter = fastAdapter

        AsyncTaskCompat.executeParallel(CategoryProductLoader(), null)
    }

    inner class CategoryProductLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getCategortProducts(categoryId)
        }

        override fun onPostExecute(result: JSONObject?) {
            super.onPostExecute(result)

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
