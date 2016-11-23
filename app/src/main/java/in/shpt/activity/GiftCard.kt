package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.getGiftVoucherPage
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.mcxiaoke.koi.ext.isConnected
import kotlinx.android.synthetic.main.activity_gift_card.*
import org.json.JSONObject

class GiftCard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_card)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        loadGiftVoucher()
    }

    fun loadGiftVoucher() {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(GiftVoucher(), null)
        }
    }

    inner class GiftVoucher : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getGiftVoucherPage()
        }

        override fun onPostExecute(result: JSONObject?) {

            supportActionBar!!.title = result!!.optString("heading_title")

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
}
