package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.init
import `in`.shpt.ext.log
import `in`.shpt.ext.searchProducts
import `in`.shpt.ext.theme
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView.OnQueryTextListener
import android.view.Menu
import android.view.MenuItem
import com.mcxiaoke.koi.ext.isConnected
import kotlinx.android.synthetic.main.activity_product_search_result.*
import org.json.JSONArray


class ProductSearchResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_product_search_result)
        init(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        search_view.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!!.length > 2) {
                    searchProductsFun(newText)
                } else {

                }
                return true;
            }
        })
    }

    fun searchProductsFun(term: String) {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(SearchProducts(), term)
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

    inner class SearchProducts : AsyncTask<String, Void, JSONArray>() {
        override fun doInBackground(vararg p0: String?): JSONArray {
            return searchProducts(p0[0] as String)
        }

        override fun onPostExecute(result: JSONArray?) {
            //optJson and add to list

            log(result.toString())
            super.onPostExecute(result)
        }
    }
}
