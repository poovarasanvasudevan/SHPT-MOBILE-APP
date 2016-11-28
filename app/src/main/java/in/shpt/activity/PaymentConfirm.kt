package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.theme
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.startActivity
import kotlinx.android.synthetic.main.activity_payment_confirm.*

class PaymentConfirm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_payment_confirm)

        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        startActivity<Home>()
        finish()
        super.onBackPressed()
    }
}
