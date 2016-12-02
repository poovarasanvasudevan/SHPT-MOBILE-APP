package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.config.JSONConfig
import `in`.shpt.ext.getuserprofile
import `in`.shpt.ext.init
import `in`.shpt.ext.saveUserDetails
import `in`.shpt.ext.theme
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import kotlinx.android.synthetic.main.activity_profile_update.*
import org.json.JSONObject

class ProfileUpdate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_profile_update)
        init(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        AsyncTaskCompat.executeParallel(UserDetailLoader(), null)
        saveButton.onClick {
            if (eTelephone.text.toString().isEmpty() || eTelephone.text.toString().isBlank() || eTelephone.text.toString().length < 8 || eTelephone.text.toString().length > 15) {

                error_bg.visibility = View.VISIBLE
                error_text.text = "Enter Valid Phone Number"
            } else {
                error_bg.visibility = View.GONE
                AsyncTaskCompat.executeParallel(UserDetailSave(),
                        eFname.text.toString(),
                        eLname.text.toString(),
                        eEmail.text.toString(),
                        eTelephone.text.toString(),
                        eFax.text.toString()
                )
            }
        }
    }

    inner class UserDetailSave : AsyncTask<String, Void, JSONObject>() {
        override fun doInBackground(vararg p0: String?): JSONObject? {
            return applicationContext.saveUserDetails(
                    p0.get(0)!!,
                    p0.get(1)!!,
                    p0.get(2)!!,
                    p0.get(3)!!,
                    p0.get(4)!!
            )
        }

        override fun onPostExecute(result: JSONObject?) {
            Log.i("mytag", result.toString())
            if (result!!.optInt("code") == 200) {
                toast(result!!.optString("message"))
            }
            super.onPostExecute(result)
        }
    }

    inner class UserDetailLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return applicationContext.getuserprofile()
        }

        override fun onPostExecute(result: JSONObject?) {
            Log.i("mytag", result.toString())

            supportActionBar!!.title = result!!.optString(JSONConfig.CART_HEADING_TITLE)
            tFname.text = getHTMLLabel(result!!.optString(JSONConfig.CART_FNAME), "*")
            tLname.text = getHTMLLabel(result!!.optString(JSONConfig.CART_LNAME), "*")
            tEmail.text = getHTMLLabel(result!!.optString(JSONConfig.CART_EMAIL), "*")
            tTelephone.text = getHTMLLabel(result!!.optString(JSONConfig.CART_TELEPHONE), "*")
            tFax.text = getHTMLLabel(result!!.optString(JSONConfig.CART_FAX), "")


            eFname.setText(result!!.optString(JSONConfig.CART_FNAMEV))
            eLname.setText(result!!.optString(JSONConfig.CART_LNAMEV))
            eEmail.setText(result!!.optString(JSONConfig.CART_EMAILV))
            eTelephone.setText(result!!.optString(JSONConfig.CART_TELEPHONEV))
            eFax.setText(result!!.optString(JSONConfig.CART_FAXV))

            heading.text = result.optString(JSONConfig.CART_MYACCOUNT_TEXT)


            super.onPostExecute(result)
        }
    }

    fun getHTMLLabel(text: String, requiredText: String): Spanned? {
        var htmlText = "<b>$text <font color='red'>$requiredText</font></b>"
        return Html.fromHtml(htmlText)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                startActivity<Home>()
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
