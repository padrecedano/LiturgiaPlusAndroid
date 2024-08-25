package org.deiverbum.app.core.model.data

import com.squareup.moshi.JsonClass

/**
 *
 * Maneja las lecturas bíblicas y sus comentarios.
 *
 *  @property biblica un objeto [MissaeLectionum] con la lectura.
 *  @property homiliae una lista de objetos [Homily] con los comentarios.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 * @see MissaeLectionum
 * @see Homily
 * @see Commentarii
 *
 */


@JsonClass(generateAdapter = true)
data class BiblicaWithComments(
    var biblica: MissaeLectionum,
) {
    var homiliae: MutableList<Homily?> = ArrayList()

    constructor(
        biblica: MissaeLectionum,
        homiliae: MutableList<Homily?> = ArrayList(),
    ) : this(biblica) {
        this.homiliae = homiliae
    }

    fun sort() {
        //homiliae=homiliae.sortedBy { it.theOrder } as MutableList<LHOfficiumLectioPrior>
        //lectioAltera=lectioAltera.sortedBy { it.theOrder } as MutableList<LHOfficiumLectioAltera>
        homiliae = homiliae.sortedWith(
            compareBy({ it!!.paterOpus.pater!!.typeFK },
                { it!!.date },
                { it!!.book },
                { it!!.chapter },
                { it!!.colParagraph },
                { it!!.colNumber })
        ) as MutableList<Homily?>

    }
}