package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mcxiaoke.koi.ext.onClick
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class RecentProductSearchAdapter(
        var productId: String,
        var value: String,
        var context: Activity) : AbstractItem<RecentProductSearchAdapter, RecentProductSearchAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.recentproductsearchadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.recent_product_search_item
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.productName.text = value


        holder.productSearchItem.onClick {

            var productDetailIntent: Intent = Intent(context, ProductDetail::class.java)
            productDetailIntent.putExtra("PRODUCTID", productId.toInt())
            context.startActivity(productDetailIntent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var productName: TextView
        internal var productSearchItem: Ripple


        init {

            productName = view.findViewById(R.id.productName) as TextView
            productSearchItem = view.findViewById(R.id.productSearchItem) as Ripple

        }
    }
}
