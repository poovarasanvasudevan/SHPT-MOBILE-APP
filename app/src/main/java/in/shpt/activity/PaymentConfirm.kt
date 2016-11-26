package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.theme
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.startActivity
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_payment_confirm.*

class PaymentConfirm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_payment_confirm)

        setSupportActionBar(toolbar)
        confirmImage.setImageDrawable(getIcon(FontAwesome.Icon.faw_check_circle_o, R.color.md_green_400, 120))
    }

    override fun onBackPressed() {
        startActivity<Home>()
        finish()
        super.onBackPressed()
    }
}
