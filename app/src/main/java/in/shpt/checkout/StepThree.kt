package `in`.shpt.checkout

import `in`.shpt.R
import `in`.shpt.config.Config
import `in`.shpt.ext.shippingMethodStep
import `in`.shpt.ext.shippingMethodStepValidate
import `in`.shpt.widget.ProgressWheel
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.widget.AppCompatRadioButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.fcannizzaro.materialstepper.AbstractStep
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.isConnected
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by poovarasanv on 24/11/16.
 */

class StepThree : AbstractStep() {

    lateinit var heading: TextView
    lateinit var shippingMethodRadioGroup: RadioGroup
    lateinit var shippingMethodLayout: LinearLayout
    lateinit var fullLayout: ScrollView
    var selectedShippingMethod: Int = 0
    var CONSTANT: Int = 2500
    lateinit var progress : ProgressWheel
    lateinit var shippingMessageLabel: TextView
    lateinit var shippingMessageText: EditText

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.step3, container, false)
        heading = v.find(R.id.heading)
        shippingMethodLayout = v.find(R.id.shippingMethodLayout)
        shippingMessageText = v.find(R.id.shippingMessageText)
        shippingMessageLabel = v.find(R.id.shippingMessageLabel)
        progress = v.findViewById(R.id.progress) as ProgressWheel
        fullLayout = v.findViewById(R.id.fullLayout) as ScrollView

        return v
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
    }

    override fun name(): String? {
        return "Delivery Method"
    }

    override fun error(): String {
        return "Please Select Shipment Method"
    }

    fun loadShippingMethodStep() {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(ShippingMethodStep(), null)
        }
    }

    fun loadShippingMethodStepValidate(code: String, message: String) {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(ShippingMethodValidate(), code, message)
        }
    }


    inner class ShippingMethodValidate : AsyncTask<String, Void, JSONArray>() {
        override fun doInBackground(vararg p0: String?): JSONArray? {
            return context.shippingMethodStepValidate(p0[0] as String, p0[1] as String)
        }

        override fun onPostExecute(result: JSONArray?) {
            super.onPostExecute(result)
        }
    }

    inner class ShippingMethodStep : AsyncTask<Void, Void, JSONObject>() {
        override fun onPreExecute() {
            progress.visibility = View.VISIBLE
            fullLayout.visibility = View.GONE
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Void?): JSONObject {
            return context.shippingMethodStep()
        }

        override fun onPostExecute(result: JSONObject?) {

            heading.text = result!!.optString("text_shipping_method")
            shippingMessageLabel.text = result.optString("text_comments")

            shippingMethodLayout.removeAllViews()
            var shippingMethods: JSONObject = result.optJSONObject("shipping_methods")
            var keys: Iterator<String> = shippingMethods.keys()

            shippingMethodRadioGroup = RadioGroup(context)
            shippingMethodRadioGroup.orientation = LinearLayout.VERTICAL
            shippingMethodRadioGroup.id = R.id.shippingmethodselection

            var bundle = mStepper.extras
            var previousSelected = bundle.getString(Config.SHIPPINGMETHOD, "NULL")


            var i = 0
            while (keys.hasNext()) {


                val shipping = shippingMethods.optJSONObject(keys.next())


                var quoteDatas: JSONObject = shipping.optJSONObject("quote")
                var quoteKey: Iterator<String> = quoteDatas.keys()
                while (quoteKey.hasNext()) {
                    i++
                    var quoteData = quoteDatas.optJSONObject(quoteKey.next())

                    var themeItem: AppCompatRadioButton = AppCompatRadioButton(context)
                    themeItem.text = quoteData.optString("title") + "\n" + "Cost :" + quoteData.optString("text")
                    themeItem.id = CONSTANT + i
                    themeItem.typeface = Typeface.DEFAULT_BOLD
                    themeItem.tag = quoteData.optString("code")
                    themeItem.setPadding(20, 20, 20, 20)

                    if (previousSelected != "NULL" && quoteData.optString("code") == previousSelected) {
                        themeItem.isChecked = true
                    }

                    shippingMethodRadioGroup.addView(themeItem)
                }
            }
            shippingMethodLayout.addView(shippingMethodRadioGroup)
            progress.visibility = View.GONE
            fullLayout.visibility = View.VISIBLE
            super.onPostExecute(result)
        }
    }

    override fun onStepVisible() {
        loadShippingMethodStep()
        super.onStepVisible()
    }

    override fun nextIf(): Boolean {
        if (shippingMethodRadioGroup.checkedRadioButtonId != -1) {
            selectedShippingMethod = shippingMethodRadioGroup.checkedRadioButtonId.minus(CONSTANT)

            if (selectedShippingMethod > 0) {

                var bundle = mStepper.extras
                bundle.putString(Config.SHIPPINGMETHOD, shippingMethodRadioGroup.find<RadioButton>(shippingMethodRadioGroup.checkedRadioButtonId).getTag().toString())


                loadShippingMethodStepValidate(shippingMethodRadioGroup.find<RadioButton>(shippingMethodRadioGroup.checkedRadioButtonId).getTag().toString(), shippingMessageText.text.toString())
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
}
