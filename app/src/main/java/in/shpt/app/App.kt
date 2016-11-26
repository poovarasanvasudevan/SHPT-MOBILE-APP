package `in`.shpt.app

import `in`.shpt.ext.theme
import android.app.Application
import android.util.Log
import com.mcxiaoke.koi.KoiConfig


/**
 * Created by poovarasanv on 14/11/16.
 */

class App : Application() {

    override fun onCreate() {
        theme()
        super.onCreate()

        //setTheme(R.style.BlueTheme)
        KoiConfig.logEnabled = true //default is false

        KoiConfig.logLevel = Log.VERBOSE
    }
}
