package `in`.shpt.app

import `in`.shpt.config.Config
import `in`.shpt.ext.theme
import `in`.shpt.service.ParseService
import android.app.Application
import android.util.Log
import com.mcxiaoke.koi.KoiConfig
import com.mcxiaoke.koi.ext.startService
import com.parse.Parse
import tgio.parselivequery.LiveQueryClient


/**
 * Created by poovarasanv on 14/11/16.
 */

class App : Application() {


    override fun onCreate() {
        theme()
        super.onCreate()

        Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId(Config.MY_APP_ID)
                .server(Config.SERVER)
                .clientKey(Config.CLIENT_KEY)
                .enableLocalDataStore()
                .build()
        )

        LiveQueryClient.init(Config.WS_URL, Config.MY_APP_ID, true)

        startService<ParseService>()

        KoiConfig.logEnabled = true
        KoiConfig.logLevel = Log.VERBOSE
    }
}
