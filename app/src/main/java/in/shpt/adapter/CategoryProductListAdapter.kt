package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.ext.limitsTo
import `in`.shpt.ext.loadUrl
import `in`.shpt.ext.log
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mcxiaoke.koi.ext.onClick
import com.mikepenz.fastadapter.items.AbstractItem
import com.veinhorn.tagview.TagView

/**
 * Created by poovarasanv on 15/11/16.
 */

class CategoryProductListAdapter(
        var productImage: String,
        var productName: String,
        var productPrice: String,
        var productDesc: String,
        var product_id: Int,
        var context: Activity,
        var isFreeShipping: Boolean = false,
        var isCorpus: Boolean = false) : AbstractItem<CategoryProductListAdapter, CategoryProductListAdapter.ViewHolder>() {

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
        holder.productShortDescription.text = productDesc.limitsTo(30)

        holder.productBannerLayout.onClick {
            var productDetailIntent: Intent = Intent(context, ProductDetail::class.java)
            productDetailIntent.putExtra("PRODUCTID", product_id)
            context.startActivity(productDetailIntent)

        }

        holder.tagLayout.removeAllViews()
        if (isCorpus) {

            context.log(productName)

            val corpusTag: TagView = TagView(context, null)

            var param: ViewGroup.MarginLayoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            param.setMargins(2,2,2,2)
            corpusTag.layoutParams = param
            corpusTag.text = "Corpus"
            corpusTag.tagType = TagView.MODERN
            corpusTag.tagColor = context.resources.getColor(R.color.md_red_400)
            holder.tagLayout.addView(corpusTag)
        }

        if (isFreeShipping) {
            context.log(productName)

            val corpusTag: TagView = TagView(context, null)
            var param: ViewGroup.MarginLayoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            param.setMargins(2,2,2,2)
            corpusTag.layoutParams = param
            corpusTag.text = "Free Shipping"
            corpusTag.tagType = TagView.MODERN
            corpusTag.tagColor = context.resources.getColor(R.color.md_green_400)
            holder.tagLayout.addView(corpusTag)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var productName: TextView
        internal var productImage: ImageView
        internal var productPrice: TextView
        internal var productShortDescription: TextView
        internal var productBannerLayout: Ripple
        internal var tagLayout: LinearLayout

        init {

            productName = view.findViewById(R.id.productName) as TextView
            productImage = view.findViewById(R.id.productImage) as ImageView
            productPrice = view.findViewById(R.id.productPrice) as TextView
            productShortDescription = view.findViewById(R.id.productShortDescription) as TextView
            productBannerLayout = view.findViewById(R.id.productBannerLayout) as Ripple
            tagLayout = view.findViewById(R.id.tagLayout) as LinearLayout
        }
    }
}
