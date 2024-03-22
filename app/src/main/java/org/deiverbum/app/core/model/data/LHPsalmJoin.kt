package org.deiverbum.app.core.model.data

import com.squareup.moshi.JsonClass

/**
 *
 * Representa la relación asociativa de los salmos en la capa de datos externa.
 *
 *  @property groupFK el identificador del grupo.
 *  @property readingFK el identificador del salmo.
 *  @property theOrder el orden en la salmodia.
 *  @property themeFK el identificador del tema.
 *  @property epigraphFK el identificador del epígrafe.
 *  @property thePart La parte.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 * @see LHPsalmodyJoin
 *
 */
@JsonClass(generateAdapter = true)
data class LHPsalmJoin(
    val groupFK: Int,
    val readingFK: Int,
    val theOrder: Int,
    val themeFK: Int? = null,
    val epigraphFK: Int? = null,
    val thePart: Int? = null,
)
