package `in`.shpt.ext

import `in`.shpt.config.Config
import `in`.shpt.pref.Prefs
import android.content.Context

/**
 * Created by poovarasanv on 22/11/16.
 */


fun Context.redTheme() {
    this.setTheme(`in`.shpt.R.style.AppTheme)
}

fun Context.blueTheme() {
    this.setTheme(`in`.shpt.R.style.BlueTheme)
}

fun Context.greenTheme() {
    this.setTheme(`in`.shpt.R.style.GreenTheme)
}


fun Context.tealTheme() {
    this.setTheme(`in`.shpt.R.style.TealTheme)
}


fun Context.yellowTheme() {
    this.setTheme(`in`.shpt.R.style.YellowTheme)
}

fun Context.orangeTheme() {
    this.setTheme(`in`.shpt.R.style.OrangeTheme)
}

fun Context.indigoTheme() {
    this.setTheme(`in`.shpt.R.style.IndigoTheme)
}

fun Context.theme() {
    if (Prefs.with(this).contains(Config.THEME)) {
        when (Prefs.with(this).readInt(Config.THEME)) {
            1 -> blueTheme()
            2 -> redTheme()
            3 -> greenTheme()
            4 -> tealTheme()
            5 -> yellowTheme()
            6 -> orangeTheme()
            7 -> indigoTheme()
            else -> redTheme()
        }
    } else {
        blueTheme()
    }
}