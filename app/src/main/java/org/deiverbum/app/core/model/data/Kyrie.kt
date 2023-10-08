package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Kyrie(
    var kyrieID: Int,
    var kyrie: String
) {

    @Ignore
    private val introduccion: String =
        "Es muy de alabar que, después de la invocación inicial, se haga el examen de conciencia, el cual en la celebración comunitaria puede concluirse con un acto penitencial, de la siguiente forma:|Hermanos, habiendo llegado al final de esta jornada que Dios nos ha concedido, reconozcamos sinceramente nuestros pecados.|Todos examinan en silencio su conciencia. Terminando el examen se añade la fórmula penitencial:"

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