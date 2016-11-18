package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.activity.OrderDetails
import `in`.shpt.widget.Ripple
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mcxiaoke.koi.ext.Bundle
import com.mcxiaoke.koi.ext.onClick
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class OrderHistoryAdapter(var orderId: String, var dateAdded: String, var quantity: Int, var name: String, var status: String, var total: String, var context: Context) : AbstractItem<OrderHistoryAdapter, OrderHistoryAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.myorderadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.my_order_item
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.orderIdTv.text = "OrderId : #" + orderId
        holder.customerName.text = name
        holder.dateAdded.text = dateAdded
        holder.quantity.text = quantity.toString()
        holder.total.text = total
        holder.status.text = status

        holder.orderHistoryItem.onClick {
            val bundle = Bundle {
                var orderDetailIntent: Intent = Intent(context, OrderDetails::class.java)
                orderDetailIntent.putExtra("ORDERID", orderId)
                orderDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(orderDetailIntent)
                //putString("ORDERID", orderId)
                //context.startA
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var orderIdTv: TextView
        internal var customerName: TextView
        internal var dateAdded: TextView
        internal var quantity: TextView
        internal var total: TextView
        internal var status: TextView
        internal var orderHistoryItem: Ripple

        init {

            orderIdTv = view.findViewById(R.id.orderIdTv) as TextView
            customerName = view.findViewById(R.id.customerName) as TextView
            dateAdded = view.findViewById(R.id.dateAdded) as TextView
            quantity = view.findViewById(R.id.quantity) as TextView
            total = view.findViewById(R.id.total) as TextView
            status = view.findViewById(R.id.status) as TextView
            orderHistoryItem = view.findViewById(R.id.orderHistoryItem) as Ripple
        }
    }
}
