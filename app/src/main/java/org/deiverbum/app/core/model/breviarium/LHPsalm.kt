package org.deiverbum.app.core.model.data.breviarium

import androidx.room.ColumnInfo
import androidx.room.Ignore

/**
 * Representa un salmo para la capa de datos externa.
 * @property theOrder El orden del salmo en la salmodia
 * @property pericopa La referencia bíblica
 * @property theme El tema (puede ser nulo)
 * @property epigraph El texto del epígrafe (puede ser nulo)
 * @property thePart El valor para la parte del salmo (puede ser nulo)
 * @property psalmus El texto del salmo
 *
 */
open class LHPsalm(
    @Ignore var theOrder: Int = 0,
    @Ignore var haveGloria: Boolean = true,

    @ColumnInfo(name = "quote")
    var pericopa: String = "",
    @ColumnInfo(name = "psalm")
    var psalmus: String = "",
) {
    constructor(
        theOrder: Int = 0,
        haveGloria: Boolean = true,
        pericopa: String = "",
        theme: String = "",
        epigraph: String = "",
        thePart: Int = 0,
        psalmus: String
    ) : this(theOrder, haveGloria, pericopa, psalmus) {
        this.theme = theme
        this.epigraph = epigraph
        this.thePart = thePart
    }

    constructor(
        theOrder: Int = 0,
        haveGloria: Boolean = true,
        pericopa: String = "",
        theme: String = "",
        psalmus: String
    ) : this(theOrder, haveGloria, pericopa, psalmus) {
        this.theme = theme
        //this.haveGloria=true
    }

    init {
        if (this.pericopa.contains("¦")) {
            this.pericopa = this.pericopa.replace("¦", "  ")
        }
        if (psalmus.endsWith("∸")) {
            this.psalmus = psalmus.replace("∸", "")
            haveGloria = false
            //return NormalizeText(psalmus.replace("∸",""),true)
        }
    }

    @Ignore
    var theme: String? = null

    @Ignore
    var epigraph: String? = null

    @Ignore
    var thePart: Int? = null

    var psalmID = 0
    var readingID = 0

    @Deprecated("")
    @Ignore
    var lhAntiphon: LHAntiphon? = null

}