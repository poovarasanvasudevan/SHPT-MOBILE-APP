package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.OrderHistoryAdapter
import `in`.shpt.ext.getMyOrders
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
import kotlinx.android.synthetic.main.activity_my_orders.*
import org.json.JSONArray


class MyOrders : AppCompatActivity() {

    var page: Int = 1
    lateinit var fastAdapter: FastItemAdapter<OrderHistoryAdapter>
    lateinit var footerAdapter: FooterAdapter<ProgressItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        orderHistory.setLayoutManager(llm)
        fastAdapter = FastItemAdapter()
        footerAdapter = FooterAdapter<ProgressItem>()
        orderHistory.itemAnimator = DefaultItemAnimator()



        orderHistory.adapter = footerAdapter.wrap(fastAdapter);
        AsyncTaskCompat.executeParallel(OrderHistoryLoader(), page)
        orderHistory.addOnScrollListener(object : EndlessRecyclerOnScrollListener(footerAdapter) {
            override fun onLoadMore(currentPage: Int) {

                footerAdapter.add(ProgressItem().withEnabled(false))

                page += 1
                AsyncTaskCompat.executeParallel(OrderHistoryLoader(), page)

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.shopping_cart_menu, menu)
        //menu!!.findItem(R.id.refresh_cart).setIcon(getIcon(Ionicons.Icon.ion_android_refresh))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


    inner class OrderHistoryLoader : AsyncTask<Int, Void, JSONArray>() {
        override fun doInBackground(vararg p0: Int?): JSONArray? {
            return applicationContext.getMyOrders(p0[0] as Int)
        }

        override fun onPostExecute(result: JSONArray?) {

            for (i in 0..(result!!.length()) - 1) {
                fastAdapter.add(OrderHistoryAdapter(
                        result.optJSONObject(i).optString("order_id"),
                        result.optJSONObject(i).optString("date_added"),
                        result.optJSONObject(i).optInt("products"),
                        result.optJSONObject(i).optString("name"),
                        result.optJSONObject(i).optString("status"),
                        result.optJSONObject(i).optString("total"),
                        applicationContext)
                )
            }
            footerAdapter.clear();

            super.onPostExecute(result)
        }
    }
}
