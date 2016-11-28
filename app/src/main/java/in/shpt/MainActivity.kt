package `in`.shpt

import `in`.shpt.activity.LoginActivity
import `in`.shpt.activity.LoginWebView
import `in`.shpt.config.Config
import `in`.shpt.ext.theme
import `in`.shpt.pref.Prefs
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_main)

        next()
    }

    fun next() {
        if (isConnected()) {
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
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No Internet")
            builder.setMessage("No Internet Connection,Are You want to retry")
            builder.setPositiveButton("OK", { dialogInterface, i ->
                run {
                    next()
                }
            })
            builder.setNegativeButton("Cancel", { dialogInterface, i ->
                run {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            })
            builder.show()
        }
    }
}
