package `in`.shpt.receivers

import `in`.shpt.event.ConnectionEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.toast
import org.greenrobot.eventbus.EventBus

/**
 * Created by poovarasanv on 28/11/16.
 */

class ConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // if(context.isConnected())
        if (context.isConnected()) {
            context.toast("Connected to Internet")
            EventBus.getDefault().post(ConnectionEvent(true))
        } else {
            context.toast("No Internet Connection")
            EventBus.getDefault().post(ConnectionEvent(false))
        }
    }
}
