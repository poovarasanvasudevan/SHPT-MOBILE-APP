package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.OrderDetailHistoryAdapter
import `in`.shpt.adapter.OrderDetailPaymentAdapter
import `in`.shpt.adapter.OrderDetailProductAdapter
import `in`.shpt.ext.getOrderDetail
import `in`.shpt.ext.theme
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import kotlinx.android.synthetic.main.activity_order_details.*
import org.json.JSONArray
import org.json.JSONObject

class OrderDetails : AppCompatActivity() {

    var orderId: Int = 0
    lateinit var fastAdapter: FastItemAdapter<OrderDetailProductAdapter>
    lateinit var paymentAdapter: FastItemAdapter<OrderDetailPaymentAdapter>
    lateinit var historyAdapter: FastItemAdapter<OrderDetailHistoryAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_order_details)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        orderId = intent.getStringExtra("ORDERID").toInt()

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        orderDetailProduct.layoutManager = llm

        val llm1 = LinearLayoutManager(this)
        llm1.orientation = LinearLayoutManager.VERTICAL
        orderDetailPayment.layoutManager = llm1

        val llm2 = LinearLayoutManager(this)
        llm2.orientation = LinearLayoutManager.VERTICAL
        orderDetailHistory.layoutManager = llm2




        fastAdapter = FastItemAdapter()
        paymentAdapter = FastItemAdapter()
        historyAdapter = FastItemAdapter()

        orderDetailProduct.itemAnimator = DefaultItemAnimator()
        orderDetailProduct.adapter = fastAdapter

        orderDetailPayment.itemAnimator = DefaultItemAnimator()
        orderDetailPayment.adapter = paymentAdapter

        orderDetailHistory.itemAnimator = DefaultItemAnimator()
        orderDetailHistory.adapter = historyAdapter

        AsyncTaskCompat.executeParallel(OrderDetailLoader(), orderId)
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

    inner class OrderDetailLoader : AsyncTask<Int, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Int?): JSONObject? {
            return applicationContext.getOrderDetail(p0[0] as Int)
        }

        override fun onPostExecute(result: JSONObject?) {

            var trackNumber: String = "";

            orderID.text = result!!.optString("order_id")
            paymentMethod.text = result!!.optString("payment_method")
            dateAdded.text = result!!.optString("date_added")
            shippingMethod.text = result!!.optString("shipping_method")
            paymentAddress.text = Html.fromHtml(result!!.optString("payment_address"))
            shippingAddress.text = Html.fromHtml(result!!.optString("shipping_address"))

            supportActionBar!!.title = "Order Detail : #" + result!!.optString("order_id")

            //loading the product
            var products: JSONArray = result.optJSONArray("products")
            for (i in 0..products.length() - 1) {
                fastAdapter.add(OrderDetailProductAdapter(
                        products.optJSONObject(i).optString("name"),
                        products.optJSONObject(i).optString("model"),
                        products.optJSONObject(i).optString("quantity").toInt(),
                        products.optJSONObject(i).optString("price"),
                        products.optJSONObject(i).optString("total")
                ))
            }
            //getIcon(Ionicons.Icon.ion_ios_location_outline)

            //loading the totals
            var totals: JSONArray = result.optJSONArray("totals")
            for (i in 0..totals.length() - 1) {
                paymentAdapter.add(OrderDetailPaymentAdapter(
                        totals.optJSONObject(i).optString("title"),
                        totals.optJSONObject(i).optString("text")
                ))
            }
            if (result.optString("comment").isBlank() || result.optString("comment").isEmpty()) {
                commentLayout.visibility = View.GONE
            } else {
                orderdetailComments.text = result.optString("comment")
            }

            //loading the history
            var history: JSONArray = result.optJSONArray("histories")
            for (i in 0..history.length() - 1) {

                if (history.optJSONObject(i).optString("status") == "Shipped") {
                    trackButton.visibility = View.VISIBLE
                    trackNumber = history.optJSONObject(i).optString("tnt_track")
                }

                historyAdapter.add(OrderDetailHistoryAdapter(
                        history.optJSONObject(i).optString("date_added"),
                        history.optJSONObject(i).optString("comment"),
                        history.optJSONObject(i).optString("status")
                ))
            }

            orderdetailProgress.visibility = View.GONE
            orderDetailLayout.visibility=View.VISIBLE

            super.onPostExecute(result)
        }
    }

}
