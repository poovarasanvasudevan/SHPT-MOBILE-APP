package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.ext.limitsTo
import `in`.shpt.ext.loadUrl
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class CategoryProductListAdapter(
        var productImage: String,
        var productName: String,
        var productPrice: String,
        var productDesc: String,
        var context: Activity) : AbstractItem<CategoryProductListAdapter, CategoryProductListAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.categoryadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.categoty_list_product
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.productImage.loadUrl(productImage)
        holder.productName.text = productName
        holder.productPrice.text = productPrice
        holder.productShortDescription.text = productDesc.limitsTo(50)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var productName: TextView
        internal var productImage: ImageView
        internal var productPrice: TextView
        internal var productShortDescription: TextView
        internal var productBannerLayout: Ripple


        init {

            productName = view.findViewById(R.id.productName) as TextView
            productImage = view.findViewById(R.id.productImage) as ImageView
            productPrice = view.findViewById(R.id.productPrice) as TextView
            productShortDescription = view.findViewById(R.id.productShortDescription) as TextView
            productBannerLayout = view.findViewById(R.id.productBannerLayout) as Ripple
        }
    }
}
