package `in`.shpt.adapter

import `in`.shpt.R
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class OrderDetailHistoryAdapter(
        var date: String,
        var comments: String,
        var status: String) : AbstractItem<OrderDetailHistoryAdapter, OrderDetailHistoryAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.myorderdetailhistoryadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.order_detail_product_history
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.OrdeDetailProductHistoryDate.text = date
        holder.OrdeDetailProductHistoryStatus.text = status
        holder.OrdeDetailProductHistoryComments.text = "\t\t" + Html.fromHtml(comments).toString()

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var OrdeDetailProductHistoryDate: TextView
        internal var OrdeDetailProductHistoryStatus: TextView
        internal var OrdeDetailProductHistoryComments: TextView

        init {

            OrdeDetailProductHistoryDate = view.findViewById(R.id.OrdeDetailProductHistoryDate) as TextView
            OrdeDetailProductHistoryStatus = view.findViewById(R.id.OrdeDetailProductHistoryStatus) as TextView
            OrdeDetailProductHistoryComments = view.findViewById(R.id.OrdeDetailProductHistoryComments) as TextView
        }
    }
}
