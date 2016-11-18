package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.config.Config
import `in`.shpt.pref.Prefs
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.log.logi
import kotlinx.android.synthetic.main.activity_login_web_view.*


class LoginWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_web_view)

        setSupportActionBar(toolbar)

        progressLoader.visibility = View.VISIBLE
        loginWebView.visibility = View.GONE
        loginWebView.settings.javaScriptEnabled = true
        loginWebView.setWebChromeClient(WebChromeClient())
        loginWebView.setWebViewClient(SHPTWebViewClient())
        loginWebView.loadUrl(Config.LOGIN_PAGE)
    }

    inner class SHPTWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            progressLoader.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {

            Log.d("LoginWebView", url)
            // logi() { url }
            if (url?.contains("account/edit")!!) {
                //No Phone Number go to edit page
                saveCookie(url)
                startActivity<ProfileUpdate>()
                finish()
            }

            if (url?.contains("common/home")!!) {
                //go to home page
                saveCookie(url)
                startActivity<Home>()
                finish()
            }

            progressLoader.visibility = View.GONE
            loginWebView.visibility = View.VISIBLE
            super.onPageFinished(view, url)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    fun saveCookie(url: String?) {
        val cookies = CookieManager.getInstance().getCookie(url)

        Log.i("Cookies", cookies)
        val temp = cookies.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

        for (ar1 in temp) {
            if (ar1.contains("PHPSESSID")) {
                val temp1 = ar1.split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val cookieValue = temp1[1]

                Prefs.with(this).write(Config.COOKIE, cookieValue.trim({ it <= ' ' }))

                logi { cookieValue.trim({ it <= ' ' }) }
            }
        }
    }
}
