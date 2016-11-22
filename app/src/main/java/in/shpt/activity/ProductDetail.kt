package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.ImagePagerAdapter
import `in`.shpt.ext.getCart
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.getProductDetail
import `in`.shpt.ext.theme
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
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
        initNavigationBar()

        AsyncTaskCompat.executeParallel(ProductDetailLoader(), productId)
    }

    fun initNavigationBar() {
        bottomMenu = productNavigation.menu
        bottomMenu.findItem(R.id.productdetailshoppingcart).setIcon(getIcon(FontAwesome.Icon.faw_shopping_cart))
        bottomMenu.findItem(R.id.productdetailwishlist).setIcon(getIcon(FontAwesome.Icon.faw_shopping_bag))
        bottomMenu.findItem(R.id.productdetailaddtowishlist).setIcon(getIcon(FontAwesome.Icon.faw_shopping_basket))
        bottomMenu.findItem(R.id.addtocart).setIcon(getIcon(FontAwesome.Icon.faw_cart_plus))

        AsyncTaskCompat.executeParallel(CartLoader(),null)
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
