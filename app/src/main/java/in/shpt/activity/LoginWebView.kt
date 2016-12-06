package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.config.Config
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.init
import `in`.shpt.ext.theme
import `in`.shpt.pref.Prefs
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import com.mcxiaoke.koi.log.logi
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.activity_login_web_view.*


class LoginWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_login_web_view)
        init(this)
        setSupportActionBar(toolbar)

        if (isConnected()) {
            progressLoader.visibility = View.VISIBLE
            loginWebView.visibility = View.GONE
            loginWebView.settings.javaScriptEnabled = true
            loginWebView.setWebChromeClient(WebChromeClient())
            loginWebView.setWebViewClient(SHPTWebViewClient())
            loginWebView.loadUrl(Config.LOGIN_PAGE)
        } else {
            contentLayout.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
            emptyIcon.setImageDrawable(getIcon(FontAwesome.Icon.faw_frown_o, Color.GRAY, 120))
            emptyText.text = getString(`in`.shpt.R.string.no_internet)

            toast(R.string.no_internet)
        }
    }

    inner class SHPTWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            progressLoader.visibility = View.VISIBLE

            if (url?.contains("account/edit")!!) {
                loginWebView.visibility = View.GONE
            }

            if (url?.contains("common/home")!!) {
                loginWebView.visibility = View.GONE
            }
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {

            Log.d("LoginWebView", url)
            // logi() { url }
            if (url?.contains("account/edit")!!) {
                loginWebView.visibility = View.GONE
                //No Phone Number go to edit page
                saveCookie(url)
                startActivity<ProfileUpdate>()
                finish()
            }

            if (url?.contains("common/home")!!) {
                loginWebView.visibility = View.GONE
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
