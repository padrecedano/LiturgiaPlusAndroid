package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.LS

open class LHResponsoriumBrevis(
    @ColumnInfo(name = "text")
    open var responsorium: String = "",
    @ColumnInfo(name = "type")
    open var typus: Int = 0
) {

    var responsoryID: Int = 0


    @get:Ignore
    open val header: SpannableStringBuilder
        get() = if (typus > 0) {
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
        val respArray =
            responsorium.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val s = StringBuilder()
        if (hourId < 3 || hourId > 5) {
            sb.append(header)
            s.append(Constants.BRS)
        }
        when (typus) {
            0 -> {
                sb.append(Constants.LS2)
                sb.append(responsorium)
                sb.append(LS)
            }

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
                sb.append(Utils.LS2)
                sb.append(getErrorMessage())
                sb.append("Tamaño del responsorio: ")
                sb.append(respArray.size.toString())
                sb.append(" Código forma: ")
                sb.append(typus.toString())
                sb.append(LS)
            }
        }
        return sb
    }

    open fun getComposable(rubricColor: Color): AnnotatedString {
        val respArray =
            responsorium.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        /*if (hourId < 3 || hourId > 5) {
            sb.append(header)
            s.append(Constants.BRS)
        }*/
        return buildAnnotatedString {
            when (typus) {
                0 -> {
                    append(Constants.LS2)
                    append(responsorium)
                    append(LS)
                }

                1 -> if (respArray.size == 3) {
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(" * ")
                    }
                    append(respArray[1])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[2])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[1][0].uppercaseChar())
                    append(respArray[1].substring(1))
                    //append(Constants.LS2)
                    //append(Utils.fromHtml(s.toString()))
                }

                2 -> {
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(" * ")
                    }
                    append(respArray[1])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[2])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[1][0].uppercaseChar())
                    append(respArray[1].substring(1))
                    //append(Constants.LS2)
                    //sb.append(Utils.fromHtml(s.toString()))
                }

                6001230 ->
                    if (respArray.size == 4) {
                        withStyle(style = SpanStyle(color = rubricColor)) {
                            append(LiturgyHelper.V)
                        }
                        append(respArray[0])
                        append(Constants.LS)
                        withStyle(style = SpanStyle(color = rubricColor)) {
                            append(LiturgyHelper.R)
                        }
                        append(respArray[0])
                        append(Constants.LS2)
                        withStyle(style = SpanStyle(color = rubricColor)) {
                            append(LiturgyHelper.V)
                        }
                        append(respArray[1])
                        append(Constants.LS)
                        withStyle(style = SpanStyle(color = rubricColor)) {
                            append(LiturgyHelper.R)
                        }
                        append(respArray[2])
                        append(Constants.LS2)
                        withStyle(style = SpanStyle(color = rubricColor)) {
                            append(LiturgyHelper.V)
                        }
                        append(respArray[3])
                        append(Constants.LS)
                        withStyle(style = SpanStyle(color = rubricColor)) {
                            append(LiturgyHelper.R)
                        }
                        append(respArray[0])
                        //append(Constants.LS2)
                        //append(Utils.fromHtml(s.toString()))
                    }

                6001020 -> if (respArray.size == 3) {
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[0])
                    append(Constants.LS)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[1])
                    append(Constants.LS)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[2])
                    append(Constants.LS)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    //append(Constants.LS2)
                    //append(Utils.fromHtml(s.toString()))
                }

                4 -> {
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[0])
                    append(Constants.LS)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[1])
                    append(Constants.LS)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    append(Constants.LS2)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[2])
                    append(Constants.LS)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[0])
                    //append(Constants.LS2)
                    //append(Utils.fromHtml(s.toString()))
                }

                201 -> {
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.V)
                    }
                    append(respArray[0])
                    append(Constants.LS)
                    withStyle(style = SpanStyle(color = rubricColor)) {
                        append(LiturgyHelper.R)
                    }
                    append(respArray[1])
                    //append(Constants.LS2)
                    //append(Utils.fromHtml(s.toString()))
                }

                else -> {
                    append(Utils.LS2)
                    append(getErrorMessageComposable(rubricColor))
                    append("Tamaño del responsorio: ")
                    append(respArray.size.toString())
                    append(" Código forma: ")
                    append(typus.toString())
                    append(LS)
                }
            }
            //return sb
        }
    }


    /**
     * Devuelve el texto del LHResponsoryShort Breve con formato
     * y adaptación adecuada para la lectura de voz
     *
     * @return Una cadena formateada con el responsorio
     */

    @get:Ignore
    open val allForRead: String
        get() {
            val respArray =
                responsorium.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val s = StringBuilder()
            s.append(Utils.pointAtEnd(Constants.TITLE_RESPONSORY_SHORT))
            when (typus) {
                0 -> s.append(responsorium)
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

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        responsorium = Utils.replaceByTime(responsorium, calendarTime)
    }

    protected fun getErrorMessage(): SpannableStringBuilder {
        return SpannableStringBuilder()
            .append(
                Utils.toRed("¡ERROR! ")
                    .append(Utils.LS2)
                    .append("Hay un error en el responsorio de este día, por favor comunícalo al desarrollador a la dirección siguiente: ")
                    .append(Utils.toRed(Configuration.MY_GMAIL))
                    .append(Utils.LS2),
            )

    }

    protected fun getErrorMessageComposable(rubricColor: Color): AnnotatedString {
        return buildAnnotatedString {
            withStyle(style = SpanStyle(color = rubricColor)) {
                append("¡ERROR! ")
            }
            append(Utils.LS2)
            append("Hay un error en el responsorio de este día, por favor comunícalo al desarrollador a la dirección siguiente: ")
            append(Utils.toRed(Configuration.MY_GMAIL))
            append(Utils.LS2)
        }
    }
}
