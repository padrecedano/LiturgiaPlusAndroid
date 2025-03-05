package org.deiverbum.app.util

/**
 * Extensiones para el manejo de cadenas.
 *
 * @since 2025.1
 *
 */


/**
 * @param charactersToRemove The characters to remove from the receiving String.
 * @return A copy of the receiving String with the characters in `charactersToRemove` removed.
 *
 * @since 2025.1
 *
 */
fun String.removeAll(charactersToRemove: Set<Char>): String {
    return filterNot { charactersToRemove.contains(it) }
}

fun String.replaceChars(replacement: Map<Char, Char>) =
    map { replacement.getOrDefault(it, it) }.joinToString("")

fun String.splitParts(separator: String) =
    split(separator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
