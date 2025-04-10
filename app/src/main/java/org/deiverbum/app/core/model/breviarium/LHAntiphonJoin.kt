package org.deiverbum.app.core.model.breviarium

import com.squareup.moshi.JsonClass

/**
 *
 * Representa la relación asociativa de los salmos en la capa de datos externa.
 *
 *  @property groupFK el identificador del grupo.
 *  @property antiphonFK el identificador de la antífona.
 *  @property theOrder el orden en la antífona.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 * @see LHPsalmodyJoin
 *
 */
@JsonClass(generateAdapter = true)
data class LHAntiphonJoin(
    var groupFK: Int,
    var antiphonFK: Int,
    var theOrder: Int
)
