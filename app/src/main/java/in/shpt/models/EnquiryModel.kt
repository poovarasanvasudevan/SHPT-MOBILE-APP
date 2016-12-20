package `in`.shpt.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

/**
 * Created by poovarasanv on 16/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 16/12/16 at 1:55 PM
 */

@ParseClassName("Enquiry")
class EnquiryModel : ParseObject() {
    var user: ParseUser
        get() = getParseUser("user")
        set(value) = put("user", value)

    var created: Long
        get() = getLong("created_date")
        set(value) = put("created_date", value)

    var query: String
        get() = getString("query")
        set(value: String) = put("query", value)

    var status: Int
        get() = getInt("status")
        set(value: Int) = put("status", value)
}
