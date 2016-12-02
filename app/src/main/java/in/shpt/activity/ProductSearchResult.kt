package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.init
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


class ProductSearchResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_search_result)
        init(this)
        handleIntent(intent);

    }

    override fun onNewIntent(intent: Intent?) {
        handleIntent(intent!!)
        super.onNewIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            Log.i("Search123", query)
        }
    }

}
