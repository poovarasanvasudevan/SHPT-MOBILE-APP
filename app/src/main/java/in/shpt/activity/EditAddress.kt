package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.*
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.toast
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class EditAddress : AppCompatActivity() {

    var address_id: Int = 0
    var selectedCountryId: Int = 0
    var selectedStateId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_edit_address)
        init(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        address_id = intent.getIntExtra("ADDRESSID", 0)

        if (address_id > 0) {
            AsyncTaskCompat.executeParallel(AddressLoader(), address_id)
        } else {
            AsyncTaskCompat.executeParallel(AddAddressLoader())
        }
        saveAddressBtn.onClick {
            val errorList = ArrayList<String>()

            if (aFname.text.toString().addressValidate()) {
                errorList.add("First Name is Required")
                aFname.error = "First Name is Required"
            }

            if (aLname.text.toString().addressValidate()) {
                errorList.add("Last Name is Required")
                aLname.error = "Last Name is Required"
            }

            if (aAddress1.text.toString().addressValidate()) {
                errorList.add("Address 1 is Required")
                aAddress1.error = "Address 1 is Required"
            }


            if (aCity.text.toString().addressValidate()) {
                errorList.add("City is Required")
                aCity.error = "City is Required"
            }

            if (aPostCode.text.toString().addressValidate()) {
                errorList.add("Postal Code is Required")
                aPostCode.error = "Postal Code is Required"
            }

            if (aCountry.text.toString().addressValidate() || selectedCountryId == 0) {
                errorList.add("Country is Required")
                aCountry.error = "Country is Required"
            }

            if (aState.text.toString().addressValidate() || selectedStateId == 0) {
                errorList.add("State/religion is Required")
                aState.error = "State/religion is Required"
            }

            if (errorList.size == 0) {
                //submit
                if (address_id > 0) {
                   // toast(" " + selectedCountryId)
                    AsyncTaskCompat.executeParallel(UpdateAddress(), null)
                } else {
                    //addAddress
                    AsyncTaskCompat.executeParallel(InsertNewAddress(), null)
                }
            }
        }
    }

    inner class UpdateAddress : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject {
            return applicationContext.updateAddress(
                    address_id,
                    aFname.text.toString(),
                    aLname.text.toString(),
                    aCompany.text.toString(),
                    aAddress1.text.toString(),
                    aAddress2.text.toString(),
                    aCity.text.toString(),
                    aPostCode.text.toString(),
                    selectedCountryId,
                    selectedStateId,
                    if (yes.isChecked) 1 else 0
            )
        }

        override fun onPostExecute(result: JSONObject?) {

            val code: Int = result!!.optInt("code")
            if (code == 200) {
                toast(result.optString("message"))
                finish()
            }
            Log.i("UpdateAddress", result.toString())
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

    inner class AddressLoader : AsyncTask<Int, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Int?): JSONObject? {
            return editAddress(p0[0] as Int)
        }

        override fun onPostExecute(result: JSONObject?) {
            supportActionBar!!.title = result!!.optString("text_edit_address")
            aFnameLabel.text = getHTMLLabel(result.optString("entry_firstname"), "*")
            aLnameLabel.text = getHTMLLabel(result.optString("entry_lastname"), "*")
            aCompanyLabel.text = getHTMLLabel(result!!.optString("entry_company"), "")
            aAddress1Label.text = getHTMLLabel(result!!.optString("entry_address_1"), "*")
            aAddress2Label.text = getHTMLLabel(result!!.optString("entry_address_2"), "")
            aCityLabel.text = getHTMLLabel(result!!.optString("entry_city"), "*")
            aPostCodeLabel.text = getHTMLLabel(result!!.optString("entry_postcode"), "*")
            aCountryLabel.text = getHTMLLabel(result!!.optString("entry_country"), "*")
            aStateLabel.text = getHTMLLabel(result!!.optString("entry_zone"), "*")
            defaultAddressLable.text = getHTMLLabel(result!!.optString("entry_default"), "")


            aFname.setText(result!!.optString("firstname"))
            aLname.setText(result!!.optString("lastname"))
            aCompany.setText(result!!.optString("company"))
            aAddress1.setText(result!!.optString("address_1"))
            aAddress2.setText(result!!.optString("address_2"))
            aCity.setText(result!!.optString("city"))
            aPostCode.setText(result!!.optString("postcode"))

            if (result!!.optBoolean("default")) yes.isChecked = true


            val list = ArrayList<String>()

            var country: JSONArray = result.optJSONArray("countries")
            aCountry.setText(country.optJSONObject(result!!.optString("country_id").toInt()).optString("name"))
            var myCountry = result!!.optString("country_id").toInt()
            var selectedCountry: String = ""
            for (i in 0..country.length() - 1) {
                list.add(country.optJSONObject(i).optString("name"))
                if (country.optJSONObject(i).optString("country_id").toInt() == myCountry) {
                    selectedCountryId = country.optJSONObject(i).optString("country_id").toInt()
                    selectedCountry = country.optJSONObject(i).optString("name");
                    aCountry.setText(selectedCountry)
                }
            }

            val dataAdapter = ArrayAdapter<String>(applicationContext,
                    android.R.layout.simple_spinner_item, list)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            aCountry.setAdapter(dataAdapter)

            AsyncTaskCompat.executeParallel(StateLoader(), result!!.optString("country_id").toInt(), result!!.optString("zone_id").toInt())

            aCountry.setOnItemClickListener { adapterView, view, i, l ->
                run {
                    //toast(l.toString())
                    val index = list.indexOf(aCountry.getText().toString())
                    selectedCountryId = index
                    var country_id = country.optJSONObject(index).optString("country_id")
                    AsyncTaskCompat.executeParallel(StateLoader(), country_id.toInt(), 0)
                }
            }
            super.onPostExecute(result)
        }
    }

    inner class StateLoader : AsyncTask<Int, Void, JSONObject>() {

        var zoneId: Int = 0
        override fun doInBackground(vararg p0: Int?): JSONObject? {
            Log.i("COuntry ${p0[0]}", "==>")
            zoneId = if (p0[1] == null) 0 else p0[1] as Int

            Log.i("ZoneId ${zoneId}:", "==>")
            return getState(p0[0] as Int)
        }

        override fun onPostExecute(result: JSONObject?) {

            var zone: JSONArray = result!!.optJSONArray("zone")
            val list = ArrayList<String>()



            for (i in 0..zone.length() - 1) {
                list.add(zone.optJSONObject(i).optString("name"))
                if (zoneId > 0) {
                    if (zone.optJSONObject(i).optString("zone_id").toInt() == zoneId) {
                        aState.setText(zone.optJSONObject(i).optString("name"))
                        selectedStateId = zoneId
                    }
                }
            }
            val dataAdapter = ArrayAdapter<String>(applicationContext,
                    android.R.layout.simple_spinner_item, list)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            aState.setAdapter(dataAdapter)

            aState.setOnItemClickListener { adapterView, view, i, l ->
                run {
                    val index = list.indexOf(aState.getText().toString())
                    selectedStateId = zone.optJSONObject(index).optString("zone_id").toInt()
                }
            }

            super.onPostExecute(result)
        }
    }

    fun getHTMLLabel(text: String, requiredText: String): Spanned? {
        var htmlText = "<b>$text <font color='red'>$requiredText</font></b>"
        return Html.fromHtml(htmlText)
    }

    inner class AddAddressLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return addAddress()
        }

        override fun onPostExecute(result: JSONObject?) {

            supportActionBar!!.title = result!!.optString("text_edit_address")
            aFnameLabel.text = getHTMLLabel(result!!.optString("entry_firstname"), "*")
            aLnameLabel.text = getHTMLLabel(result!!.optString("entry_lastname"), "*")
            aCompanyLabel.text = getHTMLLabel(result!!.optString("entry_company"), "")
            aAddress1Label.text = getHTMLLabel(result!!.optString("entry_address_1"), "*")
            aAddress2Label.text = getHTMLLabel(result!!.optString("entry_address_2"), "")
            aCityLabel.text = getHTMLLabel(result!!.optString("entry_city"), "*")
            aPostCodeLabel.text = getHTMLLabel(result!!.optString("entry_postcode"), "*")
            aCountryLabel.text = getHTMLLabel(result!!.optString("entry_country"), "*")
            aStateLabel.text = getHTMLLabel(result!!.optString("entry_zone"), "*")
            defaultAddressLable.text = getHTMLLabel(result!!.optString("entry_default"), "")

            val list = ArrayList<String>()
            var country: JSONArray = result.optJSONArray("countries")
            for (i in 0..country.length() - 1) {
                list.add(country.optJSONObject(i).optString("name"))
            }


            val dataAdapter = ArrayAdapter<String>(applicationContext,
                    android.R.layout.simple_spinner_item, list)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            aCountry.setAdapter(dataAdapter)


            aCountry.setOnItemClickListener { adapterView, view, i, l ->
                run {
                    val index = list.indexOf(aCountry.getText().toString())
                    selectedCountryId = index
                    var country_id = country.optJSONObject(index).optString("country_id")
                    AsyncTaskCompat.executeParallel(StateLoader(), country_id.toInt(), 0)
                }
            }
            super.onPostExecute(result)
        }
    }

    inner class InsertNewAddress : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject {
            return applicationContext.addNewAddress(
                    aFname.text.toString(),
                    aLname.text.toString(),
                    aCompany.text.toString(),
                    aAddress1.text.toString(),
                    aAddress2.text.toString(),
                    aCity.text.toString(),
                    aPostCode.text.toString(),
                    selectedCountryId - 1,
                    selectedStateId,
                    if (yes.isChecked) 1 else 0
            )
        }

        override fun onPostExecute(result: JSONObject?) {
            var code: Int = result!!.optInt("code")
            if (code == 200) {
                toast(result!!.optString("message"))
                finish()
            }
            Log.i("UpdateAddress", result.toString())
            super.onPostExecute(result)
        }
    }
}
