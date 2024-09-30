package org.deiverbum.app.core.database.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_psalmody_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de la salmodia de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_PSALMODY_JOIN,
    indices = [Index(value = ["groupID", "theType"], unique = true)]
)
data class LHPsalmodyJoinEntity(
    @PrimaryKey
    val groupID: Int,

    val theType: Int
)