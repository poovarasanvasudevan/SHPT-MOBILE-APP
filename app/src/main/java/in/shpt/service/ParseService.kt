package `in`.shpt.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by poovarasanv on 28/11/16.
 */

class ParseService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

      //  Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
