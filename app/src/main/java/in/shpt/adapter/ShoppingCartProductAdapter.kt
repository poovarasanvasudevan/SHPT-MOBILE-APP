package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.loadUrl
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.mcxiaoke.koi.ext.Bundle
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fontawesome_typeface_library.FontAwesome

/**
 * Created by poovarasanv on 15/11/16.
 */

class ShoppingCartProductAdapter(
        var productId: String,
        var productName: String,
        var image: String,
        var quantity: String,
        var price: String,
        var context: Activity) : AbstractItem<ShoppingCartProductAdapter, ShoppingCartProductAdapter.ViewHolder>(), PopupMenu.OnMenuItemClickListener {


    override fun onMenuItemClick(item: MenuItem?): Boolean {

        context.toast("Clicked...")
        return true;
    }

    override fun getType(): Int {
        return R.id.shoppingcartproductadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.shopping_cart_products
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.overflowIcon.setImageDrawable(context.getIcon(FontAwesome.Icon.faw_ellipsis_v, Color.BLACK, 20))
        holder.shoppingCartProductImage.loadUrl(image)
        holder.shoppingCartProductName.text = productName
        holder.shoppingCartProductQuantity.text = "Qty .(${quantity})"
        holder.shoppingCartProductPrice.text = price


        holder.overflowIcon.onClick { view ->
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.cart_item_menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
        }

        holder.cartProductItem.onClick {
            var bundle = Bundle {
                putInt("PRODUCTID", productId.toInt())
            }
            context.startActivity<ProductDetail>(Intent.FLAG_ACTIVITY_NEW_TASK, bundle)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var shoppingCartProductImage: ImageView
        internal var shoppingCartProductName: TextView
        internal var shoppingCartProductQuantity: TextView
        internal var shoppingCartProductPrice: TextView
        internal var overflowIcon: ImageButton
        internal var cartProductItem: Ripple

        init {

            shoppingCartProductImage = view.findViewById(R.id.shoppingCartProductImage) as ImageView
            shoppingCartProductName = view.findViewById(R.id.shoppingCartProductName) as TextView
            shoppingCartProductQuantity = view.findViewById(R.id.shoppingCartProductQuantity) as TextView
            shoppingCartProductPrice = view.findViewById(R.id.shoppingCartProductPrice) as TextView
            overflowIcon = view.findViewById(R.id.overflowIcon) as ImageButton
            cartProductItem = view.findViewById(R.id.cartProductItem) as Ripple


        }
    }
}
