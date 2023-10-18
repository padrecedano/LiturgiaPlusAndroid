package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
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
@JsonClass(generateAdapter = true)
class MissaeLectionumList(
    //var lectionum= mutableListOf<MissaeLectionum?>(),
    val lectionum: MutableList<MissaeLectionum?> = mutableListOf<MissaeLectionum?>(),

    var type: Int = 0
) : Sortable {

    @Json(ignore = true)
    private val titulo: SpannableStringBuilder
        get() = Utils.toH3Red(Utils.toUpper(Constants.TITLE_MASS_READING))

    @Json(ignore = true)
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
        lectionum.sortedBy { it!!.theOrder }
    }
}