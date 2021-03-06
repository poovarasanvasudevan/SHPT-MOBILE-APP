package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.ext.loadSHPTImage
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
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

        holder.productImage.loadSHPTImage(productId)
        holder.productName.text = name

        holder.productItem.onClick {
            var productDetailIntent: Intent = Intent(context, ProductDetail::class.java)
            productDetailIntent.putExtra("PRODUCTID", productId.toInt())
            context.startActivity(productDetailIntent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var productImage: ImageView
        internal var productName: TextView
        internal var productItem: Ripple

        init {

            productImage = view.findViewById(R.id.productImage) as ImageView
            productName = view.findViewById(R.id.productName) as TextView
            productItem = view.findViewById(R.id.productItem) as Ripple
        }
    }
}
