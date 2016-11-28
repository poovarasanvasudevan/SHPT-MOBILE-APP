package `in`.shpt.activity

import `in`.shpt.R
import `in`.shpt.config.Config
import `in`.shpt.ext.theme
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import kotlinx.android.synthetic.main.activity_payment_web.*

class PaymentWeb : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme()
        setContentView(R.layout.activity_payment_web)

        setSupportActionBar(toolbar)
        // payment_web
        payment_web.settings.javaScriptEnabled = true
        payment_web.setWebChromeClient(WebChromeClient())
        payment_web.setWebViewClient(PaymentWebClient())
        payment_web.loadUrl(Config.PAYMENTURL)
    }

    inner class PaymentWebClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            progress.visibility = View.VISIBLE
            view!!.visibility = View.GONE

            if (url!!.contains(Config.PAYMENT_CONFIRM) || url.contains(Config.INSTAMOJO)) {
                view!!.visibility = View.GONE
            } else {
                view!!.visibility = View.VISIBLE
            }
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {

            if (url!!.contains(Config.INSTAMOJO)) {
                view!!.visibility = View.GONE
            } else {
                view!!.visibility = View.VISIBLE
            }

            if (url!!.contains(Config.PAYMENT_CONFIRM)) {
                view!!.visibility = View.GONE
                toast("payment confirmed")
                startActivity<PaymentConfirm>()
                finish()
            }


            progress.visibility = View.GONE


            super.onPageFinished(view, url)
        }
    }
}
