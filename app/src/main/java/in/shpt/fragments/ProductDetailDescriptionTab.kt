package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.ext.extractLinks
import `in`.shpt.ext.getIcon
import `in`.shpt.ext.log
import `in`.shpt.widget.Ripple
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.CardView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.easyvideoplayer.EasyVideoCallback
import com.afollestad.easyvideoplayer.EasyVideoPlayer
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.onLongClick
import com.mcxiaoke.koi.ext.onProgressChanged
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.view.IconicsImageView
import org.json.JSONObject


/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailDescriptionTab(var result: JSONObject) : Fragment(), EasyVideoCallback {
    override fun onPrepared(player: EasyVideoPlayer?) {
        seekbarvideo.max = player!!.duration
        durationOfTime.text = DateUtils.formatElapsedTime((player.currentPosition / 1000).toLong()) + " / " + DateUtils.formatElapsedTime((player.duration / 1000).toLong())
    }

    override fun onStarted(player: EasyVideoPlayer?) {

    }

    override fun onCompletion(player: EasyVideoPlayer?) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetry(player: EasyVideoPlayer?, source: Uri?) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubmit(player: EasyVideoPlayer?, source: Uri?) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBuffering(percent: Int) {

    }

    override fun onPreparing(player: EasyVideoPlayer?) {

    }

    override fun onError(player: EasyVideoPlayer?, e: Exception?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPaused(player: EasyVideoPlayer?) {

    }

    private val progressDialog: ProgressDialog? = null
    lateinit var video: EasyVideoPlayer
    lateinit var videoLayout: CardView
    lateinit var playpause: Ripple
    lateinit var fullScreenButton: Ripple
    lateinit var playpauseButtonIcon: IconicsImageView
    lateinit var fullScreenButtonIcon: IconicsImageView
    lateinit var seekbarvideo: AppCompatSeekBar
    lateinit var durationOfTime: TextView
    lateinit var descWeb: WebView
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.product_detail_tab_3, container, false)
        val descLayout = view!!.find<LinearLayout>(R.id.descLayout)
        videoLayout = view.find<CardView>(R.id.videoLayout)
        playpause = view.find<Ripple>(R.id.playpauseButton)
        fullScreenButton = view.find<Ripple>(R.id.fullScreenButton)
        playpauseButtonIcon = view.find<IconicsImageView>(R.id.playpauseButtonIcon)
        fullScreenButtonIcon = view.find<IconicsImageView>(R.id.fullScreenButtonIcon)

        video = view.find<EasyVideoPlayer>(R.id.teaserVideo)
        seekbarvideo = view.find<AppCompatSeekBar>(R.id.seekbarvideo)
        durationOfTime = view.find<TextView>(R.id.durationOfTime)
        descWeb = view.find<WebView>(R.id.descWeb)


        playpauseButtonIcon.icon = context.getIcon(FontAwesome.Icon.faw_play) as IconicsDrawable
        fullScreenButtonIcon.icon = context.getIcon(FontAwesome.Icon.faw_arrows_alt) as IconicsDrawable

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

            videoLayout.visibility = View.VISIBLE
            video.setCallback(this)
            video.setSource(Uri.parse(links[i]))
            seekbarvideo.max = video.duration
            video.setProgressCallback({ pos, tot ->
                run {
                    seekbarvideo.progress = video.currentPosition
                    durationOfTime.text = DateUtils.formatElapsedTime((video.currentPosition / 1000).toLong()) + " / " + DateUtils.formatElapsedTime((video.duration / 1000).toLong())
                }
            })

            context.log(links[i])
        }

        // video.setAutoFullscreen(true)
        playpause.onClick {
            if (video.isPlaying) {
                video.pause()
                playpauseButtonIcon.icon = context.getIcon(FontAwesome.Icon.faw_play) as IconicsDrawable
            } else {

                video.start()
                playpauseButtonIcon.icon = context.getIcon(FontAwesome.Icon.faw_pause) as IconicsDrawable
            }
        }


        seekbarvideo.onProgressChanged { seekBar, i, b ->
            run {
                if (b) {
                    video.seekTo(i)
                }
            }
        }



        return view
    }

    override fun onPause() {
        if (video.isPlaying) {
            video.pause()
        }
        super.onPause()
    }

}
