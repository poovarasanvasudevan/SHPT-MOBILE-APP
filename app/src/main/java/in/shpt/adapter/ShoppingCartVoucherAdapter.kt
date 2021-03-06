package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.event.ItemRemovedFromCartEvent
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.removeFromCart
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.graphics.Color
import android.os.AsyncTask
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.toast
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * Created by poovarasanv on 15/11/16.
 */

class ShoppingCartVoucherAdapter(
        var productId: String,
        var productName: String,
        var price: String,
        var context: Activity,
        var isAlertable: Boolean = true) : AbstractItem<ShoppingCartVoucherAdapter, ShoppingCartVoucherAdapter.ViewHolder>(), PopupMenu.OnMenuItemClickListener {


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.delete_item -> deleteFromCart()
            else -> context.toast("clicked")
        }
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

        if (isAlertable) {

            holder.overflowIcon.onClick { view ->
                val popup = PopupMenu(view.context, view)
                popup.inflate(R.menu.cart_voucher_menu)
                popup.setOnMenuItemClickListener(this)
                popup.show()
            }
        } else {
            holder.overflowIcon.visibility = View.GONE
        }

    }

    fun deleteFromCart() {
        if (context.isConnected()) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Are You Sure?")
            builder.setMessage("Are you sure you want to remove ${productName} from cart ...")
            builder.setPositiveButton("OK", { dialogInterface, i ->
                run {
                    AsyncTaskCompat.executeParallel(DeleteFromCart(), null)
                }
            })
            builder.setNegativeButton("Cancel", null)
            builder.show()
        } else {
            context.toast("No Internet Connection")
        }
    }

    inner class DeleteFromCart : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return context.removeFromCart(productId)
        }

        override fun onPostExecute(result: JSONObject?) {

            if (result!!.optInt("code", 0) != 0) {
                context.toast("Item Removed From Cart")

                EventBus.getDefault().post(ItemRemovedFromCartEvent(true))
            }
            super.onPostExecute(result)
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
