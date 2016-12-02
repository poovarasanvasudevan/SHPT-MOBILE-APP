package `in`.shpt.adapter


import `in`.shpt.R
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class ProductDetailAttributeAdapter(
        var label: String,
        var value: String) : AbstractItem<ProductDetailAttributeAdapter, ProductDetailAttributeAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.productdetailattributeadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.product_detail_attribute_item
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.detailLabel.text = label
        holder.detailText.text = value

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var detailLabel: TextView
        internal var detailText: TextView

        init {

            detailLabel = view.findViewById(R.id.detailLabel) as TextView
            detailText = view.findViewById(R.id.detailText) as TextView
        }
    }
}

