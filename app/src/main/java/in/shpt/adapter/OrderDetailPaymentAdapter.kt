package `in`.shpt.adapter

import `in`.shpt.R
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class OrderDetailPaymentAdapter(
        var label: String,
        var value: String) : AbstractItem<OrderDetailPaymentAdapter, OrderDetailPaymentAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.myorderdetailpaymentadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.order_detail_product_payment
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.OrderDetailPaymentLabel.text = label
        holder.OrderDetailPaymentLabelValue.text = value

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var OrderDetailPaymentLabel: TextView
        internal var OrderDetailPaymentLabelValue: TextView

        init {

            OrderDetailPaymentLabel = view.findViewById(R.id.OrderDetailPaymentLabel) as TextView
            OrderDetailPaymentLabelValue = view.findViewById(R.id.OrderDetailPaymentLabelValue) as TextView
        }
    }
}
