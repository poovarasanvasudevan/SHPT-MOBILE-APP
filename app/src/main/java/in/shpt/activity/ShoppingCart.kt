package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.ShoppingCartProductAdapter
import `in`.shpt.adapter.ShoppingCartVoucherAdapter
import `in`.shpt.event.ItemRemovedFromCartEvent
import `in`.shpt.ext.getFullCart
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.theme
import android.graphics.Color
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
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.FooterAdapter
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject


class ShoppingCart : AppCompatActivity() {
    lateinit var fastAdapter: FastItemAdapter<ShoppingCartProductAdapter>
    lateinit var voucherAdapter: FooterAdapter<ShoppingCartVoucherAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_shopping_cart)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        shoppingCartProducts.layoutManager = llm

        fastAdapter = FastItemAdapter()
        voucherAdapter = FooterAdapter()
        shoppingCartProducts.itemAnimator = DefaultItemAnimator()
        shoppingCartProducts.adapter = voucherAdapter.wrap(fastAdapter)

        loadCart()

    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    fun loadCart() {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(FullCartLoader(), null)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ItemRemovedFromCartEvent) {
        if (event.isSuccess) {
            loadCart()
        }
    }

    inner class FullCartLoader : AsyncTask<Void, Void, JSONObject>() {

        override fun onPreExecute() {
            progress.visibility = View.VISIBLE
            productPanel.visibility = View.GONE
            bottomPanel.visibility = View.GONE

            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getFullCart()
        }

        override fun onPostExecute(result: JSONObject?) {

            productPanel.visibility = View.VISIBLE
            bottomPanel.visibility = View.VISIBLE
            progress.visibility = View.GONE
            supportActionBar!!.title = result!!.optString("heading_title")
            if (result.optJSONArray("products") == null) {
                bottomPanel.visibility = View.GONE
                productPanel.visibility = View.GONE
                emptyPanel.visibility = View.VISIBLE

                emptyImage.setImageDrawable(getIcon(FontAwesome.Icon.faw_frown_o, Color.GRAY, 120))
                emptyText.setText(result.optString("text_error"))
            } else {

                fastAdapter.clear()
                voucherAdapter.clear()


                var products: JSONArray = result.optJSONArray("products")
                for (i in 0..products.length() - 1) {
                    fastAdapter.add(ShoppingCartProductAdapter(
                            products.optJSONObject(i).optString("model"),
                            products.optJSONObject(i).optString("name"),
                            products.optJSONObject(i).optString("thumb"),
                            products.optJSONObject(i).optInt("quantity").toString(),
                            products.optJSONObject(i).optString("total"),
                            this@ShoppingCart
                    ))
                }

                var voucher: JSONArray = result.optJSONArray("vouchers")
                for (i in 0..voucher.length() - 1) {
                    voucherAdapter.add(ShoppingCartVoucherAdapter(
                            voucher.optJSONObject(i).optInt("key").toString(),
                            voucher.optJSONObject(i).optString("description"),
                            voucher.optJSONObject(i).optString("amount"),
                            this@ShoppingCart
                    ))
                }

                couponCode.setText(result.optString("coupon"))
                vouchercouponCode.setText(result.optString("voucher"))
                couponLabelText.text = result.optString("text_use_coupon")
                voucherLabelText.text = result.optString("text_use_voucher")



                if (result.optString("coupon_status").toInt() == 0) {
                    couponBlock.visibility = View.GONE
                }

                if (result.optString("voucher_status").toInt() == 0) {
                    voucherBlock.visibility = View.GONE
                }


            }
            // getIcon(FontAwesome.Icon.faw_gift)

            super.onPostExecute(result)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_cart_menu, menu)
        menu!!.findItem(R.id.refresh_cart).setIcon(getIcon(FontAwesome.Icon.faw_refresh))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
            R.id.refresh_cart -> loadCart()
        }
        return super.onOptionsItemSelected(item)
    }
}
