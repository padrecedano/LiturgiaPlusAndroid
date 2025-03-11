package org.deiverbum.app.core.model.liturgia

import android.text.SpannableStringBuilder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils

data class Kyrie(
    var kyrieID: Int,
    var kyrie: String
) {

    @Ignore
    val introduccion: String =
        "Es muy de alabar que, después de la invocación inicial, " +
                "se haga el examen de conciencia, el cual en la celebración comunitaria puede concluirse con un acto penitencial, de la siguiente forma:" +
                "|Hermanos, habiendo llegado al final de esta jornada que Dios nos ha concedido, reconozcamos sinceramente nuestros pecados.|"


    @Ignore
    val primumRubrica: String = "Es muy de alabar que, después de la invocación inicial, " +
            "se haga el examen de conciencia, el cual en la celebración comunitaria puede concluirse con un acto penitencial, de la siguiente forma:"

    @Ignore
    val introductio: String =
        "Hermanos, habiendo llegado al final de esta jornada que Dios nos ha concedido, " +
                "reconozcamos sinceramente nuestros pecados."

    @Ignore
    val secundoRubrica: String =
        "Todos examinan en silencio su conciencia. " +
                "Terminando el examen se añade la fórmula penitencial:"
    @Ignore
    val conclusio: List<String> = listOf(
        "El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.",
        "Amén."
    )

    @Ignore
    val tertiaRubrica: String = "Pueden usarse otras invocaciones penitenciales."

    @Ignore
    val quartaRubrica: String =
        "Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos:"

    @get:Ignore
    private val introduccionForRead: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            ssb.append(Utils.fromHtml("<p>EXAMEN DE CONCIENCIA.</p>"))
            val introArray =
                introduccion.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (introArray.size == 3) {
                ssb.append(Utils.fromHtml("<p>" + introArray[1] + "</p>"))
            } else {
                ssb.append(introduccion)
            }
            return ssb
        }

    private fun getIntroduccion(): SpannableStringBuilder {

        val ssb = SpannableStringBuilder()
        ssb.append(Utils.formatTitle(Constants.TITLE_SOUL_SEARCHING))
        ssb.append(Utils.LS2)
        val introArray =
            introduccion.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (introArray.size == 3) {
            ssb.append(Utils.toSmallSizeRed(introArray[0]))
            ssb.append(Utils.LS2)
            ssb.append(introArray[1])
            ssb.append(Utils.LS2)
            ssb.append(Utils.toSmallSizeRed(introArray[2]))
        } else {
            ssb.append(introduccion)
        }
        return ssb
    }

    fun getIntroduccionComposable(rubricColor: Color): AnnotatedString {

        val introArray =
            introduccion.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return buildAnnotatedString {
            if (introArray.size == 3) {
                //ssb.append(Utils.toSmallSizeRed(introArray[0]))
                withStyle(style = SpanStyle(color = rubricColor/*,fontSize = 10.sp*/)) {
                    append(introArray[0])
                }
                append(Utils.LS2)
                append(introArray[1])
                append(Utils.LS2)
                withStyle(style = SpanStyle(color = rubricColor/*,fontSize = 10.sp*/)) {
                    append(introArray[2])
                }
                //append(Utils.toSmallSizeRed(introArray[2]))
            } else {
                //ssb.append(introduccion)
                //introduccion
                withStyle(style = SpanStyle(color = rubricColor/*,fontSize = 10.sp*/)) {
                    append(introduccion)
                }
            }
            append(Utils.LS2)
            append(Utils.fromHtml(kyrie))
            append(Utils.LS2)
            withStyle(style = SpanStyle(color = rubricColor)) {
                append("Pueden usarse otras invocaciones penitenciales.")
            }

            append(Utils.LS2)

            withStyle(style = SpanStyle(color = rubricColor)) {
                append("Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos:")
            }
            append(Utils.LS2)
            withStyle(style = SpanStyle(color = rubricColor)) {
                append(LiturgyHelper.V)
            }
            append("El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.")
            append(Utils.LS2)
            withStyle(style = SpanStyle(color = rubricColor)) {
                append(LiturgyHelper.R)
            }

            append("Amén.")
        }
        //return ssb
    }


    private val conclusionForRead: String
        get() = "El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna. Amén."

    private fun getConclusion(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toSmallSizeRed("Pueden usarse otras invocaciones penitenciales."))
        ssb.append(Utils.LS)
        ssb.append(Utils.toSmallSizeRed("Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos:"))
        ssb.append(Utils.LS2)
        ssb.append(Utils.toRed("V. "))
        ssb.append("El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.")
        ssb.append(Utils.LS2)
        ssb.append(Utils.toRed("R. "))
        ssb.append("Amén.")
        return ssb
    }

    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(getIntroduccion())
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(kyrie))
            sb.append(Utils.LS2)
            sb.append(getConclusion())
            return sb
        }
    val allForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(introduccionForRead)
            sb.append(Utils.fromHtml(kyrie))
            sb.append(conclusionForRead)
            return sb
        }
}