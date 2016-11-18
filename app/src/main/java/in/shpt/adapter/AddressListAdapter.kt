package `in`.shpt.adapter

import `in`.shpt.R
import `in`.shpt.activity.AddressBook
import `in`.shpt.activity.EditAddress
import `in`.shpt.ext.deleteAddress
import `in`.shpt.widget.Ripple
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.widget.TextView
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import com.mikepenz.fastadapter.items.AbstractItem
import org.json.JSONObject
import java.util.*

/**
 * Created by poovarasanv on 15/11/16.
 */

class AddressListAdapter(
        var address_id: String,
        var isDefault: Boolean = false,
        var addressCount: Int,
        var address: String,
        var context: Activity) : AbstractItem<AddressListAdapter, AddressListAdapter.ViewHolder>() {

    override fun getType(): Int {
        return R.id.addressbookadapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.address_book_entries
    }

    override fun bindView(holder: ViewHolder, payloads: List<*>?) {
        super.bindView(holder, payloads)

        // holder.OrderDetailPaymentLabel.text = add
        holder.addressData.text = Html.fromHtml(address)
        holder.addressEditBtn.setOnClickListener {
            var editAddressIntent: Intent = Intent(context, EditAddress::class.java)
            editAddressIntent.putExtra("ADDRESSID", address_id.toInt())
            editAddressIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(editAddressIntent)
        }

        holder.addressDeleteBtn.setOnClickListener {

            val builder: AlertDialog = AlertDialog.Builder(context)
                    .setTitle("Are You Sure")
                    .setMessage("Are You sure you want to delete the Address")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        run {
                            val list = ArrayList<String>()
                            if (addressCount < 2) {
                                list.add("You Have No ther address Id")
                            }

                            if (isDefault) {
                                list.add("You Can't Delete Default Address")
                            }

                            if (list.size == 0) {
                                //delete
                                AsyncTaskCompat.executeParallel(DeleteAddress(), address_id.toInt())
                            } else {
                                var str: String = ""
                                list.forEach { str += it + "\n" }
                                context.toast(str.trim())
                            }

                        }
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

                    }).create()

            builder.show()


        }
    }

    inner class DeleteAddress : AsyncTask<Int, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Int?): JSONObject? {
            return context.deleteAddress(p0[0] as Int)
        }

        override fun onPostExecute(result: JSONObject?) {
            context.toast(context.getString(R.string.address_delete_success))
            context.startActivity<AddressBook>()
            context.finish()
            super.onPostExecute(result)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var addressData: TextView
        internal var addressEditBtn: Ripple
        internal var addressDeleteBtn: Ripple


        init {

            addressData = view.findViewById(R.id.addressData) as TextView
            addressEditBtn = view.findViewById(R.id.addressEditBtn) as Ripple
            addressDeleteBtn = view.findViewById(R.id.addressDeleteBtn) as Ripple
        }
    }
}
