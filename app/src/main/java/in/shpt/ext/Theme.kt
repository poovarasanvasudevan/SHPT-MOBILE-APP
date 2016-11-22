package `in`.shpt.ext

import `in`.shpt.config.Config
import `in`.shpt.pref.Prefs
import android.content.Context
import com.mcxiaoke.koi.ext.isConnected

/**
 * Created by poovarasanv on 22/11/16.
 */
object Theme {
    var BLUE = 1
    var RED = 2
    var GREEN = 3
    var TEAL = 4
    var YELLOW = 5
    var ORANGE = 6
    var INDIGO = 7
}

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
    if (isConnected()) {
        when (Prefs.with(this).readInt(Config.THEME, Config.DEFAULT_THEME)) {
            Theme.BLUE -> blueTheme()
            Theme.RED -> redTheme()
            Theme.GREEN -> greenTheme()
            Theme.TEAL -> tealTheme()
            Theme.YELLOW -> yellowTheme()
            Theme.ORANGE -> orangeTheme()
            Theme.INDIGO -> indigoTheme()
            else -> redTheme()
        }
    } else {
        redTheme()
    }
}