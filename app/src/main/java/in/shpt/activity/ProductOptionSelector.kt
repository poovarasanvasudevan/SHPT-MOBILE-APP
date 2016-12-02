package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.event.OptionKeyModel
import `in`.shpt.ext.init
import `in`.shpt.ext.loadMandatory
import `in`.shpt.ext.loadUrl
import `in`.shpt.ext.theme
import `in`.shpt.widget.StyledSpinner
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.toast
import kotlinx.android.synthetic.main.activity_product_option_selector.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class ProductOptionSelector : AppCompatActivity() {

    lateinit var fullProductJSON: JSONObject

    val Constant: Int = 1098
    var error: Boolean = false

    lateinit var validateModels: ArrayList<OptionKeyModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        theme()
        setContentView(R.layout.activity_product_option_selector)
        init(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        validateModels = arrayListOf()
        fullProductJSON = JSONObject(intent.getStringExtra("FULLPRODUCT"))
        var options: JSONArray = fullProductJSON.optJSONArray("options")


        productImage.loadUrl(fullProductJSON.optString("popup"))
        productName.text = fullProductJSON.optString("heading_title")
        productPrice.text = fullProductJSON.optString("price")


        addToCartWithOption.onClick {
            error = false
            //toast("Size ${validateModels.size}")
            for (i in 0..validateModels.size - 1) {

                Log.i("Resss", validateModels[i].isRequired.toString())

                if (validateModels[i].isRequired == true) {
                    if (validateModels[i].isEntered == false) {
                        toast(validateModels[i].componentDisplayName + " is Required")
                        error = true
                    }
                }
            }

            if (error == false) {
                toast("Form success")
            }
        }

        for (i in 0..options.length() - 1) {


            var optionKeyModel: OptionKeyModel = OptionKeyModel()

            var optionItemLayout = LinearLayout(this)
            optionItemLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            optionItemLayout.orientation = LinearLayout.VERTICAL


            var labelText = TextView(this)
            labelText.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            labelText.loadMandatory(options.optJSONObject(i).optString("name"), if (options.optJSONObject(i).optString("required") == "1") "*" else "")
            optionItemLayout.addView(labelText)

            optionKeyModel.componentDisplayName = options.optJSONObject(i).optString("name")
            if (options.optJSONObject(i).optString("required") == "1") {
                optionKeyModel.isRequired = true
            }

            optionKeyModel.isEntered = false
            optionKeyModel.componentId = Constant + i;
            optionKeyModel.optionId = options.optJSONObject(i).optString("product_option_id")

            when (options.optJSONObject(i).optString("type")) {
                "select" -> {

                    var selectOptions: JSONArray = options.optJSONObject(i).optJSONArray("option_value")
                    var optionArray: ArrayList<String> = arrayListOf()
                    optionArray.add(fullProductJSON.optString("text_select"))
                    for (j in 0..selectOptions.length() - 1) {
                        optionArray.add(selectOptions.optJSONObject(j).optString("name"))
                    }
                    val spinner = StyledSpinner(this)

                    val spinnerArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionArray) //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = spinnerArrayAdapter
                    spinner.background = ContextCompat.getDrawable(applicationContext, R.drawable.spinner)
                    spinner.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120)
                    spinner.id = Constant + i;
                    spinner.onItemSelectedListener = object : OnItemSelectedListener {
                        override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                            if (position > 0) {
                                var ItemPosition = position - 1
                                if (selectOptions.optJSONObject(ItemPosition).optString("image") != null) {
                                    productImage.loadUrl(selectOptions.optJSONObject(ItemPosition).optString("image"))
                                } else {
                                    productImage.loadUrl(fullProductJSON.optString("popup"))
                                }

                                if (selectOptions.optJSONObject(ItemPosition).opt("price") is Boolean) {

                                } else {
                                    productPrice.text = selectOptions.optJSONObject(ItemPosition).optString("price")
                                }

                                validateModels[i].isEntered = true
                                validateModels[i].selectedValue = selectOptions.optJSONObject(ItemPosition).optString("product_option_value_id")
                            } else {
                                validateModels[i].selectedValue = ""
                                validateModels[i].isEntered = false
                            }
                        }

                        override fun onNothingSelected(parentView: AdapterView<*>) {
                            // your code here
                            productImage.loadUrl(fullProductJSON.optString("popup"))
                            productName.text = fullProductJSON.optString("heading_title")
                            productPrice.text = fullProductJSON.optString("price")

                            validateModels[i].isEntered = false
                        }

                    }

                    optionItemLayout.addView(spinner)
                    validateModels.add(optionKeyModel)
                }
            }

            optionItemLayout.setPadding(10, 10, 10, 10)
            optionLayout.addView(optionItemLayout)
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
