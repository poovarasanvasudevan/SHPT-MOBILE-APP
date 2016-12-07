package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.ext.loadUrl
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.mcxiaoke.koi.ext.onClick
import com.mikepenz.fastadapter.items.AbstractItem

/**
 * Created by poovarasanv on 15/11/16.
 */

class ImageProductItemAdapter(
        var productId: String,
        var imageurl: String,
        var name: String,
        var context: Activity) : AbstractItem<ImageProductItemAdapter, ImageProductItemAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.imageproductattributeadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.product_grid_item
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.productImage.loadUrl(imageurl)
        holder.productName.text = name

        holder.productItem.onClick {

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var productImage: ImageView
        internal var productName: TextView
        internal var productItem: RelativeLayout

        init {

            productImage = view.findViewById(R.id.productImage) as ImageView
            productName = view.findViewById(R.id.productName) as TextView
            productItem = view.findViewById(R.id.productItem) as RelativeLayout
        }
    }
}
