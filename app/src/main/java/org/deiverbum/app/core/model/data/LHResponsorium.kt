package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils
import java.util.Locale

class LHResponsorium(
    override var text: String = "",
    override var type: Int = 0,
    var source: String = ""
) : LHResponsoriumBrevis(text, type) {
    @get:Ignore
    override val header: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            if (source != null) {
                sb.append(
                    Utils.toRed(
                        String.format(
                            Locale("es"),
                            "%s%s%s",
                            Constants.TITLE_RESPONSORY,
                            "     ",
                            source
                        )
                    )
                )
            } else {
                sb.append(Utils.toRed(Constants.TITLE_RESPONSORY))
            }
            sb.append(Utils.LS2)
            return sb
        }

    /**
     *
     * Método que crea la cadena completa de un responsorio dado.
     *
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     * @since 2022.01
     */
    @get:Ignore
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            val respArray =
                text.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val s = StringBuilder()
            sb.append(header)
            when (type) {
                1 -> if (respArray.size == 3) {
                    s.append(Utils.toRedFont("R. "))
                    s.append(respArray[0])
                    s.append(Utils.toRed(" * "))
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
                    s.append(Utils.toRed(" * "))
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


                    sb.append(getErrorMessage())
                    sb.append("Tamaño del responsorio: ")
                    sb.append(respArray.size.toString())
                    sb.append(Utils.LS2)
                    sb.append(" Código forma: ")
                    sb.append(type.toString())
                    sb.append(Utils.LS2)
                }
            }
            return sb
        }

    /**
     * Método que crea la cadena completa de un responsorio dado destinado a la lectura de voz
     *
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */
    @get:Ignore
    override val allForRead: String
        get() {
            val respArray =
                text.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val s = StringBuilder()
            when (type) {
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

                else -> s.append(getErrorMessage())
            }
            return s.toString()
        }
}