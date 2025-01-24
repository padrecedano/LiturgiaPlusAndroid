package org.deiverbum.app.util

import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.CharacterStyle
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import org.deiverbum.app.util.ColorUtils.red
import org.deiverbum.app.util.ColorUtils.rubricColor
import org.deiverbum.app.util.Constants.BR
import org.deiverbum.app.util.Constants.BRS
import org.deiverbum.app.util.Constants.CSS_RED_A
import org.deiverbum.app.util.Constants.CSS_RED_Z
import org.deiverbum.app.util.Constants.ERR_REPORT
import org.deiverbum.app.util.Constants.NBSP_4
import org.deiverbum.app.util.Constants.NBSP_SALMOS
import org.deiverbum.app.util.Constants.NEWBIS
import org.deiverbum.app.util.Constants.NEWONE
import org.deiverbum.app.util.Constants.OBIEN
import org.deiverbum.app.util.Constants.PRECES_IL
import org.deiverbum.app.util.Constants.PRECES_R
import org.deiverbum.app.util.Constants.TABBIS
import org.deiverbum.app.util.Constants.TABTRI
import org.deiverbum.app.util.Constants.VERSION_CODE_FORMATTED
import java.sql.Timestamp
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Objects


/**
 * Clase utilitaria que se usa en varias partes de la aplicación
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2018.1
 */
//@SuppressWarnings("all")
//@ExperimentalStdlibApi
object Utils {
    val LS: String? = System.lineSeparator()
    val LS2 = LS + LS
    private const val H1 = 2.2f
    private const val H2 = 1.7f
    private const val H3 = 1.4f
    private const val H4 = 1.1f

    /**
     * Formatea el texto litúrgico según la convención de marcado.
     * Método adaptado para Jetpack Compose.
     *
     * @since 2024.1
     */

    //@RequiresApi(Build.VERSION_CODES.O)
    fun transformTextHtml(text: String, rubricColor: Color): AnnotatedString {
        return buildAnnotatedString {
            text.forEach { c ->
                when (c) {
                    '℣', '℟' -> withStyle(SpanStyle(color = rubricColor)) { append(c) }
                    '¦' -> append("\t")
                    '≀' -> append("\n\t\t")
                    '§' -> append("\n\n")
                    else -> append(c)
                }
            }
        }
    }

    fun transformTextHtmll(text: String, rubricColor: Color): AnnotatedString {
        var tmp = AnnotatedString.fromHtml(getFormato(text, Color.Red))
        //tmp= replaceHtmlTags(tmp)
        var tmq = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)//.toAnnotatedString()
        val ssb = SpannableStringBuilder()

        return tmp
        return buildAnnotatedString {
            tmp.forEach { c ->
                when (c) {
                    '℣', '℟' -> withStyle(SpanStyle(color = rubricColor)) { append(c) }
                    '¦' -> append("\t")
                    '≀' -> append("\n\t\t")
                    '§' -> append("\n\n")
                    else -> append(c)
                }
            }
        }
    }

    /**
     * Formatea el texto litúrgico según la convención de marcado.
     * Método adaptado para Jetpack Compose.
     *
     * @since 2024.1
     */

    fun transformText(text: String, rubricColor: Color) = buildAnnotatedString {
        text.forEach { c ->
            when (c) {
                '℣', '℟' -> withStyle(SpanStyle(color = rubricColor)) { append(c) }
                '¦' -> append("\t")
                '≀' -> append("\n\t\t")
                '_' -> append("\n\t\t")

                '§' -> append("\n\n")
                else -> append(c)
            }
        }
    }

    /**
     * Formatea el texto litúrgico según la convención de marcado.
     * Método adaptado para Jetpack Compose.
     *
     * @since 2025.1
     */

    fun transformBodyText(text: String, rubricColor: Color) = buildAnnotatedString {
        text.forEach { c ->
            when (c) {
                '¦' -> append("\t")
                '≀' -> append("\n\t\t")
                '§' -> append("\n\n")
                '~' -> append("\n")
                '⊣' -> append("\n\t\t")
                '≠' -> {
                    append("\n\t\t")
                    withStyle(SpanStyle(color = rubricColor)) { append(PRECES_R) }
                }

                '∞' -> {
                    append("\n\n")
                    withStyle(SpanStyle(color = rubricColor)) { append(PRECES_IL) }
                    append("\n\n")
                }

                else -> append(c)
                /*
                 .replace("_", NBSP_SALMOS)
            .replace("§", BRS)
            .replace("~", BR)
            .replace("¦", NBSP_4)
            .replace("⊣", BR + NBSP_4)
            .replace("≠", String.format("%s %s ", NBSP_SALMOS, toRedFont(PRECES_R)))
            .replace("∞", String.format("%s%s%s", BRS, toRedFont(PRECES_IL), BRS))
            .replace("⊚", OBIEN)
            .replace("†", toRedFont(" † "))
            .replace("⊓", toRedFont(" N. "))
            .replace("Ɽ", toRedFont(" R. "))
            .replace("⟨", toRedFont("("))
            .replace("⟩", toRedFont(")"))
            .replace("ⱱ", toRedFont("V/."))
            .replace("ⱴ", toRedFont("R/."))
            .replace("Ʀ", toRedFont(" R/. ") + BRS) //NEW
            .replace("℟", toRedFont("℟.")) //.replace("℟",  toRed("℟") )
            .replace("℣", toRedFont("℣."))
            .replace("≀", BR + NBSP_4 + NBSP_4)
            .replace("~", BR)
            .replace("§", BRS)
            .replace("∸", BRS)
            .replace("⊞", toRedFont("✚. "))
            .replace("⊝", toRedFont("C. "))
            .replace("⊟", toRedFont("S. "))
            .replace("[rubrica]", CSS_RED_A)
            .replace("[/rubrica]", CSS_RED_Z)
            .replace("%cssBlackOn", "<font color=\"#000000\">")
            .replace("%cssBlackOff", "</font>")
                * */
            }
        }
    }

    fun formatTitle(sOrigen: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(toUpper(sOrigen))
        ssb.setSpan(CharacterStyle.wrap(red), 0, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.setSpan(
            CharacterStyle.wrap(StyleSpan(Typeface.BOLD)),
            0,
            ssb.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return ssb
    }

    fun capitalize(s: String): String {
        return s.replaceFirstChar { it.uppercase() }
    }

    fun toRedBold(sOrigen: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(sOrigen)
        ssb.setSpan(CharacterStyle.wrap(red), 0, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.setSpan(
            CharacterStyle.wrap(StyleSpan(Typeface.BOLD)),
            0,
            ssb.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return ssb
    }


    fun formatSubTitle(sOrigen: String?): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(sOrigen)
        val textSize = RelativeSizeSpan(H3)
        ssb.setSpan(
            CharacterStyle.wrap(textSize),
            0,
            ssb.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.setSpan(
            CharacterStyle.wrap(StyleSpan(Typeface.BOLD)),
            0,
            ssb.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return ssb
    }

    fun formatSubTitleToLower(sOrigen: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(toLower(sOrigen))
        val textSize = RelativeSizeSpan(H3)
        ssb.setSpan(
            CharacterStyle.wrap(textSize),
            0,
            ssb.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.setSpan(
            CharacterStyle.wrap(StyleSpan(Typeface.BOLD)),
            0,
            ssb.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return ssb
    }

    fun toSmallSize(sOrigen: String?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(0.8f)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    private fun toSmallSizes(sOrigen: Spanned?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(0.8f)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toSmallSizeRed(sOrigen: String?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(0.8f)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(red),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toH3(sOrigen: String?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(H3)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(StyleSpan(Typeface.BOLD)),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toH4(sOrigen: String?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(H4)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(StyleSpan(Typeface.BOLD)),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toH2(sOrigen: String?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(H2)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toH2Red(sOrigen: String?): SpannableStringBuilder {
        val h2 = RelativeSizeSpan(H2)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(h2), 0,
            spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(red),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toH2RedNew(sOrigen: String): SpannableStringBuilder {
        val h2 = RelativeSizeSpan(H2)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(toRedHtml(sOrigen))
        //spannableString=fromHtml(spannableString);
        spannableString.setSpan(
            CharacterStyle.wrap(h2), 0,
            spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(red),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toH3Red(sOrigen: String?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(H3)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(red),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toH4Red(sOrigen: String?): SpannableStringBuilder {
        val smallSizeText = RelativeSizeSpan(H4)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(smallSizeText),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(red),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    fun toRed(sOrigen: String?): SpannableStringBuilder {
        val s = SpannableString(sOrigen)
        s.setSpan(CharacterStyle.wrap(red), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return SpannableStringBuilder(s)
    }

    fun toRedCompose(text: String?, rubricColor: Color): AnnotatedString {
        return buildAnnotatedString {
            withStyle(style = SpanStyle(color = rubricColor)) {
                append(text)
            }
            //append(antiphon)
        }
    }

    fun toRedFont(s: String?): String {
        return String.format("<font color=\"%s\">%s</font>", rubricColor, s)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toRedFontCompose(s: String, rubricColor: Color): String {
        var color = "#" + rubricColor.toArgb()
        //#ffff0000
        //val str = rubricColor.toArgb().toHexString(HexFormat.UpperCase).takeLast(6)

        return String.format(
            "<font color=\"#%s\">%s</font>",
            ComposeColorUtil.getHexColor(rubricColor),
            s
        )
    }

    fun toRedNew(sOrigen: SpannableStringBuilder): SpannableStringBuilder {
        sOrigen.setSpan(
            CharacterStyle.wrap(red),
            0,
            sOrigen.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return sOrigen
    }


    private fun toRedHtml(sOrigen: String): SpannableStringBuilder {
        val s = SpannableString(fromHtml(sOrigen))
        s.setSpan(CharacterStyle.wrap(red), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return SpannableStringBuilder(s)
    }


    /**
     *
     * Sustituye y/o formatea determinados caracteres según una convención de marcado ideada por mí mismo.
     *
     * La convención es la siguiente:
     *
     *  *
     *
     *
     * @param sOrigen Cadena original para hacer el reemplazo
     * @return La cadena formateada
     */
    fun getFormato(sOrigen: String): String {
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫  ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿
        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟
        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿
        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟
        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿

        Nuevos valores desde v. 2022.01.01:
            ⊞ en lugar de τ ...  ✚.
            ⊝ en lugar de ν ...  C.
            ⊟ en lugar de ς ...  S.
            ⊓  en lugar de ƞ ... N.
            ⊚  en lugar de ℇ ... O bien
        */
        return sOrigen
            .replace("_", NBSP_SALMOS)
            .replace("§", BRS)
            .replace("~", BR)
            .replace("¦", NBSP_4)
            .replace("⊣", BR + NBSP_4)
            .replace("≠", String.format("%s %s ", NBSP_SALMOS, toRedFont(PRECES_R)))
            .replace("∞", String.format("%s%s%s", BRS, toRedFont(PRECES_IL), BRS))
            .replace("⊚", OBIEN)
            .replace("†", toRedFont(" † "))
            .replace("⊓", toRedFont(" N. "))
            .replace("Ɽ", toRedFont(" R. "))
            .replace("⟨", toRedFont("("))
            .replace("⟩", toRedFont(")"))
            .replace("ⱱ", toRedFont("V/."))
            .replace("ⱴ", toRedFont("R/."))
            .replace("Ʀ", toRedFont(" R/. ") + BRS) //NEW
            .replace("℟", toRedFont("℟.")) //.replace("℟",  toRed("℟") )
            .replace("℣", toRedFont("℣."))
            .replace("≀", BR + NBSP_4 + NBSP_4)
            .replace("~", BR)
            .replace("§", BRS)
            .replace("∸", BRS)
            .replace("</p>", BRS, true)
            .replace("<p>", "", true)

            .replace("⊞", toRedFont("✚. "))
            .replace("⊝", toRedFont("C. "))
            .replace("⊟", toRedFont("S. "))
            .replace("[rubrica]", CSS_RED_A)
            .replace("[/rubrica]", CSS_RED_Z)
            .replace("%cssBlackOn", "<font color=\"#000000\">")
            .replace("%cssBlackOff", "</font>")

    }

    /**
     *
     * Sustituye y/o formatea determinados caracteres según una convención de marcado ideada por mí mismo.
     *
     * La convención es la siguiente:
     *
     *  *
     *
     *
     * @param sOrigen Cadena original para hacer el reemplazo
     * @return La cadena formateada
     */
    //@RequiresApi(Build.VERSION_CODES.O)
    fun getFormato(sOrigen: String, rubricColor: Color): String {
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫  ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿
        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟
        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿
        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟
        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿

        Nuevos valores desde v. 2022.01.01:
            ⊞ en lugar de τ ...  ✚.
            ⊝ en lugar de ν ...  C.
            ⊟ en lugar de ς ...  S.
            ⊓  en lugar de ƞ ... N.
            ⊚  en lugar de ℇ ... O bien
        */
        return sOrigen
            .replace("_", TABTRI)
            .replace("§", NEWBIS)
            .replace("~", NEWONE)
            .replace("¦", TABBIS)
            .replace("⊣", NEWONE + TABTRI)
            .replace("≠", String.format("%s %s ", TABTRI, toRedFont(PRECES_R)))
            .replace("∞", String.format("%s%s%s", NEWBIS, toRedFont(PRECES_IL), NEWBIS))
            .replace("⊚", OBIEN)
            .replace("†", toRedFont(" † "))
            .replace("⊓", toRedFont(" N. "))
            .replace("Ɽ", toRedFont(" R. "))
            .replace("⟨", toRedFont("("))
            .replace("⟩", toRedFont(")"))
            .replace("ⱱ", toRedFont("V/."))
            .replace("ⱴ", toRedFont("R/."))
            .replace("Ʀ", toRedFont(" R/. ") + NEWBIS) //NEW
            //.replace("℟", toRedFontCompose("℟.",rubricColor)) //.replace("℟",  toRed("℟") )
            //.replace("℣", toRedFontCompose("℣.",rubricColor))
            .replace("≀", NEWONE + TABTRI + TABTRI)
            .replace("~", NEWONE)
            .replace("§", NEWBIS)
            .replace("∸", NEWBIS)
            .replace("</p>", NEWBIS, true)
            .replace("<p>", "", true)

            .replace("⊞", toRedFont("✚. "))
            .replace("⊝", toRedFont("C. "))
            .replace("⊟", toRedFont("S. "))
            .replace("[rubrica]", CSS_RED_A)
            .replace("[/rubrica]", CSS_RED_Z)
            .replace("%cssBlackOn", "<font color=\"#000000\">")
            .replace("%cssBlackOff", "</font>")

    }


    /**
     *
     * Extrae, para la lectura por voz, algunos caracteres que pueden agregarse para la presentación visual.
     *
     * @param sOrigen Cadena original para hacer el reemplazo
     * @return La cadena formateada
     * @since 2023.1
     */
    fun getFormatoForRead(sOrigen: String): String {
        return sOrigen
            .replace("_", " ")
            .replace("§", " ")
            .replace("~", " ")
            .replace("¦", " ")
            .replace("⊣", " ")
            .replace("≠", " ")
            .replace("∞", " ")
            .replace("⊚", OBIEN)
            .replace("†", "")
            .replace("⊓", " ")
            .replace("Ɽ", " ")
            .replace("⟨", " ")
            .replace("⟩", " ")
            .replace("ⱱ", " ")
            .replace("ⱴ", " ")
            .replace("Ʀ", " ")
            .replace("℟", " ")
            .replace("℣", " ")
            .replace("≀", " ")
            .replace("~", " ")
            .replace("§", " ")
            .replace("∸", " ")
            .replace("⊞", " ")
            .replace("⊝", " ")
            .replace("⊟", " ")
            .replace("[rubrica]", " ")
            .replace("[/rubrica]", " ")
            .replace("«", "")
            .replace(".»", "».")
            .replace("\"", "")
            .replace("'", "")
            .replace("“", "")
            .replace("”", "")
            .replace("(...)", ".")
            .replace("(", "")
            .replace(")", "")
            .replace("...", ".")
            .replace("[...]", "")
            .replace("ς", "")
            .replace("ν", "")
            .replace("τ", "")
            .replace("*", "")
            .replace("—", "") //.replace("ü", "u")
            .replace("⊞", " ")
            .replace("⊝", " ")
            .replace("⊟", " ")
            .replace("⊓", " ")
    }

    fun replaceByTime(mText: String, timeID: Int): String {
        val sFormateado: String = if (timeID == 6) {
            mText
                .replace("Ƥ. ", "Aleluya. ")
                .replace(" Ƥ.", " Aleluya.")
                .replace("Ƥ.", " Aleluya.")
                .replace("Ƥ".toRegex(), " Aleluya.")
                .replace("α", " Aleluya.")
                .replace("αα", " Aleluya, aleluya.")
        } else {
            mText
                .replace("Ƥ. ", "")
                .replace(" Ƥ.", "")
                .replace("Ƥ.", "")
                .replace("Ƥ".toRegex(), "")
                .replace("α.", "")
                .replace("αα", "")
                .replace("α", "")
        }
        return sFormateado
    }


    fun fromHtml(s: String): Spanned {
        return Html.fromHtml(getFormato(s), Html.FROM_HTML_MODE_LEGACY)
    }

    fun fromHtmlWithOutFormat(s: String): Spanned {
        return Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
    }


    fun fromHtmlForRead(s: String): Spanned {
        return fromHtml(getFormatoForRead(s))
    }

    fun fromHtmlSmall(s: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(fromHtml(s))
        return toSmallSizes(ssb)
    }

    fun composeSmallText(text: String): AnnotatedString {
        return buildAnnotatedString {
            //withStyle(style = ParagraphStyle(lineHeight = 20.sp)) {
            withStyle(style = SpanStyle(fontSize = 12.sp)) {
                append(fromHtml(text))
            }
            //}
        }
    }


    /**
     * Este método es un ayudador para la lectura de voz,
     * que intenta quitar ciertas combinaciones de caracteres que distraerían la atención
     * durante la lectura de voz, porque son pronunciables.
     * Remueve también ciertos delimitadores que se usan
     * para dar formato al texto en la vista
     *
     * @param sOrigen Cadena original
     * @return La cadena sin las comillas
     */
    fun stripQuotation(sOrigen: String): String {
        val sFormateado: String = sOrigen
            .replace("«", "")
            .replace(".»", "».")
            .replace("\"", "")
            .replace("'", "")
            .replace("“", "")
            .replace("”", "")
            .replace("(...)", ".")
            .replace("(", "")
            .replace(")", "")
            .replace("...", ".")
            .replace("[...]", "")
            .replace("ς", "")
            .replace("ν", "")
            .replace("τ", "")
            .replace("*", "")
            .replace("—", "") //.replace("ü", "u")
            .replace("⊞", " ")
            .replace("⊝", " ")
            .replace("⊟", " ")
            .replace("⊓", " ")
        return sFormateado.trim { it <= ' ' }
    }

    /**
     * Este método cambia las <p></p> por \n,
     *
     * @param sOrigen Cadena original
     * @return La cadena sin las comillas
     */
    fun replaceHtmlTags(sOrigen: String): String {
        val sFormateado: String = sOrigen
            .replace("</p>", BRS, true)
            .replace("<p>", "", true)
            .replace("¦", "\t")
            .replace("≀", "\n\t\t")
            .replace("§", "\n\n")


        return sFormateado.trim { it <= ' ' }
    }

    /**
     * Este método cambia las <p></p> por \n,
     *
     * @param sOrigen Cadena original
     * @return La cadena sin las comillas
     */
    fun tagsHtmlToSystem(sOrigen: String): String {
        val sFormateado: String = sOrigen
            .replace("</p>", LS2, true)
            .replace("<p>", "", true)
        return sFormateado.trim { it <= ' ' }
    }


    /**
     * Este método es un ayudador para la lectura de voz,
     * que agrega el punto al final de aquellos contenidos que no lo tengan.
     * El punto es el carácter que se usa para separar los bloques de lectura de voz.
     *
     * @param sOrigen La cadena original
     * @return Cadena con final normalizado
     */
    fun normalizeEnd(sOrigen: String): String {
        return if (sOrigen.endsWith(".")) sOrigen else String.format("%s.", sOrigen)
    }

    /**
     * Método que devuelve la fecha del sistema menos N días en formato yyyyMMdd
     *
     * @param minusDays Cantidad de días a sustraer
     * @return Un número con la fecha
     */
    fun getTodayMinus(minusDays: Int): Int {
        val today = LocalDate.now().minusDays(minusDays.toLong())
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        return today.toInt()
    }

    val hoy: String
        /**
         * Método que devuelve la fecha del sistema en formato yyyyMMdd
         *
         * @return Una cadena con la fecha formateada
         */
        get() {
            val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            return format.format(Date())
        }

    /**
     * Método que devuelve una fecha dada en un determinado formato
     *
     * @since 2024.1
     *
     * @param theDate La cadena con la fecha a formatear
     * @param inputFormat El formato original de la fecha
     * @param outPutFormat El formato al que será convertida la fecha dada
     * @return Una cadena con la fecha formatada
     */
    @JvmStatic
    fun formatDate(theDate: String?, inputFormat: String?, outPutFormat: String?): String {
        val loc = Locale("es", "ES")
        val sdf = SimpleDateFormat(outPutFormat, loc)
        val df: DateFormat = SimpleDateFormat(inputFormat, loc)
        return try {
            sdf.format(Objects.requireNonNull(df.parse(theDate!!)))
        } catch (e: ParseException) {
            ""
        }
    }

    val fecha: String
        /**
         * Método que devuelve la fecha del sistema en forma legible: 22 de Agosto de 1972
         *
         * @return Una cadena con la fecha
         */
        get() {
            val mFormat = SimpleDateFormat("dd 'de' MMMM yyyy", Locale.getDefault())
            return mFormat.format(Date())
        }

    /**
     * Método que devuelve el mes y el día de una fecha dada
     *
     * @param dateString La fecha a formatear
     * @return Un array con el mes y el día de la fecha
     * @since 2023.1
     */
    fun getMonthAndDay(dateString: String?): IntArray? {
        val c = Calendar.getInstance()
        val monthAndDay = IntArray(2)
        return try {
            val theDate = SimpleDateFormat("yyyyMMdd", Locale("es", "ES")).parse(dateString!!)
            c.time = Objects.requireNonNull(theDate)
            monthAndDay[0] = c[Calendar.MONTH] + 1
            monthAndDay[1] = c[Calendar.DAY_OF_MONTH]
            monthAndDay
        } catch (e: ParseException) {
            null
        }
    }

    /**
     * Crea mensajes de error personalizados
     *
     * @param msg    El mensaje de error
     * @param params Los parámetros que se usaron para obtener la información en el contexto
     * @return Una cadena con el mensaje personalizado
     * @since 2022.01
     */
    fun createErrorMessage(msg: String?, params: String?): String {
        return String.format("%s\nParámetros: %s", msg, params)
    }

    fun createErrorMessage(msg: String?): String {
        return String.format(
            Locale("es"),
            "Ha ocurrido el siguiente error:%s%s %s%s %sVersión de la aplicación: %s",
            LS2,
            msg,
            LS2,
            ERR_REPORT,
            LS2,
            VERSION_CODE_FORMATTED
        )
    }

    /**
     * Obtiene las dos cifras del mes desde una cadena de fecha
     *
     * @param date La fecha en formato yyyymmdd
     * @return Una cadena con la parte del mes
     * @since 2022.1
     */
    //    @SuppressWarnings(value = "unused")
    fun getMonth(date: String): String {
        return date.substring(4, 6)
    }

    /**
     * Obtiene las dos cifras del día desde una cadena de fecha
     *
     * @param date La fecha en formato yyyymmdd
     * @return Una cadena con la parte del día
     * @since 2022.1
     */
    fun getDay(date: String): String {
        return date.substring(6, 8)
    }

    fun getDayName(day: Int): String {
        if (day in 1..7) {
            val locale = Locale("es", "ES")
            val symbols = DateFormatSymbols(locale)
            val dayNames = symbols.weekdays
            return dayNames[day]
        }
        return ""
    }

    fun pointAtEnd(s: String?): String {
        return String.format("%s.", s)
    }

    fun toUpper(s: String): String {
        return s.uppercase(Locale.getDefault())
    }

    fun toLower(s: String): String {
        return s.lowercase(Locale.getDefault())
    }

    fun toH1Red(sOrigen: String?): SpannableStringBuilder {
        val h2 = RelativeSizeSpan(H1)
        val ssb = SpannableStringBuilder("")
        val spannableString = SpannableString(sOrigen)
        spannableString.setSpan(
            CharacterStyle.wrap(h2), 0,
            spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            CharacterStyle.wrap(red),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(spannableString)
        return ssb
    }

    /**
     * Convierte números arábigos a romanos
     *
     * @since 2024.1
     */
    fun toRoman(arabicInput: Int?): String {
        var romanOutput = ""
        val arabicToRoman = mutableMapOf(
            Pair(1, "I"),
            Pair(4, "IV"),
            Pair(5, "V"),
            Pair(9, "IX"),
            Pair(10, "X"),
            Pair(40, "XL"),
            Pair(50, "L"),
            Pair(90, "XC"),
            Pair(100, "C")
        )

        val arrayRoman = arrayOf(100, 90, 50, 40, 10, 9, 5, 4, 1)
        var startCount = 0

        if (arabicInput!! > 100) {
            romanOutput = ""
        } else {
            if (arabicInput > 90) {
                startCount = 1
            } else if (arabicInput > 50) {
                startCount = 2
            } else if (arabicInput > 40) {
                startCount = 3
            } else if (arabicInput > 10) {
                startCount = 4
            } else if (arabicInput > 5) {
                startCount = 5
            } else if (arabicInput > 4) {
                startCount = 6
            } else if (arabicInput > 1) {
                startCount = 7
            }

            var num = arabicInput
            for (i in startCount..<arrayRoman.size) {
                var count = num / arrayRoman[i]
                num %= arrayRoman[i]
                while (count > 0) {
                    romanOutput += arabicToRoman[arrayRoman[i]]
                    count--
                }
            }

        }
        return romanOutput
    }

    /**
     * Método que devuelve la fecha y hora actual en formato yyyy-MM-dd HH:mm:ss
     *
     * @return Una cadena con la fecha
     */
    fun getCurrentTimeStamp(): String {
        val stamp = Timestamp(System.currentTimeMillis())
        val date = Date(stamp.time)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(date)

    }


}