package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.ext.init
import `in`.shpt.ext.theme
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.startActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_login)
        init(this)
        loginButton.onClick {
            startActivity<LoginWebView>()
            finish()
        }
    }
}
