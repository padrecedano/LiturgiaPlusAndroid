package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class LHOfficiumLectioAltera(
    @ColumnInfo(name = "source")
    var theSource: String? = null,
    @ColumnInfo(name = "theme")
    var tema: String? = null,
    @Ignore
    var homilia: String = "",
    /*@Ignore
    var ref: String? = null,*/
    @JvmField
    @Ignore
    var paterOpus: PaterOpus? = null,
    @Ignore
    var responsorium: LHResponsorium? = null,
    var theOrder: Int? = null
) {

    var groupFK: Int? = null
    var homilyFK: Int? = null
    var responsoryFK: Int? = null

    private val textForRead: Spanned
        get() = Utils.fromHtmlForRead(homilia)
    private val textoSpan: Spanned
        get() = Utils.fromHtml(homilia)
    val header: SpannableStringBuilder
        get() = Utils.formatTitle("SEGUNDA LECTURA")
    private val responsorioHeaderForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_RESPONSORY)
    private val headerForRead: String
        get() = "Segunda lectura."

    /**
     *
     * Obtiene la lectura patrística completa, incluyendo el responsorio, formateada para la vista.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    val allForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(headerForRead)
            sb.append(Utils.normalizeEnd(paterOpus!!.opusForView))
            if (tema != null && tema != "") {
                sb.append(Utils.normalizeEnd(tema!!))
            }
            sb.append(textForRead)
            sb.append(Utils.normalizeEnd(paterOpus!!.paterForView))
            sb.append(responsorioHeaderForRead)
            sb.append(responsorium?.allForRead)
            return sb
        }


    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(paterOpus?.opusForView)
            sb.append(Utils.LS2)
            sb.append(Utils.toSmallSizeRed(theSource))
            sb.append(Utils.LS2)
            sb.append(Utils.toRed(tema))
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            //sb.append(Utils.LS)
            //sb.append(responsorium!!.all)
            return sb
        }
}
