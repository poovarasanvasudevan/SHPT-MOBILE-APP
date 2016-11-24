package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.*
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatRadioButton
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_gift_card.*
import org.json.JSONArray
import org.json.JSONObject

class GiftCard : AppCompatActivity() {

    lateinit var themeGroups: RadioGroup
    val themeConstant: Int = 10065
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_gift_card)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        loadGiftVoucher()


        purchaseButton.onClick {
            var error: Boolean = false

            if (erName.isInvalid()) {
                erName.error = getRequiredError("Recipient Name")
                error = true
            }

            if (erEmail.isInvalid() || erEmail.isEmailValid() == false) {
                erEmail.error = "Invalid Recipient Email"
                error = true
            }

            if (eyName.isInvalid()) {
                eyName.error = getRequiredError("Your Name")
                error = true
            }

            if (eyEmail.isInvalid() || eyEmail.isEmailValid() == false) {
                eyEmail.error = "Invalid Email"
                error = true
            }

            if (themeGroups.checkedRadioButtonId == -1) {
                themeError.visibility = View.VISIBLE
                error = true
            } else {
                themeError.visibility = View.GONE
               // toast("You Selected ${themeGroups.checkedRadioButtonId.minus(themeConstant)}")
            }

            if (eyAmount.text.toString().isEmpty() || eyAmount.text.toString().isBlank() || eyAmount.text.toString().toInt() < 1 || eyAmount.text.toString().toInt() > 1000) {
                eyAmount.error = "Invalid Amount"
                error = true
            }

            if (agree.isChecked == false) {
                agree.error = "Please accept the Agreement"
                error = true
            }


            if (error == false) {
                //insert giftVoucher....
                getIcon(FontAwesome.Icon.faw_check)
                addGiftVoucher()
            }
        }

        gotoCartBtn.onClick {
            startActivity<ShoppingCart>()
        }
    }

    fun loadGiftVoucher() {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(GiftVoucher(), null)
        }
    }

    fun addGiftVoucher() {
        if (isConnected()) {
            AsyncTaskCompat.executeParallel(AddGiftVoucher(), null)
        }
    }

    inner class AddGiftVoucher : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return addGiftVoucher(
                    erName.text.toString(),
                    erEmail.text.toString(),
                    eyName.text.toString(),
                    eyEmail.text.toString(),
                    themeGroups.checkedRadioButtonId.minus(themeConstant).toString(),
                    eyMessage.text.toString(),
                    eyAmount.text.toString()
            )
        }

        override fun onPostExecute(result: JSONObject?) {

            if (result!!.optInt("code", 0) != 0) {
                toast("Voucher Added to Your Cart Succesfully")
                fullLayout.visibility = View.GONE
                successLayout.visibility = View.VISIBLE
                supportActionBar!!.title = "Success"
            }
            super.onPostExecute(result)
        }
    }

    inner class GiftVoucher : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return getGiftVoucherPage()
        }

        override fun onPostExecute(result: JSONObject?) {

            supportActionBar!!.title = result!!.optString("heading_title")
            heading.text = result.optString("heading_title")
            subheading.text = "** " + result.optString("text_description")
            trName.loadMandatory(result.optString("entry_to_name"), "*")
            trEmail.loadMandatory(result.optString("entry_to_email"), "*")
            tyName.loadMandatory(result.optString("entry_from_name"), "*")
            tyEmail.loadMandatory(result.optString("entry_from_email"), "*")
            gcTheme.loadMandatory(result.optString("entry_theme"), "*")
            tyMessage.loadMandatory(result.optString("entry_message"), "")
            tyAmount.loadMandatory(result.optString("entry_amount"), "*")
            agree.text = result.optString("text_agree")

            eyName.setText(result.optString("from_name"))
            eyEmail.setText(result.optString("from_email"))

            var themes: JSONArray = result.optJSONArray("voucher_themes")
            themeGroups = RadioGroup(this@GiftCard)
            themeGroups.orientation = LinearLayout.VERTICAL
            themeGroups.id = R.id.giftcardthemeselection

            for (i in 0..themes.length() - 1) {
                var themeItem: AppCompatRadioButton = AppCompatRadioButton(this@GiftCard)
                themeItem.tag = themes.optJSONObject(i).optString("voucher_theme_id")
                themeItem.text = themes.optJSONObject(i).optString("name")
                themeItem.id = themeConstant + themes.optJSONObject(i).optString("voucher_theme_id").toInt()
                themeGroups.addView(themeItem)
            }

            themeGroup.addView(themeGroups)
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
