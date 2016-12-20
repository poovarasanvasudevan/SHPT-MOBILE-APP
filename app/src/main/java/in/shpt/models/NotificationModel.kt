package `in`.shpt.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

/**
 * Created by poovarasanv on 16/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 16/12/16 at 12:11 PM
 */

@ParseClassName("NotificationMessages")
class NotificationModel : ParseObject() {

    var notificationType: Int
        get() = getInt("notification_type")
        set(value: Int) = put("notification_type", value)
    var messages: String
        get() = getString("messages")
        set(value) = put("messages", value)
    var icon: String
        get() = getString("icon")
        set(value) = put("icon", value)
    var createdDate: Date
        get() = getDate("created_date")
        set(value: Date) = put("created_date", value)
    var createdBy: ParseUser
        get() = getParseUser("created_by")
        set(value: ParseUser) = put("created_by", value)

}
