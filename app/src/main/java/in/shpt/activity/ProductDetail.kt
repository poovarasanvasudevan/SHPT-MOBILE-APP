package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.ImagePagerAdapter
import `in`.shpt.ext.*
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.mcxiaoke.koi.ext.*
import com.mikepenz.actionitembadge.library.ActionItemBadge
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class ProductDetail : AppCompatActivity() {

    var productId: Int = 0
    lateinit var bottomMenu: Menu
    var cartCount: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_product_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        productId = intent.getIntExtra("PRODUCTID", 0)
        AsyncTaskCompat.executeParallel(ProductDetailLoader(), productId)

        addtocart.onClick {
            addToCart()
        }

        addtocart.onLongClick {
            var quantityInp: String = ""
            var promptsView: View = inflate(R.layout.quantity_prompt)
            val alertDialogBuilder = AlertDialog.Builder(this@ProductDetail)

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView)

            val userInput = promptsView
                    .findViewById(R.id.input_quantity) as EditText

            alertDialogBuilder
                    .setCancelable(true)
                    .setPositiveButton("OK"
                    ) { dialog, id ->
                        quantityInp = userInput.text.toString()
                        addToCart(quantityInp)
                    }
                    .setNegativeButton("Cancel"
                    ) { dialog, id -> dialog.cancel() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            true
        }
    }

    fun addToCart(quantity: String = "1") {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(AddToCart(), quantity)
        }
    }

    inner class AddToCart : AsyncTask<String, Void, JSONObject>() {
        override fun doInBackground(vararg p0: String?): JSONObject? {
            return addToCart(productId.toString(), p0[0] as String)
        }

        override fun onPostExecute(result: JSONObject?) {
            if (result!!.optString("success", "NULL") != "NULL") {
                toast("Product added to Cart Succesfully")
                addtocart.setImageDrawable(getIcon(FontAwesome.Icon.faw_check))
            }
            super.onPostExecute(result)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class ProductDetailLoader : AsyncTask<Int, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Int?): JSONObject? {
            return getProductDetail(p0[0] as Int)
        }

        override fun onPostExecute(result: JSONObject?) {
            supportActionBar!!.title = result!!.optString("heading_title")

            var imageList = ArrayList<String>()
            var images: JSONArray = result.optJSONArray("images")
            imageList.add(result.optString("thumb"))
            for (i in 0..images.length() - 1) {
                imageList.add(images.optJSONObject(i).optString("thumb"))
            }

            imageList.add(result.optString("thumb"))
            var imageAdapter = ImagePagerAdapter(supportFragmentManager, imageList)
            imagePager.adapter = imageAdapter


            productDetailName.text = result.optString("heading_title")
            productDetailDescription.text = Html.fromHtml(result.optString("description"))
            super.onPostExecute(result)
        }
    }


    inner class CartLoader : AsyncTask<Void, Void, JSONArray>() {
        override fun doInBackground(vararg p0: Void?): JSONArray? {
            return getCart()
        }

        override fun onPostExecute(result: JSONArray?) {
            cartCount = result!!.length()

            if (cartCount > 0) {
                ActionItemBadge.update(this@ProductDetail, bottomMenu.findItem(R.id.productdetailshoppingcart), getIcon(FontAwesome.Icon.faw_shopping_cart), ActionItemBadge.BadgeStyles.GREEN, cartCount);
            } else {
                bottomMenu.findItem(R.id.productdetailshoppingcart).icon = (getIcon(FontAwesome.Icon.faw_shopping_cart));
            }
            //   bottomMenu.findItem(R.id.productdetailshoppingcart).setIcon(getIcon(FontAwesome.Icon.faw_shopping_cart))
            super.onPostExecute(result)
        }
    }
}
