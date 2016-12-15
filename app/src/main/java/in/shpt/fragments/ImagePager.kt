package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.ext.loadUrl
import `in`.shpt.widget.ImageZoom
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_pager.view.*


/**
 * Created by poovarasanv on 14/11/16.
 */

class ImagePager : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val message: String = arguments.getString(EXTRA_MESSAGE)
        val v = inflater!!.inflate(R.layout.image_pager, container, false)
        v.pager_image.loadUrl(message)

        ImageZoom.setViewZoomable(v.pager_image)
        return v;
    }


    companion object {
        val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        fun newInstance(message: String): ImagePager {
            val f = ImagePager()
            val bdl = Bundle(1)
            bdl.putString(EXTRA_MESSAGE, message)
            f.arguments = bdl
            return f
        }
    }
}
