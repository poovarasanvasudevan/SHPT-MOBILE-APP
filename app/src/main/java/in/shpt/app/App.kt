package `in`.shpt.app

import `in`.shpt.R
import android.app.Application




/**
 * Created by poovarasanv on 14/11/16.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setTheme(R.style.BlueTheme)
    }
}
