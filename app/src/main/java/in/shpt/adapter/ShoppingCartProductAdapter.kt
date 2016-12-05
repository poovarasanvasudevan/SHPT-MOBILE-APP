package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.event.ItemRemovedFromCartEvent
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.loadUrl
import `in`.shpt.ext.modifyCart
import `in`.shpt.ext.removeFromCart
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.mcxiaoke.koi.ext.*
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


/**
 * Created by poovarasanv on 15/11/16.
 */

class ShoppingCartProductAdapter(
        var productId: String,
        var productName: String,
        var image: String,
        var quantity: String,
        var price: String,
        var options: JSONArray,
        var context: Activity,
        var isAlertable: Boolean = true,
        var clickable: Boolean = true) : AbstractItem<ShoppingCartProductAdapter, ShoppingCartProductAdapter.ViewHolder>(), PopupMenu.OnMenuItemClickListener {


    override fun onMenuItemClick(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.delete_item -> deleteFromCart()
            R.id.modify_item -> modifyItem()
            else -> context.toast("clicked")
        }
        // context.toast("Clicked...")
        return true
    }

    fun modifyItem() {
        if (context.isConnected()) {
            var quantityInp: String = ""
            var promptsView: View = context.inflate(R.layout.quantity_prompt)
            val alertDialogBuilder = AlertDialog.Builder(context)

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView)

            val userInput = promptsView
                    .findViewById(R.id.input_quantity) as EditText

            userInput.setText(quantity)
            userInput.setSelection(userInput.text.length)

            alertDialogBuilder
                    .setCancelable(true)
                    .setPositiveButton("OK", null)
                    .setNegativeButton("Cancel"
                    ) { dialog, id -> dialog.cancel() }


            val alertDialog = alertDialogBuilder.create()

            alertDialog.setOnShowListener(OnShowListener {
                val b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                b.setOnClickListener {
                    quantityInp = userInput.text.toString()
                    if (quantityInp.isEmpty() || quantityInp.isBlank()) {
                        userInput.error = "Invalid Quantity"
                    } else {
                        modifyCart(quantityInp)
                        alertDialog.dismiss()
                    }
                }
            })
            alertDialog.show()

        } else {
            context.toast("No INternet Connection")
        }
    }

    fun modifyCart(quantity: String) {
        var map: HashMap<String, String> = HashMap()
        var qu: String = "quantity[$productId]"
        map.put(qu, quantity)

        AsyncTaskCompat.executeParallel(ModifyCart(), map)
    }

    inner class ModifyCart : AsyncTask<HashMap<String, String>, Void, JSONObject>() {
        override fun doInBackground(vararg p0: HashMap<String, String>?): JSONObject? {
            return context.modifyCart(p0[0] as HashMap<String, String>)
        }

        override fun onPostExecute(result: JSONObject?) {
            if (result!!.optInt("code", 0) != 0) {

                //context.longToast(result.toString())
                context.longToast("${productName} Modified From Cart")

                EventBus.getDefault().post(ItemRemovedFromCartEvent(true))
            }
            super.onPostExecute(result)
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
                context.toast("${productName} Removed From Cart")

                EventBus.getDefault().post(ItemRemovedFromCartEvent(true))
            }
            super.onPostExecute(result)
        }
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

        if (options.length() > 0) {
            var optionString: String = ""
            for (i in 0..options.length() - 1) {
                optionString += options.optJSONObject(i).optString("name") + " : " + options.optJSONObject(i).optString("value") + "\n"
            }

            holder.shoppingCartProductOptions.text = optionString
        } else {
            holder.shoppingCartProductOptions.visibility = View.GONE
        }

        if (isAlertable) {

            holder.overflowIcon.onClick { view ->
                val popup = PopupMenu(view.context, view)
                popup.inflate(R.menu.cart_item_menu)
                popup.setOnMenuItemClickListener(this)
                popup.show()
            }
        } else {
            holder.overflowIcon.visibility = View.GONE
        }

        if (clickable) {

            holder.cartProductItem.onClick {
                var bundle = Bundle {
                    putInt("PRODUCTID", productId.split(":")[0].toInt())
                }
                context.startActivity<ProductDetail>(Intent.FLAG_ACTIVITY_NEW_TASK, bundle)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var shoppingCartProductImage: ImageView
        internal var shoppingCartProductName: TextView
        internal var shoppingCartProductQuantity: TextView
        internal var shoppingCartProductPrice: TextView
        internal var shoppingCartProductOptions: TextView
        internal var overflowIcon: ImageButton
        internal var cartProductItem: Ripple

        init {

            shoppingCartProductImage = view.findViewById(R.id.shoppingCartProductImage) as ImageView
            shoppingCartProductName = view.findViewById(R.id.shoppingCartProductName) as TextView
            shoppingCartProductQuantity = view.findViewById(R.id.shoppingCartProductQuantity) as TextView
            shoppingCartProductPrice = view.findViewById(R.id.shoppingCartProductPrice) as TextView
            shoppingCartProductOptions = view.findViewById(R.id.shoppingCartProductOptions) as TextView
            overflowIcon = view.findViewById(R.id.overflowIcon) as ImageButton
            cartProductItem = view.findViewById(R.id.cartProductItem) as Ripple
        }
    }
}
