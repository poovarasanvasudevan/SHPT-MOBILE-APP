package `in`.shpt.ext

import android.text.Html
import android.widget.EditText
import android.widget.TextView

/**
 * Created by poovarasanv on 23/11/16.
 */


fun TextView.loadMandatory(text: String, mandatory: String) {
    var htmlText = "<b>$text <font color='red'>$mandatory</font></b>"
    this.text = Html.fromHtml(htmlText)
}

fun EditText.isInvalid(): Boolean {
    if (this.text.isBlank() || this.text.isEmpty()) {
        return true
    }
    return false
}

fun EditText.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches();
}