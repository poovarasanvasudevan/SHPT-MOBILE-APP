package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.activity.ProductDetail
import `in`.shpt.ext.extractLinks
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.log
import android.app.ProgressDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ScrollView
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.onLongClick
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.universalvideoview.UniversalMediaController
import com.universalvideoview.UniversalVideoView
import org.json.JSONObject




/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailDescriptionTab(var result: JSONObject) : Fragment(), UniversalVideoView.VideoViewCallback {
    override fun onPause(mediaPlayer: MediaPlayer?) {

    }

    override fun onStart(mediaPlayer: MediaPlayer?) {

    }

    override fun onScaleChange(isFullscreen: Boolean) {
        this.fullScreeen = isFullscreen
        if (isFullscreen) {
            val layoutParams = mVideoLayout.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            mVideoLayout.layoutParams = layoutParams
            descLayout.visibility = View.GONE
            (activity as ProductDetail).getAppBar().visibility = View.GONE
            (activity as ProductDetail).getFAB().visibility = View.GONE
        } else {
            val layoutParams = mVideoLayout.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = cacheHeight
            mVideoLayout.layoutParams = layoutParams
            descLayout.visibility = View.VISIBLE
            (activity as ProductDetail).getAppBar().visibility = View.VISIBLE
            (activity as ProductDetail).getFAB().visibility = View.VISIBLE
        }

    }

    override fun onBufferingStart(mediaPlayer: MediaPlayer?) {

    }

    override fun onBufferingEnd(mediaPlayer: MediaPlayer?) {

    }


    private val progressDialog: ProgressDialog? = null
    lateinit var video: UniversalVideoView
    lateinit var videoController: UniversalMediaController
    lateinit var descWeb: WebView
    lateinit var mVideoLayout: FrameLayout
    lateinit var descLayout: ScrollView
    var cacheHeight: Int = 0
    var fullScreeen = false
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.product_detail_tab_3, container, false)

        video = view!!.find<UniversalVideoView>(R.id.teaserVideo)
        descWeb = view.find<WebView>(R.id.descWeb)
        mVideoLayout = view.find<FrameLayout>(R.id.video_view_layout)
        descLayout = view.find<ScrollView>(R.id.descLayout)
        videoController = view.find<UniversalMediaController>(R.id.media_controller)
        video.setMediaController(videoController)
        video.setVideoViewCallback(this)
        cacheHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 205f, resources.displayMetrics).toInt();

        var txt = result.optString("description")
        if (txt.indexOf("Teaser") > 0) {
            txt = txt.substring(0, txt.indexOf("Teaser")).replace("Teaser", "")
        }

        descWeb.setWebChromeClient(WebChromeClient())
        descWeb.setWebViewClient(WebViewClient())
        descWeb.settings.javaScriptEnabled = true
        descWeb.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        val webViewContents = "<html><body style='text-align:justify;font-family: 'Times New Roman';font-style: normal !important; -webkit-user-select: none;>${txt.replace("href=", "_href=")}</body></html>"
        descWeb.loadData(webViewContents, "text/html; charset=utf-8", "UTF-8")

        descWeb.onLongClick {
            true
        }
        descWeb.isLongClickable = false
        descWeb.isHapticFeedbackEnabled = false
        context.getIcon(FontAwesome.Icon.faw_arrows_alt)

        val links = result.optString("description").extractLinks()


        for (i in 0..links.size - 1) {

            mVideoLayout.visibility = View.VISIBLE
            video.setVideoURI(Uri.parse(links[i]))

            context.log(links[i])
        }

        return view
    }

    override fun onPause() {

        super.onPause()
    }

}
