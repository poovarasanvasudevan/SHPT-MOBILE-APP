package `in`.shpt.ext

import android.text.Html
import android.text.Spanned
import com.mcxiaoke.koi.utils.currentVersion

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