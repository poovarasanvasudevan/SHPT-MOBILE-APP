package `in`.shpt.app

import `in`.shpt.R
import `in`.shpt.config.Config
import `in`.shpt.ext.theme
import `in`.shpt.models.NotificationModel
import `in`.shpt.service.ParseService
import android.app.Application
import android.util.Log
import com.mcxiaoke.koi.KoiConfig
import com.mcxiaoke.koi.ext.startService
import com.parse.Parse
import com.parse.ParseInstallation
import com.parse.ParseObject


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
                .clientKey(resources.getString(R.string.parse_client_key))
                .enableLocalDataStore()
                .build()
        )

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE)

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseObject.registerSubclass(NotificationModel::class.java)


        startService<ParseService>()

        KoiConfig.logEnabled = true
        KoiConfig.logLevel = Log.VERBOSE
    }
}
