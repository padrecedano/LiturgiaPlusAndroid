package org.deiverbum.app.core.model.data.crud

import com.squareup.moshi.JsonClass

/**
 *
 * Representa la relación asociativa de los salmos en la capa de datos externa.
 *
 *  @property groupFKNew el identificador del grupo.
 *  @property antiphonFKNew el identificador de la antífona.
 *  @property theOrderNew el orden en la antífona.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 * @see LHPsalmodyJoin
 *
 */
@JsonClass(generateAdapter = true)
data class LHAntiphonJoinCrud(
    var groupFKNew: Int,
    var antiphonFKNew: Int,
    var theOrderNew: Int,

    var groupFKOld: Int,
    var antiphonFKOld: Int,
    var theOrderOld: Int,

    )
