package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.BannerAdapter
import `in`.shpt.config.Config
import `in`.shpt.config.JSONConfig
import `in`.shpt.event.ConnectionEvent
import `in`.shpt.ext.*
import `in`.shpt.models.BannerModel
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import com.mikepenz.actionitembadge.library.ActionItemBadge
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject


class Home : AppCompatActivity() {

    var cartCount: Int = 0
    lateinit var homeMenu: Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_home)


        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(getIcon(FontAwesome.Icon.faw_bars))

        navigation_view.setNavigationItemSelectedListener {
            drawer.closeDrawers()
            true
        }

        //navMenu = navigation_view.menu
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close) {

            override fun onDrawerClosed(drawerView: View) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView)
            }
        }
        drawer.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        next(isConnected())

    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    fun next(isConnected: Boolean) {
        if (isConnected) {
            emptyLayout.visibility = View.GONE
            root.visibility = View.VISIBLE
            AsyncTaskCompat.executeParallel(CategoriesLoaderTask(), null)
            AsyncTaskCompat.executeParallel(BannerLoader(), null)
            AsyncTaskCompat.executeParallel(CartLoader(), null)
        } else {
            navigation_view.menu.clear()
            root.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
            emptyIcon.setImageDrawable(getIcon(FontAwesome.Icon.faw_frown_o, Color.GRAY, 120))
            emptyText.text = "No Internet Connection..."
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ConnectionEvent) {
        next(event.isConnected)
    }

    inner class CategoriesLoaderTask : AsyncTask<Void, Void, JSONObject?>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getAllCategories()
        }

        override fun onPostExecute(result: JSONObject?) {

            var navMenu: Menu = navigation_view.menu
            var categories = result!!.optJSONArray(JSONConfig.CATEGORIES_BASE)
            var categoriesMenu: SubMenu = navMenu.addSubMenu("Categories")
            for (i in 0..(categories.length() - 1)) {
                val item = categories.getJSONObject(i)
                var mItem: MenuItem = categoriesMenu
                        .add(item.optString("name").toCamelCase())
                        .setIcon(applicationContext.getIcon(FontAwesome.Icon.faw_bookmark_o, Color.GRAY, 20))
                        .setOnMenuItemClickListener {
                            var intent1 = Intent(this@Home, CategoryView::class.java)
                            intent1.putExtra("CATEGORYID", item.optString("category_id"))
                            intent1.putExtra("SUBCATEGORY", item.optJSONArray("children").toString())
                            startActivity(intent1)
                            true
                        }
            }


            addSettingMenu()

            //  drawer.build();
            super.onPostExecute(result)
        }
    }


    inner class CartLoader : AsyncTask<Void, Void, JSONArray>() {
        override fun doInBackground(vararg p0: Void?): JSONArray? {
            return getCart()
        }

        override fun onPostExecute(result: JSONArray?) {
            cartCount = result!!.length()

            updateCartCount()

            var cartData: MutableList<BannerModel> = arrayListOf()
            for (i in 0..cartCount - 1) {
                cartData.add(BannerModel(
                        result.optJSONObject(i).optString("product_id").toInt(),
                        result.optJSONObject(i).optString("name"),
                        result.optJSONObject(i).optInt("price").toString() + ".0000",
                        "",
                        Config.IMAGE_PATH + result.optJSONObject(i).optString("image")
                ))
            }

            // cartPager.adapter = BannerAdapter(supportFragmentManager, cartData)
            super.onPostExecute(result)
        }
    }

    fun updateCartCount() {
        if (cartCount > 0) {
            ActionItemBadge.update(this, homeMenu.findItem(R.id.shoppingcart), getIcon(FontAwesome.Icon.faw_shopping_cart), ActionItemBadge.BadgeStyles.GREEN, cartCount);
        } else {
            homeMenu.findItem(R.id.shoppingcart).icon = (getIcon(FontAwesome.Icon.faw_shopping_cart));
        }
    }

    inner class BannerLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getBanner()
        }

        override fun onPostExecute(result: JSONObject?) {
            var banners: MutableList<BannerModel> = arrayListOf()

            for (i in 0..(result!!.optJSONArray("banners")).length() - 1) {
                banners.add(BannerModel(
                        result!!.optJSONArray("banners").optJSONObject(i).optString("product_id").toInt(),
                        result!!.optJSONArray("banners").optJSONObject(i).optString("title"),
                        result!!.optJSONArray("banners").optJSONObject(i).optJSONObject("full_detail").optString("price"),
                        result!!.optJSONArray("banners").optJSONObject(i).optJSONObject("full_detail").optString("meta_description"),
                        result!!.optJSONArray("banners").optJSONObject(i).optString("image")
                ))
            }

            bannerPager.adapter = BannerAdapter(supportFragmentManager, banners)


            super.onPostExecute(result)
        }
    }

    fun addSettingMenu() {
        var navMenu: Menu = navigation_view.menu
        var settingMenu: SubMenu = navMenu.addSubMenu("Application")

        settingMenu.add("My Accounts")
                .setIcon(getIcon(FontAwesome.Icon.faw_users))
                .setOnMenuItemClickListener {
                    startActivity<ProfileUpdate>()
                    true
                }
        settingMenu
                .add("Address Book")
                .setIcon(getIcon(FontAwesome.Icon.faw_home))
                .setOnMenuItemClickListener {
                    startActivity<AddressBook>()
                    true
                }

        settingMenu
                .add("Order History")
                .setIcon(getIcon(FontAwesome.Icon.faw_history))
                .setOnMenuItemClickListener {
                    startActivity<MyOrders>()
                    true
                }

        settingMenu.add("Track Order").icon = getIcon(FontAwesome.Icon.faw_location_arrow);
        settingMenu
                .add("Gift Certificate")
                .setIcon(getIcon(FontAwesome.Icon.faw_gift))
                .setOnMenuItemClickListener {
                    startActivity<GiftCard>()
                    true
                }
        settingMenu.add("Wishlist").setIcon(getIcon(FontAwesome.Icon.faw_shopping_bag)).setOnMenuItemClickListener {
            startActivity<WishList>()
            true
        }
        settingMenu.add("Feedback").icon = getIcon(FontAwesome.Icon.faw_envelope_open);
        settingMenu.add("Settings").icon = getIcon(FontAwesome.Icon.faw_cogs);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        homeMenu = menu!!;
        updateCartCount()
        menu!!.findItem(R.id.shoppingcart).icon = getIcon(FontAwesome.Icon.faw_shopping_cart)
        menu!!.findItem(R.id.search).icon = getIcon(FontAwesome.Icon.faw_search)
        menu!!.findItem(R.id.notification).icon = getIcon(FontAwesome.Icon.faw_bell)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.shoppingcart -> startActivity<ShoppingCart>()
            R.id.notification -> toast("Hello World")
            R.id.myaccount -> startActivity<ProfileUpdate>()
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START);
                return true
            }
            else -> toast("Nothing")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        super.onBackPressed()
    }

    override fun onPostResume() {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(CartLoader(), null)
        }
        drawer.closeDrawers()
        super.onPostResume()
    }

    override fun onPause() {
        drawer.closeDrawers()
        super.onPause()
    }
}
