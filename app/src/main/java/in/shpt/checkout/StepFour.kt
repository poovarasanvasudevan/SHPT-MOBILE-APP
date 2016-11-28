package `in`.shpt.checkout

import `in`.shpt.R
import `in`.shpt.config.Config
import `in`.shpt.ext.paymentMethodStep
import `in`.shpt.ext.paymentMethodStepValidate
import `in`.shpt.widget.ProgressWheel
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.os.AsyncTaskCompat
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatRadioButton
import android.text.Html
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

class StepFour : AbstractStep() {

    lateinit var heading: TextView
    lateinit var paymentMessageLabel: TextView
    lateinit var paymentMessageText: EditText
    lateinit var cardImage: ImageView
    lateinit var agree: AppCompatCheckBox
    lateinit var fullLayout: ScrollView
    lateinit var progress : ProgressWheel
    lateinit var paymentMethodRadioGroup: RadioGroup
    lateinit var paymentMethodLayout: LinearLayout
    var selectedpaymentMethod: Int = 0
    var CONSTANT: Int = 1996

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.step4, container, false)

        paymentMessageText = v.find(R.id.paymentMessageText)
        heading = v.find(R.id.heading)
        paymentMessageLabel = v.find(R.id.paymentMessageLabel)
        paymentMethodLayout = v.find(R.id.paymentMethodLayout)
        cardImage = v.find(R.id.cardImage)
        agree = v.find(R.id.agree)

        progress = v.findViewById(R.id.progress) as ProgressWheel
        fullLayout = v.findViewById(R.id.fullLayout) as ScrollView
        cardImage.setImageDrawable(resources.getDrawable(R.drawable.payment))
        return v
    }

    fun loadPaymentSteps() {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(PaymentMethodStep(), null)
        }
    }

    fun loadPaymentStepsValidate(payment_method: String, comments: String, agree: String) {
        if (context.isConnected()) {
            AsyncTaskCompat.executeParallel(PaymentMethodStepValidate(), payment_method, comments, agree)
        }
    }

    inner class PaymentMethodStepValidate : AsyncTask<String, Void, JSONArray>() {
        override fun doInBackground(vararg p0: String?): JSONArray {
            return context.paymentMethodStepValidate(p0[0] as String, p0[1] as String, p0[2] as String)
        }

        override fun onPostExecute(result: JSONArray?) {
            super.onPostExecute(result)
        }
    }

    inner class PaymentMethodStep : AsyncTask<Void, Void, JSONObject>() {

        override fun onPreExecute() {
            progress.visibility = View.VISIBLE
            fullLayout.visibility = View.GONE
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Void?): JSONObject? {
            return context.paymentMethodStep()
        }

        override fun onPostExecute(result: JSONObject?) {

            heading.text = result!!.optString("text_payment_method")
            paymentMessageLabel.text = result.optString("text_comments")
            agree.text = Html.fromHtml(result.optString("text_agree"))
            paymentMethodLayout.removeAllViews()

            var paymentMethods: JSONObject = result.optJSONObject("payment_methods")
            var keys: Iterator<String> = paymentMethods.keys()

            paymentMethodRadioGroup = RadioGroup(context)
            paymentMethodRadioGroup.orientation = LinearLayout.VERTICAL
            paymentMethodRadioGroup.id = R.id.paymentmethodselection

            var i = 0

            var bundle = mStepper.extras
            var previousSelected = bundle.getString(Config.PAYMENTMETHOD, "NULL")


            while (keys.hasNext()) {
                i++
                val payment = paymentMethods.optJSONObject(keys.next())

                var id = CONSTANT + i
                var themeItem: AppCompatRadioButton = AppCompatRadioButton(context)
                themeItem.text = payment.optString("title")
                themeItem.id = id
                themeItem.tag = payment.optString("code")
                themeItem.typeface = Typeface.DEFAULT_BOLD
                themeItem.setPadding(20, 20, 20, 20)
                if (previousSelected != "NULL" && payment.optString("code") == previousSelected) {
                    themeItem.isChecked = true
                }

                paymentMethodRadioGroup.addView(themeItem)
            }

            paymentMethodLayout.addView(paymentMethodRadioGroup)
            progress.visibility = View.GONE
            fullLayout.visibility = View.VISIBLE
            super.onPostExecute(result)
        }
    }


    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
    }

    override fun name(): String? {
        return "Payment Method"
    }

    override fun error(): String {
        var message: String = ""
        if (agree.isChecked == false) {
            agree.error = "Please Accept the terms and condition"
            message = "Please Accept the terms and condition"
        }

        if (selectedpaymentMethod < 0) {
            message = "Please Select the Payment Method"
        }
        return message
    }


    override fun onStepVisible() {
        loadPaymentSteps()
        super.onStepVisible()
    }

    override fun nextIf(): Boolean {
        if (paymentMethodRadioGroup.checkedRadioButtonId != -1) {
            selectedpaymentMethod = paymentMethodRadioGroup.checkedRadioButtonId.minus(CONSTANT)

            if (selectedpaymentMethod > 0 && agree.isChecked) {
                var bundle = mStepper.extras
                bundle.putSerializable(Config.PAYMENTMETHOD, paymentMethodRadioGroup.find<RadioButton>(paymentMethodRadioGroup.checkedRadioButtonId).tag.toString())

                loadPaymentStepsValidate(paymentMethodRadioGroup.find<RadioButton>(paymentMethodRadioGroup.checkedRadioButtonId).tag.toString(), paymentMessageText.text.toString(), if (agree.isChecked) "1" else "0")
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
}
