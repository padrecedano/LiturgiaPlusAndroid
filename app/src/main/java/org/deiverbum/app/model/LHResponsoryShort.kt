package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils
import java.util.*

open class LHResponsoryShort {
    var responsoryID: Int? = null
    var text: String = ""
    var type: Int? = null
    @Suppress("unused")
    fun getHeader(hourId: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        if (hourId < 3 || hourId > 5) {
            sb.append(
                Utils.toRed(
                    String.format(
                        Locale("es"),
                        "%s%s%s",
                        Utils.toUpper(Constants.TITLE_RESPONSORY_SHORT),
                        "     ",
                        "getRef()"
                    )
                )
            )
        }
        return sb
    }

    open val header: SpannableStringBuilder
        get() = if (type!! > 0) {
            Utils.formatTitle(Constants.TITLE_RESPONSORY_SHORT)
        } else {
            Utils.toRed("En lugar del responsorio breve, se dice la siguiente antífona:")
        }

    /**
     *
     * Método que crea la cadena completa de un responsorio dado.
     *
     * @param hourId El ID de la hora
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     * @since 2022.01
     */
    fun getAll(hourId: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        val respArray = text.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val s = StringBuilder()
        if (hourId < 3 || hourId > 5) {
            sb.append(header)
            s.append(Constants.BRS)
        }
        when (type) {
            0 -> {
                sb.append(Constants.LS2)
                sb.append(text)
                sb.append(Utils.LS)
            }
            1 -> if (respArray.size == 3) {
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Utils.toRedFont(" * "))
                s.append(respArray[1])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[2])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[1][0].uppercaseChar())
                s.append(respArray[1].substring(1))
                s.append(Constants.BRS)
                sb.append(Utils.fromHtml(s.toString()))
            }
            2 -> {
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Utils.toRedFont(" * "))
                s.append(respArray[1])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[2])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[1][0].uppercaseChar())
                s.append(respArray[1].substring(1))
                s.append(Constants.BRS)
                sb.append(Utils.fromHtml(s.toString()))
            }
            6001230 -> if (respArray.size == 4) {
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[0])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[1])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[2])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[3])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                sb.append(Utils.fromHtml(s.toString()))
            }
            6001020 -> if (respArray.size == 3) {
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[0])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[1])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[2])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                sb.append(Utils.fromHtml(s.toString()))
            }
            4 -> {
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[0])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[1])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[2])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[0])
                s.append(Constants.BRS)
                sb.append(Utils.fromHtml(s.toString()))
            }
            201 -> {
                s.append(Utils.toRedFont("V. "))
                s.append(respArray[0])
                s.append(Constants.BR)
                s.append(Utils.toRedFont("R. "))
                s.append(respArray[1])
                s.append(Constants.BRS)
                sb.append(Utils.fromHtml(s.toString()))
            }
            else -> {
                sb.append(
                    Utils.fromHtml(
                        Constants.ERR_RESPONSORIO))
                sb.append(Utils.LS)
                sb.append("Tamaño del responsorio: ")
                sb.append(respArray.size.toString())
                sb.append(" Código forma: ")
                sb.append(type.toString())
                sb.append(Utils.LS)
            }
        }
        return sb
    }

    /**
     * Devuelve el texto del LHResponsoryShort Breve con formato
     * y adaptación adecuada para la lectura de voz
     *
     * @return Una cadena formateada con el responsorio
     */
    open val allForRead: String
        get() {
            val respArray =
                text.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val s = StringBuilder()
            s.append(Utils.pointAtEnd(Constants.TITLE_RESPONSORY_SHORT))
            when (type) {
                0 -> s.append(text)
                1 -> if (respArray.size == 3) {
                    s.append(respArray[0])
                    s.append(respArray[1])
                    s.append(respArray[2])
                    s.append(respArray[1])
                }
                2 -> {
                    s.append(respArray[0])
                    s.append(respArray[1])
                    s.append(Constants.BRS)
                    s.append(respArray[2])
                    s.append(Constants.BRS)
                    s.append(respArray[1])
                }
                6001230 -> if (respArray.size == 4) {
                    s.append(respArray[0])
                    s.append(respArray[0])
                    s.append(respArray[1])
                    s.append(respArray[2])
                    s.append(respArray[3])
                    s.append(respArray[0])
                }
                6001020 -> if (respArray.size == 3) {
                    s.append(respArray[0])
                    s.append(respArray[0])
                    s.append(respArray[1])
                    s.append(respArray[0])
                    s.append(respArray[2])
                    s.append(respArray[0])
                }
                4 -> {
                    s.append(respArray[0])
                    s.append(respArray[0])
                    s.append(respArray[1])
                    s.append(respArray[0])
                    s.append(respArray[2])
                    s.append(respArray[0])
                }
                201 -> {
                    s.append(respArray[0])
                    s.append(respArray[1])
                }
                else -> s.append(Constants.ERR_RESPONSORIO)
            }
            return s.toString()
        }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        text = Utils.replaceByTime(text, calendarTime)
    }
}