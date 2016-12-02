package `in`.shpt.ext

import android.app.Activity
import com.parse.ParseAnalytics

/**
 * Created by poovarasanv on 1/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 1/12/16 at 2:01 PM
 */

fun init(act: Activity) {
    ParseAnalytics.trackAppOpenedInBackground(act.intent)
}