package `in`.shpt.app

import `in`.shpt.R
import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig




/**
 * Created by poovarasanv on 14/11/16.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()


        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/myfont.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}
