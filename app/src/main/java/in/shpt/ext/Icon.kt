package `in`.shpt.ext

import `in`.shpt.R
import `in`.shpt.config.Config
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable

/**
 * Created by poovarasanv on 14/11/16.
 */

fun Context.getIcon(icon: FontAwesome.Icon, color: Int = Color.WHITE, size: Int = IconicsDrawable.ANDROID_ACTIONBAR_ICON_SIZE_DP - 4): Drawable {

    return IconicsDrawable(this)
            .icon(icon)
            .color(color)
            .sizeDp(size)
}


fun Context.bitmap2drawable(bitmap: Bitmap): Drawable {
    return BitmapDrawable(this.resources, bitmap)
}

fun ImageView.loadUrl(url: String) {
    Glide.with(context)
            .load(url)
            .crossFade()
            .fitCenter()
            .placeholder(R.drawable.no_image)
            .error(R.drawable.no_image)
            .into(this)
}

fun ImageView.loadSHPTImage(product_id: String, size: String = "550") {

    var imageURL = Config.BASE + "/image/cache/data/${product_id}_FC-${size}x${size}.jpg"
    Glide.with(context)
            .load(imageURL)
            .crossFade()
            .fitCenter()
            .placeholder(R.drawable.no_image)
            .error(R.drawable.no_image)
            .into(this)
}