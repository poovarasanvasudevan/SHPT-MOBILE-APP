package `in`.shpt.adapter

import `in`.shpt.R
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class OrderDetailProductAdapter(
        var productName: String,
        var model: String,
        var quantity: Int,
        var price: String,
        var total: String) : AbstractItem<OrderDetailProductAdapter, OrderDetailProductAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.myorderdetailadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.order_detail_product_item
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.OrdeDetailProductName.text = productName
        holder.OrdeDetailModel.text = model
        holder.OrdeDetailQuantity.text = quantity.toString()
        holder.OrdeDetailPrice.text = price
        holder.OrdeDetailTotal.text = total
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var OrdeDetailProductName: TextView
        internal var OrdeDetailModel: TextView
        internal var OrdeDetailQuantity: TextView
        internal var OrdeDetailPrice: TextView
        internal var OrdeDetailTotal: TextView


        init {

            OrdeDetailProductName = view.findViewById(R.id.OrdeDetailProductName) as TextView
            OrdeDetailModel = view.findViewById(R.id.OrdeDetailModel) as TextView
            OrdeDetailQuantity = view.findViewById(R.id.OrdeDetailQuantity) as TextView
            OrdeDetailPrice = view.findViewById(R.id.OrdeDetailPrice) as TextView
            OrdeDetailTotal = view.findViewById(R.id.OrdeDetailTotal) as TextView
        }
    }
}
