package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.squareup.moshi.Json
import org.deiverbum.app.core.designsystem.theme.NiaTypography
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
    private val titulo: SpannableStringBuilder
        get() {
            return if (type == -1) {
                Utils.formatTitle("EVANGELIO")
            } else {
                Utils.toH3Red(Utils.toUpper(Constants.TITLE_MASS_READING))
            }
        }

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

    fun getComposable(rubricColor: Color): AnnotatedString {
        //sort()
        return try {
            buildAnnotatedString {
                if (type == -1) {
                    append(Utils.LS)
                    withStyle(
                        SpanStyle(
                            fontSize = NiaTypography.titleMedium.fontSize,
                            color = rubricColor
                        )
                    ) {
                        append(Constants.TITLE_MASS_GOSPEL)
                    }
                }
                lectionum.forEach {
                    append(it?.theOrder.toString())
                    append(it?.getComposable(type, rubricColor))
                }
            }
        } catch (e: Exception) {
            buildAnnotatedString {
                append(Utils.createErrorMessage(e.message))
            }
        }
        //return sb
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