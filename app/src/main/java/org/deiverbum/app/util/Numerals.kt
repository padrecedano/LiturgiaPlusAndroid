package org.deiverbum.app.util

/**
 * Convierte números enteros de numeral a ordinal.
 * Migrado a Kotlin desde el orinial de
 *
 * @author Leo Aso
 */
class Numerals
/**
 * Creates a new instance. Equivalent to
 * `new Numerals(true, true, true)`.
 */ @JvmOverloads constructor(
    private val useAnd: Boolean = true,
    private val useCommas: Boolean = true,
    private val useDashes: Boolean = true
) {

    /**
     * Converts the number to words in English.
     *
     * @param number  the number to convert.
     * @param ordinal if true, generates an ordinal (first, forty-third);
     * if false, generates a regular numeral (one, forty-two).
     * @return the generated numeral/ordinal.
     */

    fun toWords(number: Int, ordinal: Boolean = false): String {
        var newNumber = number
        if (newNumber == 0) return if (ordinal) "zeroth" else "zero"
        val sb = StringBuilder()
        for (i in HIGHER_BASES.indices) {
            val base = HIGHER_BASES[i]
            if (newNumber < base) continue
            sb.append(wordsBelow1000(newNumber / base, false))
            sb.append(' ').append(HIGHER_NAMES[i])
            newNumber %= base
            if (newNumber == 0) {
                if (ordinal) sb.append("th")
                return sb.toString()
            } else if (newNumber >= 100) {
                sb.append(if (useCommas) ", " else " ")
            } else {
                sb.append(if (useAnd) " and " else " ")
                break
            }
        }
        sb.append(wordsBelow1000(newNumber, ordinal))
        return sb.toString()
    }

    private fun wordsBelow1000(number: Int, useOrdinals: Boolean): String {
        var newNumber = number
        if (newNumber == 0) return ""
        val sb = StringBuilder()
        if (newNumber >= 100) {
            val h = newNumber / 100 % 10
            sb.append(NUMERALS_ES[h])
            sb.append(" hundred")
            newNumber %= 100
            if (newNumber == 0) {
                if (useOrdinals) sb.append("th")
                return sb.toString()
            }
            if (useAnd) sb.append(" and ") else sb.append(" ")
        }
        val words = if (useOrdinals) ORDINALS_ES else NUMERALS_ES
        if (newNumber < 20) return sb.append(words[newNumber]).toString()
        if (newNumber % 10 == 0) {
            val word = words[18 + newNumber / 10]
            return sb.append(word).toString()
        }
        sb.append(NUMERALS_ES[18 + newNumber / 10])
        sb.append(if (useDashes) '-' else ' ')
        sb.append(words[newNumber % 10])
        return sb.toString()
    }

    companion object {
        private val HIGHER_NAMES = arrayOf("billion", "million", "thousand")
        private val HIGHER_BASES = intArrayOf(1000000000, 1000000, 1000)

        private val ORDINALS_ES: List<String> = mutableListOf(
            "",
            "primera", "segunda", "tercera", "cuarta", "quinta", "sexta",
            "séptima", "octava", "novena", "décima", "undécima",
            "duodécima", "décimo tercera", "décimo cuarta", "décimo quinta",
            "décimo sexta", "décimo séptima", "décimo octava", "décimo novena",
            "vigésima", "trigésima", "cuadragésima", "quincuagésima",
            "sexagésima", "septuagésima", "octogésima", "nonagésima"
        )

        private val NUMERALS_ES: List<String> = mutableListOf(
            "",
            "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen",
            "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen", "vigésimo", "trigésimo", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"
        )
    }
}