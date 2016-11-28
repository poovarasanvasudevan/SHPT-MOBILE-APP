package `in`.shpt.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.mcxiaoke.koi.ext.isConnected
import tgio.parselivequery.BaseQuery
import tgio.parselivequery.LiveQueryClient
import tgio.parselivequery.LiveQueryEvent

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
        if (LiveQueryClient.isConnected() == false && isConnected()) {
            LiveQueryClient.connect()
        }



        LiveQueryClient.on(LiveQueryEvent.CONNECTED, {
            Log.i("Connection", "Connected")
        })

        LiveQueryClient.on(LiveQueryEvent.SUBSCRIBED, {
            Log.i("Connection", "Subscribed")
        })

        val subscription = BaseQuery.Builder("Cart")
                .where("body", "poosan")
                .addField("body")
                .build()
                .subscribe()

        subscription.on(LiveQueryEvent.CREATE) {
            Log.i("Connection", it.toString())
        }

      //  Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
