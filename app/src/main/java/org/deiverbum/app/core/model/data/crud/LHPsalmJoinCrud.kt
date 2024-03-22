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
data class LHPsalmJoinCrud(
    var groupFKNew: Int,
    var readingFKNew: Int,
    var theOrderNew: Int,
    var themeFKNew: Int?,
    var epigraphFKNew: Int?,
    var thePartNew: Int?,

    var groupFKOld: Int,
    var readingFKOld: Int,
    var theOrderOld: Int,
    var themeFKOld: Int?,
    var epigraphFKOld: Int?,
    var thePartOld: Int?,
)
