package `in`.shpt.adapter

import `in`.shpt.R
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class ProductSearchHeadingAdapter(
        var value: String) : AbstractItem<ProductSearchHeadingAdapter, ProductSearchHeadingAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.productsearchheadingadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.product_search_header
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.headingTitle.text = value

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var headingTitle: TextView

        init {
            headingTitle = view.findViewById(R.id.headingTitle) as TextView
        }
    }
}
