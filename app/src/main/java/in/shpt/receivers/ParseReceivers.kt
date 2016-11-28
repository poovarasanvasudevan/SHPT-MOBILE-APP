package `in`.shpt.receivers

import `in`.shpt.service.ParseService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mcxiaoke.koi.ext.startService


/**
 * Created by poovarasanv on 28/11/16.
 */

class ParseReceivers : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.startService<ParseService>()




    }
}
