package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.adapter.AddressListAdapter
import `in`.shpt.ext.getAddress
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.init
import `in`.shpt.ext.theme
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.mcxiaoke.koi.ext.startActivity
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_address_book.*
import org.json.JSONArray
import org.json.JSONObject


class AddressBook : AppCompatActivity() {

    lateinit var addressAdapter: FastItemAdapter<AddressListAdapter>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        theme()
        setContentView(R.layout.activity_address_book)
        init(this)


        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        addressList.layoutManager = llm

        addressAdapter = FastItemAdapter()
        addressList.itemAnimator = DefaultItemAnimator()
        addressList.adapter = addressAdapter

        AsyncTaskCompat.executeParallel(AddressLoader(), null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.address_book_message, menu)
        menu!!.findItem(R.id.addAddress).icon = getIcon(FontAwesome.Icon.faw_plus)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
            R.id.addAddress -> startActivity<EditAddress>()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class AddressLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getAddress()
        }

        override fun onPostExecute(result: JSONObject?) {

            addressAdapter.clear()
            supportActionBar!!.title = result!!.optString("heading_title")
            var address: JSONArray = result.optJSONArray("addresses")
            for (i in 0..address.length() - 1) {
                addressAdapter.add(AddressListAdapter(
                        address.getJSONObject(i).optString("address_id"),
                        if (address.getJSONObject(i).optString("address_id") == address.getJSONObject(i).optString("default")) true else false,
                        address.length(),
                        address.getJSONObject(i).optString("address"),
                        this@AddressBook
                ))
            }
            super.onPostExecute(result)
        }
    }

    override fun onPostResume() {
        AsyncTaskCompat.executeParallel(AddressLoader(), null)
        super.onPostResume()
    }
}
