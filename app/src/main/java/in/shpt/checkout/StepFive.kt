package `in`.shpt.checkout

import `in`.shpt.R
import `in`.shpt.adapter.ShoppingCartProductAdapter
import `in`.shpt.adapter.ShoppingCartTotalAdapter
import `in`.shpt.adapter.ShoppingCartVoucherAdapter
import `in`.shpt.ext.confirmOrderStep
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fcannizzaro.materialstepper.AbstractStep
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.isConnected
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.FooterAdapter
import org.json.JSONObject

/**
 * Created by poovarasanv on 24/11/16.
 */

class StepFive : AbstractStep() {

    lateinit var productList: RecyclerView
    lateinit var totalList: RecyclerView

    lateinit var productListAdapter: FastItemAdapter<ShoppingCartProductAdapter>
    lateinit var voucherListAdapter: FooterAdapter<ShoppingCartVoucherAdapter>

    lateinit var totalAdapter: FastItemAdapter<ShoppingCartTotalAdapter>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.step5, container, false)

        productList = v.find(R.id.productList)
        totalList = v.find(R.id.totalList)

        productListAdapter = FastItemAdapter()
        voucherListAdapter = FooterAdapter()
        totalAdapter = FastItemAdapter()


        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        productList.setLayoutManager(llm)


        val llm1 = LinearLayoutManager(context)
        llm1.orientation = LinearLayoutManager.VERTICAL
        totalList.setLayoutManager(llm1)


        totalList.adapter = totalAdapter
        productList.adapter = voucherListAdapter.wrap(productListAdapter)

        return v
    }

    fun loadConfirm() {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(ConfirmOrderStep(), null)
        }
    }

    override fun onStepVisible() {
        loadConfirm()
        super.onStepVisible()
    }

    inner class ConfirmOrderStep : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject {
            return context.confirmOrderStep()
        }

        override fun onPostExecute(result: JSONObject?) {

            var products = result!!.optJSONArray("products")

            if(products !=null) {
                for (i in 0..products.length() - 1) {
                    productListAdapter.add(ShoppingCartProductAdapter(
                            products.optJSONObject(i).optString("product_id"),
                            products.optJSONObject(i).optString("name"),
                            products.optJSONObject(i).optString("image"),
                            products.optJSONObject(i).optString("quantity"),
                            products.optJSONObject(i).optString("total"),
                            activity,
                            false,
                            false
                    ))
                }
            }

            var vouchers = result.optJSONArray("vouchers")
            if(vouchers !=null) {
                for (i in 0..vouchers.length() - 1) {
                    voucherListAdapter.add(ShoppingCartVoucherAdapter(
                            vouchers.optJSONObject(i).optString("code"),
                            "Gift Voucher for " + vouchers.optJSONObject(i).optString("to_name"),
                            vouchers.optJSONObject(i).optString("amount"),
                            activity,
                            false
                    ))
                }
            }

            var totals = result.optJSONArray("totals")
            if(totals !=null) {
                for (i in 0..totals.length() - 1) {
                    totalAdapter.add(ShoppingCartTotalAdapter(
                            totals.optJSONObject(i).optString("title"),
                            totals.optJSONObject(i).optString("text")
                    ))
                }
            }

            super.onPostExecute(result)
        }
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
    }

    override fun name(): String? {
        return "Confirm Order"
    }

    override fun nextIf(): Boolean {
        return super.nextIf()
    }


}
