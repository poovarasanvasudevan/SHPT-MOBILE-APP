package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.ext.getIcon
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.toast
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fontawesome_typeface_library.FontAwesome

/**
 * Created by poovarasanv on 15/11/16.
 */

class ShoppingCartVoucherAdapter(
        var productId: String,
        var productName: String,
        var price: String,
        var context: Activity) : AbstractItem<ShoppingCartVoucherAdapter, ShoppingCartVoucherAdapter.ViewHolder>(), PopupMenu.OnMenuItemClickListener {


    override fun onMenuItemClick(item: MenuItem?): Boolean {

        context.toast("Clicked...")
        return true;
    }

    override fun getType(): Int {
        return R.id.shoppingcartvoucheradapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.shopping_cart_voucher
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        holder.voucherImage.setImageDrawable(context.getIcon(FontAwesome.Icon.faw_gift, Color.RED, 20))
        holder.overflowIcon.setImageDrawable(context.getIcon(FontAwesome.Icon.faw_ellipsis_v, Color.BLACK, 20))
        holder.shoppingCartProductName.text = productName
        holder.shoppingCartProductPrice.text = price


        holder.overflowIcon.onClick { view ->
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.cart_item_menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var shoppingCartProductName: TextView
        internal var shoppingCartProductPrice: TextView
        internal var overflowIcon: ImageButton
        internal var cartProductItem: Ripple
        internal var voucherImage: ImageView

        init {

            shoppingCartProductName = view.findViewById(R.id.shoppingCartProductName) as TextView
            shoppingCartProductPrice = view.findViewById(R.id.shoppingCartProductPrice) as TextView
            overflowIcon = view.findViewById(R.id.overflowIcon) as ImageButton
            cartProductItem = view.findViewById(R.id.cartProductItem) as Ripple
            voucherImage = view.findViewById(R.id.voucherImage) as ImageView

        }
    }
}
