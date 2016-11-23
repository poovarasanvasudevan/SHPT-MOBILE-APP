package `in`.shpt.ext

import android.text.Html
import android.widget.TextView

/**
 * Created by poovarasanv on 23/11/16.
 */


fun TextView.loadMandatory(text: String, mandatory: String) {
    var htmlText = "<b>$text <font color='red'>$mandatory</font></b>"
    this.text = Html.fromHtml(htmlText)
}