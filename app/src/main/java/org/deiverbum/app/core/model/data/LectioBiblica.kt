package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.ColumnInfo
import androidx.room.Ignore
import java.util.Locale

open class LectioBiblica(
    @ColumnInfo(name = "quote")
    open var pericopa: String = "",
    @ColumnInfo(name = "text")
    open var biblica: String = "",
) {

    /*constructor(theOrder: Int, tema: String, quote: String, text: String) : this(quote, text) {
        this.theOrder = theOrder
        this.tema = tema
    }*/

    constructor(book: BibleBook, pericopa: String, biblica: String, theOrder: Int) : this(
        pericopa,
        biblica
    ) {
        this.book = book
        this.theOrder = theOrder
    }

    constructor(
        book: BibleBook,
        pericopa: String,
        biblica: String,
        tema: String,
        theOrder: Int
    ) : this(
        pericopa,
        biblica
    ) {
        this.book = book
        this.theOrder = theOrder
        this.tema = tema
    }
    /*
        constructor(theOrder: Int, tema: String, book: BibleBook) : this(tema) {
            this.book = book
            this.theOrder = theOrder
        }*/

    constructor(book: BibleBook, pericopa: String, biblica: String) : this(pericopa, biblica) {
        this.book = book
    }

    constructor(tema: String, theOrder: Int) : this() {
        this.tema = tema
        this.theOrder = theOrder
    }

    @Ignore
    open lateinit var book: BibleBook

    @Ignore
    open var theOrder: Int = 0
    @Ignore
    open var tema: String = ""

    var bookFK = 0

    var verseChapter: String = ""

    var verseFrom: String = ""
    var verseTo: String = ""

    var readingID: Int? = null


    /*val textoSpan: Spanned
        get() = Utils.fromHtml(biblica)
    val textoForRead: Spanned
        get() = Utils.fromHtml(
            Utils.getFormatoForRead(
                biblica
            )
        )*/

    fun setCita(ref: String) {
        this.pericopa = ref
    }

    fun getRefBreve(): String {
        return pericopa
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
        /*sb.append(getHeader())
        //sb.append(Utils.formatTitle(getHeader(type)))

        sb.append(Utils.LS2)
        sb.append(book.liturgyName)
        sb.append("    ")
        //sb.append(Utils.toRed(verseChapter))
        //sb.append(", ")
        //sb.append(Utils.toRed(verseFrom))
        sb.append(Utils.toRed(pericopa))
        sb.append(Utils.LS2)
        //sb.append(Utils.toRed(getTema()));
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(biblica))
        sb.append(Utils.LS)
        //sb.append(responsorio.getAll());*/
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
        /*sb.append(Utils.pointAtEnd(getHeader()))
        sb.append(book.getForRead())
        sb.append(textoForRead)
        sb.append(getConclusionForRead())
        sb.append(getResponsorioHeaderForRead())*/
        return sb
    }

    @Deprecated("Cambiar por propiedad")
    open fun getOrden(): Int? {
        return theOrder
    }

    @Deprecated("Cambiar por propiedad")
    open fun setOrden(theOrder: Int) {
        //theOrder = theOrder
    }


}