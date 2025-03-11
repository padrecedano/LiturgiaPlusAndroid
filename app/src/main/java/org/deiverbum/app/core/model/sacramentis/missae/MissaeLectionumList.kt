package org.deiverbum.app.core.model.data.missae

import android.text.SpannableStringBuilder
import com.squareup.moshi.Json
import org.deiverbum.app.core.model.data.Sortable
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
    var lectionum: MutableList<MissaeLectionum?> = mutableListOf(),
    var type: Int = 0
) : Sortable {


    @Json(ignore = true)
    private val tituloForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_MASS_READING)

    fun getForView(): SpannableStringBuilder {
        sort()
        val sb = SpannableStringBuilder()
        try {
            if (type == -1) {
                sb.append(Utils.LS)
                sb.append(Utils.formatTitle("EVANGELIO DE LA MISA"))
            }
            lectionum.forEach {
                sb.append(it?.getAll(type))
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }



    @Json(ignore = true)
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
        lectionum = lectionum.sortedBy { it!!.theOrder } as MutableList<MissaeLectionum?>
    }
}