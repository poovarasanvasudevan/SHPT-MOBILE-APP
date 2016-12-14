package `in`.shpt.service

import `in`.shpt.config.Config
import `in`.shpt.ext.log
import com.google.android.gms.gcm.GcmTaskService
import com.google.android.gms.gcm.TaskParams

/**
 * Created by poovarasanv on 8/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 8/12/16 at 5:15 PM
 */

class SchedulerServices : GcmTaskService() {
    override fun onRunTask(taskParams: TaskParams): Int {
        log("On Run Task")
        when (taskParams.tag) {
            Config.SINGLE_JOB -> {

            }

            Config.LOCATION_PEROIDIC_JOB -> {

            }
        }

        return 0
    }
}
