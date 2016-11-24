package `in`.shpt.adapter

import `in`.shpt.R
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class ShoppingCartTotalAdapter(
        var label: String,
        var value: String) : AbstractItem<ShoppingCartTotalAdapter, ShoppingCartTotalAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.shoppingcarttotaladapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.cart_total_layout
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.totalLabel.text = label
        holder.totalValue.text = value

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var totalLabel: TextView
        internal var totalValue: TextView

        init {

            totalLabel = view.findViewById(R.id.totalLabel) as TextView
            totalValue = view.findViewById(R.id.totalValue) as TextView
        }
    }
}
