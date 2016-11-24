package `in`.shpt.checkout

import `in`.shpt.R
import `in`.shpt.ext.paymentAddressStep
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.widget.AppCompatRadioButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.github.fcannizzaro.materialstepper.AbstractStep
import com.mcxiaoke.koi.ext.isConnected
import org.json.JSONObject


/**
 * Created by poovarasanv on 24/11/16.
 */

class StepOne : AbstractStep() {

    lateinit var addressLayout: LinearLayout
    lateinit var paymentAddressRadioGroup: RadioGroup
    var CONSTANT: Int = 1688
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.step1, container, false)
        addressLayout = v.findViewById(R.id.addressLayout) as LinearLayout

        loadPaymentAddressSteps()
        return v
    }

    fun loadPaymentAddressSteps() {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(PaymentAddressStep(), null)
        }
    }

    inner class PaymentAddressStep : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return context.paymentAddressStep()
        }

        override fun onPostExecute(result: JSONObject?) {

            var address: JSONObject = result!!.optJSONObject("addresses")

            paymentAddressRadioGroup = RadioGroup(context)
            paymentAddressRadioGroup.orientation = LinearLayout.VERTICAL
            paymentAddressRadioGroup.id = R.id.paymentaddressselection

            var keys: Iterator<String> = address.keys()
            while (keys.hasNext()) {
                val addressValue = address.optJSONObject(keys.next())

                var themeItem: AppCompatRadioButton = AppCompatRadioButton(context)


                /*
                *
                * "firstname": "Poovarasan",
"lastname": "Vasudevan",
"company": "HTC",
"company_id": "",
"tax_id": "",
"address_1": "22/30 Arunachala Nagar  second street",
"address_2": "Gudiyatham",
"postcode": "632602",
"city": "Vellore",
"zone_id": "1503",
"zone": "Tamil Nadu",
"zone_code": "TN",
"country_id": "99",
"country": "India",
"iso_code_2": "IN",
"iso_code_3": "IND",
                * */
                themeItem.tag = addressValue.optString("address_id")
                themeItem.text =
                        addressValue.optString("firstname") + " " + addressValue.optString("lastname") + "\n" +
                                addressValue.optString("company") + "\n" +
                                addressValue.optString("address_1") + "\n" +
                                addressValue.optString("address_2") + "\n" +
                                addressValue.optString("city") + "\n" +
                                "PIN :" + addressValue.optString("postcode") + "\n" +
                                addressValue.optString("zone")
                themeItem.id = CONSTANT + addressValue.optString("address_id").toInt()
                paymentAddressRadioGroup.addView(themeItem)
            }

            addressLayout.addView(paymentAddressRadioGroup)

            super.onPostExecute(result)
        }
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
    }

    override fun name(): String? {
        return "Billing Detail"
    }

    override fun nextIf(): Boolean {
        return super.nextIf()
    }
}
