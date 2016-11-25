package `in`.shpt.checkout

import `in`.shpt.R
import `in`.shpt.activity.EditAddress
import `in`.shpt.config.Config
import `in`.shpt.ext.shippingAddressStep
import `in`.shpt.ext.shippingAddressStepValidate
import `in`.shpt.widget.Ripple
import android.content.Intent
import android.graphics.Typeface
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
import com.mcxiaoke.koi.ext.onClick
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by poovarasanv on 24/11/16.
 */

class StepTwo : AbstractStep() {
    lateinit var addressLayout: LinearLayout
    lateinit var newAddress: Ripple
    lateinit var paymentAddressRadioGroup: RadioGroup
    var CONSTANT: Int = 1788
    var selectedPaymentAddress: Int = 0
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.step2, container, false)

        addressLayout = v.findViewById(R.id.addressLayout) as LinearLayout
        newAddress = v.findViewById(R.id.newAddress) as Ripple

        newAddress.onClick {
            var editAddressIntent: Intent = Intent(context, EditAddress::class.java)
            editAddressIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(editAddressIntent)
        }
        return v
    }


    override fun onStepVisible() {

        loadShippingAddressStep()
        super.onStepVisible()
    }

    fun loadShippingAddressStep() {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(DeliveryAddressDetail(), null)
        }
    }

    fun loadShippingAddressStepsValidate(address_id: String) {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(DeliveryAddressStepValidate(), address_id)
        }
    }


    inner class DeliveryAddressStepValidate : AsyncTask<String, Void, JSONArray>() {
        override fun doInBackground(vararg p0: String?): JSONArray {
            return context.shippingAddressStepValidate("existing", p0[0] as String)
        }

        override fun onPostExecute(result: JSONArray?) {
            super.onPostExecute(result)
        }
    }

    inner class DeliveryAddressDetail : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return context.shippingAddressStep()
        }

        override fun onPostExecute(result: JSONObject?) {

            addressLayout.removeAllViews()
            var address: JSONObject = result!!.optJSONObject("addresses")

            paymentAddressRadioGroup = RadioGroup(context)
            paymentAddressRadioGroup.orientation = LinearLayout.VERTICAL
            paymentAddressRadioGroup.id = R.id.paymentaddressselection

            var bundle = mStepper.extras
            var previousSelection = bundle.getInt(Config.PAYMENTADDRESSID, 0)

            var keys: Iterator<String> = address.keys()
            while (keys.hasNext()) {
                val addressValue = address.optJSONObject(keys.next())

                if (addressValue.optString("country_id") == Config.DEFAULT_COUNTRY) {

                    var vDivider: View = View(context)
                    vDivider.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
                    vDivider.setBackgroundColor(resources.getColor(R.color.md_green_500))
                    paymentAddressRadioGroup.addView(vDivider)

                    var themeItem: AppCompatRadioButton = AppCompatRadioButton(context)

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
                    themeItem.typeface = Typeface.DEFAULT_BOLD
                    if (previousSelection > 0 && previousSelection == addressValue.optString("address_id").toInt()) {
                        themeItem.isChecked = true
                    }
                    themeItem.setPadding(20, 20, 20, 20)
                    paymentAddressRadioGroup.addView(themeItem)


                }
            }

            addressLayout.addView(paymentAddressRadioGroup)

            super.onPostExecute(result)
        }
    }

    override fun onStart() {

        super.onStart()
    }

    override fun error(): String {
        return "Please Select Delivery Address"
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
    }

    override fun name(): String? {
        return "Delivery Details"
    }

    override fun nextIf(): Boolean {
        if (paymentAddressRadioGroup.checkedRadioButtonId != -1) {
            selectedPaymentAddress = paymentAddressRadioGroup.checkedRadioButtonId.minus(CONSTANT)

            if (selectedPaymentAddress > 0) {

                var bundle = mStepper.extras
                bundle.putInt(Config.PAYMENTADDRESSID, selectedPaymentAddress)

                loadShippingAddressStepsValidate(selectedPaymentAddress.toString())
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
}
