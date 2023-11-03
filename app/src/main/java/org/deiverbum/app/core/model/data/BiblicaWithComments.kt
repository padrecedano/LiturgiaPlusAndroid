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
 * @since 2023.1.3
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

}