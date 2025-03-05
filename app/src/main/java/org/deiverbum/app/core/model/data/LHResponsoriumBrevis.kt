package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

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
}
