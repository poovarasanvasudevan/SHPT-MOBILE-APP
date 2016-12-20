package `in`.shpt.ext

import android.text.Html
import android.text.Spanned
import android.util.Log
import android.util.Patterns
import com.mcxiaoke.koi.utils.currentVersion
import java.util.*


/**
 * Created by poovarasanv on 15/11/16.
 */

fun String.toCamelCase(): String? {
    if (this == null)
        return null

    val ret = StringBuilder(this.length)

    for (word in this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
        if (!word.isEmpty()) {
            ret.append(word.substring(0, 1).toUpperCase())
            ret.append(word.substring(1).toLowerCase())
        }
        if (ret.length != this.length)
            ret.append(" ")
    }

    return ret.toString()
}

fun String.extractLinks(): Array<String> {
    val links = ArrayList<String>()
    val m = Patterns.WEB_URL.matcher(this)
    while (m.find()) {
        val url = m.group()

        Log.i("Videop URL",url)
        if (url.startsWith("https") && url.endsWith(".webm"))
            links.add(url)
    }

    return links.toTypedArray()
}

private fun String.removeHtml(): String {
    var html = this
    html = html.replace("<(.*?)\\>".toRegex(), " ")
    html = html.replace("<(.*?)\\\n".toRegex(), " ")
    html = html.replaceFirst("(.*?)\\>".toRegex(), " ")
    html = html.replace("&nbsp;".toRegex(), " ")
    html = html.replace("&amp;".toRegex(), " ")
    return html
}

fun String.limitsTo(characters: Int): String? {
    if (this.isBlank() || this.isEmpty()) {
        return this
    }
    if (this.length < characters - 3) {
        return this + "..."
    } else {
        return this.substring(0, characters - 3) + "..."
    }
}

fun String.currencyFormat(): String? {
    if (this.isBlank() || this.isEmpty()) {
        return this
    }

    var splitStr = this.split(".")
    return "₹ " + splitStr[0] + "." + splitStr[1].substring(0, 2)
}

fun String.removehtml(): Spanned? {
    if (currentVersion() >= android.os.Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY);
    } else {
        return Html.fromHtml(this);
    }
}

fun String.addressValidate(): Boolean {
    if (this.isEmpty() || this.isBlank()) {
        return true;
    }

    return false
}

fun getRequiredError(text: String): String {
    return text + " is Required"
}