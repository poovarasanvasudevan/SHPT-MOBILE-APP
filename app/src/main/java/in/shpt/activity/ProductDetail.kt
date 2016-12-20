package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.ProductDetailPagertAdapter
import `in`.shpt.config.Config
import `in`.shpt.event.ConnectionEvent
import `in`.shpt.ext.*
import `in`.shpt.widget.ImageZoom
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.design.widget.TabLayout.OnTabSelectedListener
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.mcxiaoke.koi.ext.*
import com.mikepenz.actionitembadge.library.ActionItemBadge
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject




class ProductDetail : AppCompatActivity(), OnTabSelectedListener {

    lateinit var fetchedResult: JSONObject
    lateinit var imageZoom : ImageZoom

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        pager.currentItem = tab?.position!!
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
    var productId: Int = 0
    var cartCount: Int = 0
    var isCorpus = false
    var isPrerelease = false
    lateinit var productMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_product_detail)
        init(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        productId = intent.getIntExtra("PRODUCTID", 0)
        isCorpus = intent.getBooleanExtra("CORPUS", false)
        isPrerelease = intent.getBooleanExtra("PRERELEASE", false)

        imageZoom = ImageZoom(this)

        // toast("Product : ${productId}")
        next(isConnected())

        addtocart.onClick {
            if (fetchedResult.optJSONArray("options").length() > 0) {
                var optionIntent = Intent(this@ProductDetail, ProductOptionSelector::class.java)
                optionIntent.putExtra("FULLPRODUCT", fetchedResult.toString())
                startActivity(optionIntent)
            } else {
                addToCart()
            }
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return imageZoom.onDispatchTouchEvent(ev!!) || super.dispatchTouchEvent(ev);
    }
    override fun onPostResume() {
        loadCart()
        super.onPostResume()
    }


    fun loadCart() {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(CartLoader(), null)
        }
    }

    inner class CartLoader : AsyncTask<Void, Void, JSONArray>() {
        override fun doInBackground(vararg p0: Void?): JSONArray? {
            return getCart()
        }

        override fun onPostExecute(result: JSONArray?) {

            cartCount = 0
            for (i in 0..result!!.length() - 1) {
                cartCount += result.optJSONObject(i).optInt("quantity")
            }

            updateCartCount()

            super.onPostExecute(result)
        }
    }

    fun updateCartCount() {
        if (cartCount > 0) {
            ActionItemBadge.update(this, productMenu.findItem(R.id.cart), getIcon(FontAwesome.Icon.faw_shopping_cart), ActionItemBadge.BadgeStyles.GREEN, cartCount);
        } else {
            productMenu.findItem(R.id.cart).icon = (getIcon(FontAwesome.Icon.faw_shopping_cart));
        }
    }

    fun next(isConnected: Boolean) {
        if (isConnected) {
            emptyLayout.visibility = View.GONE
            fullLayout.visibility = View.VISIBLE
            AsyncTaskCompat.executeParallel(ProductDetailLoader(), productId)
        } else {
            supportActionBar!!.title = resources.getString(R.string.no_internet)
            fullLayout.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
            emptyIcon.setImageDrawable(getIcon(FontAwesome.Icon.faw_frown_o, Color.GRAY, 120))
            emptyText.text = resources.getString(R.string.no_internet)
        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ConnectionEvent) {
        next(event.isConnected)
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

                loadCart()
            }
            super.onPostExecute(result)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        productMenu = menu!!
        menu!!.findItem(R.id.cart).icon = getIcon(FontAwesome.Icon.faw_shopping_cart)

        loadCart()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
            R.id.cart -> startActivity<ShoppingCart>()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class ProductDetailLoader : AsyncTask<Int, Void, JSONObject>() {
        override fun onPreExecute() {
            progress.visibility = View.VISIBLE
            fullLayout.visibility = View.GONE
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Int?): JSONObject? {
            return getProductDetail(p0[0] as Int)
        }

        override fun onPostExecute(result: JSONObject?) {

            tabLayout.removeAllTabs()

            supportActionBar!!.title = result!!.optString("heading_title")

            if (intent.getBooleanExtra("SEARCH", false)) {

                var obj: ParseObject = ParseObject(Config.RECENTPRODUCT_CLASS)
                obj.put("product_id", productId)
                obj.put("product_name", result.optString("heading_title"))
                obj.put("created", System.currentTimeMillis())
                obj.pinInBackground()
            }

            tabLayout.addTab(tabLayout.newTab())
            tabLayout.addTab(tabLayout.newTab())
            tabLayout.addTab(tabLayout.newTab())
            tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            pager.adapter = ProductDetailPagertAdapter(supportFragmentManager, isCorpus, isPrerelease,result, tabLayout.tabCount)
            tabLayout.addOnTabSelectedListener(this@ProductDetail)
            tabLayout.setupWithViewPager(pager)
            pager.offscreenPageLimit = tabLayout.tabCount



            fetchedResult = result


            progress.visibility = View.GONE
            fullLayout.visibility = View.VISIBLE


            loadCart()
            super.onPostExecute(result)
        }
    }
}
