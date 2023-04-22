package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.Ignore
import org.deiverbum.app.utils.Utils
import java.util.*

open class Biblical {
     var bookFK = 0

    @Ignore var book: BibleBook? = null

     var verseChapter: String? = null

     var text: String? = null


    //getReferencia();
     var quote: String? = ""

    //getReferencia();
    @Ignore var order: Int? = null
     var readingID: Int? = null
     var verseFrom: String? = null
     var verseTo: String? = null




    val textoSpan: Spanned
        get() = Utils.fromHtml(text)
    val textoForRead: Spanned
        get() = Utils.fromHtml(
            Utils.getFormatoForRead(
                text
            )
        )

    fun setCita(ref: String?) {
        this.quote = ref
    }

    fun getRefBreve(): String? {
        return if (quote != null) quote else ""
    }

    fun getReferencia(): String {
        return String.format(Locale("es"), "%s, %s%s", verseChapter, verseFrom, verseTo)
    }

    open fun getHeader(): String? {
        return "PRIMERA LECTURA"
    }

    fun getConclusionForRead(): String {
        return "Palabra de Dios."
    }

    open fun getResponsorioHeaderForRead(): String? {
        return "LHResponsoryShort."
    }

    /**
     *
     * Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    open fun getAll(): SpannableStringBuilder? {
        val sb = SpannableStringBuilder()
        sb.append(getHeader())
        sb.append(Utils.LS2)
        sb.append(book!!.liturgyName)
        sb.append("    ")
        sb.append(Utils.toRed(verseChapter))
        sb.append(", ")
        sb.append(Utils.toRed(verseFrom))
        sb.append(Utils.toRed(verseTo))
        sb.append(Utils.LS2)
        //sb.append(Utils.toRed(getTema()));
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        sb.append(Utils.LS)
        //sb.append(responsorio.getAll());
        return sb
    }

    /**
     *
     * Obtiene la lectura bíblica completa formateada para la lectura de voz.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    open fun getAllForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.pointAtEnd(getHeader()))
        sb.append(book!!.getForRead())
        sb.append(textoForRead)
        sb.append(getConclusionForRead())
        sb.append(getResponsorioHeaderForRead())
        return sb
    }

    open fun getOrden(): Int? {
        return order
    }

    open fun setOrden(orden: Int?) {
        order = orden
    }

    fun getLecturaId(): Int? {
        return readingID
    }
}