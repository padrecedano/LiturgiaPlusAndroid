package org.deiverbum.app.util

import org.deiverbum.app.util.Constants.BR
import org.deiverbum.app.util.Constants.BRS
import org.deiverbum.app.util.Constants.NBSP_4
import org.deiverbum.app.util.Constants.NBSP_SALMOS

/**
 * Extensiones para el manejo de cadenas.
 *
 * @since 2025.1
 *
 */


fun String.replaceChars(replacement: Map<Char, Char>) =
    map { replacement.getOrDefault(it, it) }.joinToString("")

fun String.splitParts(separator: String) =
    split(separator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

fun String.firstUpper(): String {
    val regex = Regex("^[^A-Za-z]*")
    val length = regex.find(this)!!.value.length
    val head = this.take(length)
    val tail = this.drop(length)
    return head + tail.replaceFirstChar { it.uppercase() }
}

fun String.marksAndHtml(): String {
    return this
        .replace("_", NBSP_SALMOS)
        .replace("§", BRS)
        .replace("~", BR)
        .replace("¦", NBSP_4)
        .replace("⊣", BR + NBSP_4)
        .replace("≀", BR + NBSP_4 + NBSP_4)
        .replace("~", BR)
        .replace("§", BRS)
        .replace("∸", BRS)
        .replace("≡", "")
        .replace("</p>", BRS, true)
        .replace("<p>", "", true)
}