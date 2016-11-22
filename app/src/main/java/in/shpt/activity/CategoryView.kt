package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.CategoryProductListAdapter
import `in`.shpt.ext.getCategortProducts
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.theme
import `in`.shpt.widget.SimpleDividerItemDecoration
import android.os.AsyncTask
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.os.AsyncTaskCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import com.mcxiaoke.koi.log.logi
import com.mikepenz.fastadapter.IItemAdapter
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.FooterAdapter
import com.mikepenz.fastadapter_extensions.items.ProgressItem
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_category_view.*
import org.json.JSONArray
import org.json.JSONObject


class CategoryView : AppCompatActivity() {

    var categoryId: String = ""
    var children: String = ""
    lateinit var fastAdapter: FastItemAdapter<CategoryProductListAdapter>
    lateinit var footerAdapter: FooterAdapter<ProgressItem>
    lateinit var search: SearchView
    lateinit var drawerMenu: MenuItem

    var isSubCategoryAvailable: Boolean = false
    var tempCategoryId: String = ""
    var page: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_category_view)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        categoryId = intent.getStringExtra("CATEGORYID")
        children = intent.getStringExtra("SUBCATEGORY")


        tempCategoryId = categoryId
        navigation_view.setNavigationItemSelectedListener {
            drawer.closeDrawers()
            true
        }


        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        productList.layoutManager = llm
        productList.addItemDecoration(SimpleDividerItemDecoration(applicationContext))
        fastAdapter = FastItemAdapter()
        fastAdapter.withFilterPredicate(IItemAdapter.Predicate<CategoryProductListAdapter> { item, constraint ->
            run {
                if (constraint.toString().isEmpty() || constraint.isBlank()) {
                    true
                } else {
                    !item.productName.toLowerCase().contains(constraint.toString().toLowerCase())
                }
            }
        })
        footerAdapter = FooterAdapter()

        productList.itemAnimator = DefaultItemAnimator()
        productList.adapter = footerAdapter.wrap(fastAdapter);

        AsyncTaskCompat.executeParallel(CategoryProductLoader(), categoryId)
        productList.addOnScrollListener(object : EndlessRecyclerOnScrollListener(footerAdapter) {
            override fun onLoadMore(currentPage: Int) {
                page += 1
                AsyncTaskCompat.executeParallel(CategoryProductLoader(), tempCategoryId)
            }
        })

        setupMenu()
    }

    @UiThread
    fun setupMenu() {

        var subCategory: JSONArray = JSONArray(children)

        var navMenu: Menu = navigation_view.menu

        if (subCategory.length() > 0) {
            for (i in 0..subCategory.length() - 1) {
                var mainMenu: SubMenu = navMenu.addSubMenu(subCategory.optJSONObject(i).optString("name"))
                var smallMenuItems = subCategory.optJSONObject(i).optJSONArray("children")
                for (j in 0..smallMenuItems.length() - 1) {
                    mainMenu
                            .add(smallMenuItems.optJSONObject(j).optString("name"))
                            //  .setIcon(applicationContext.getIcon(FontAwesome.Icon.faw_angle_right, Color.WHITE, 6))
                            .setOnMenuItemClickListener {
                                page = 1
                                tempCategoryId = tempCategoryId + "_" + smallMenuItems.optJSONObject(j).optString("category_id")
                                fastAdapter.clear()
                                AsyncTaskCompat.executeParallel(CategoryProductLoader(), tempCategoryId)

                                true
                            }
                }
            }

            isSubCategoryAvailable = true
        }
    }

    inner class CategoryProductLoader : AsyncTask<String, Void, JSONObject>() {
        override fun doInBackground(vararg p0: String?): JSONObject? {
            return getCategortProducts(p0[0] as String, page.toString())
        }

        override fun onPostExecute(result: JSONObject?) {


            supportActionBar!!.title = result!!.optString("heading_title")

            var products = result.optJSONArray("products")

            for (i in 0..products.length() - 1) {
                fastAdapter.add(CategoryProductListAdapter(
                        products.optJSONObject(i).optString("thumb"),
                        products.optJSONObject(i).optString("name"),
                        products.optJSONObject(i).optString("price"),
                        products.optJSONObject(i).optString("description"),
                        products.optJSONObject(i).optString("product_id").toInt(),
                        this@CategoryView
                ))
            }

            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END)
            }
            super.onPostExecute(result)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_category_menu, menu)
        menu!!.findItem(R.id.search).icon = getIcon(FontAwesome.Icon.faw_search)
        drawerMenu = menu!!.findItem(R.id.subCategory)
        drawerMenu.icon = getIcon(FontAwesome.Icon.faw_th_large)
        menu!!.findItem(R.id.sortOrder).icon = getIcon(FontAwesome.Icon.faw_sort_alpha_desc)
        search = MenuItemCompat.getActionView(menu.findItem(R.id.search)) as SearchView

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return true
            }

            fun callSearch(query: String) {
                logi("Query : ${query}")
                fastAdapter.filter(query)
                fastAdapter.notifyDataSetChanged()
            }

        })

        if (isSubCategoryAvailable == false) {
            drawerMenu.setVisible(false)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
            R.id.subCategory -> {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END)
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
