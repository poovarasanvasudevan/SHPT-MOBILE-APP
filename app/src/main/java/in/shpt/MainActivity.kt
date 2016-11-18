package `in`.shpt

import `in`.shpt.activity.LoginActivity
import `in`.shpt.activity.LoginWebView
import `in`.shpt.config.Config
import `in`.shpt.pref.Prefs
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        if (Prefs.with(this).contains(Config.COOKIE)) {

            Handler().postDelayed(Runnable {
                startActivity<LoginWebView>()
                finish()
            }, 2000)
        } else {

            Handler().postDelayed(Runnable {
                startActivity<LoginActivity>()
                finish()
            }, 2000)
        }
    }
}
