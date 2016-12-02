package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.getWishList
import `in`.shpt.ext.init
import `in`.shpt.ext.theme
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_wish_list.*
import org.json.JSONObject

class WishList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_wish_list)
        init(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        AsyncTaskCompat.executeParallel(WishListLoader(), null)
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

    inner class WishListLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getWishList()
        }

        override fun onPostExecute(result: JSONObject?) {

            supportActionBar!!.title = result!!.optString("heading_title")
            var products = result!!.optJSONArray("products")
            if (products.length() > 0) {

            } else {
                emptyLayout.visibility = View.VISIBLE
                wishList.visibility = View.GONE
                emptyIcon.setImageDrawable(getIcon(FontAwesome.Icon.faw_dropbox, Color.RED, 120))
                emptyText.text = result!!.optString("text_empty")
            }
            super.onPostExecute(result)
        }
    }
}
