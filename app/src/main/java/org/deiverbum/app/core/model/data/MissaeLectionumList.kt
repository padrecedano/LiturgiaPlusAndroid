package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Esta clase contiene todas las lecturas de la misa. Se usa como una propiedad de [Missae].
 *
 * @property lectionum Una lista de objetos [MissaeLectionum] con cada lectura.
 * @property type El tipo de lecturas (columna proveniente del modelo de datos). Sirve para decir a la clase padre cómo debe imprimir la vista.
 *
 * @author A. Cedano
 * @since 2022.1
 *
 * @see [Sortable]
 */
class MissaeLectionumList(
    var lectionum: ArrayList<MissaeLectionum?> = ArrayList<MissaeLectionum?>(),
    var type: Int = 0
) : Sortable {

    private val titulo: SpannableStringBuilder
        get() = Utils.toH3Red(Utils.toUpper(Constants.TITLE_MASS_READING))

    private val tituloForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_MASS_READING)

    fun getForView(): SpannableStringBuilder {
        sort()
        val sb = SpannableStringBuilder(Utils.LS2)
        try {
            sb.append(titulo)
            sb.append(Utils.LS2)
            lectionum.forEach {
                sb.append(it?.getAll(type))
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder(tituloForRead)
            try {
                lectionum.forEach {
                    sb.append(it?.getAllForRead(type))
                }
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }

    override fun sort() {
        lectionum.sortedBy { it!!.theOrder }
    }
}