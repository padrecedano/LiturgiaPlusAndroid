package org.deiverbum.app.core.database.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad para la tabla **`lh_easter_biblical_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de la salmodia de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = "lh_easter_biblical_join",
    indices = [Index(value = ["groupID"], unique = true)]
)
data class LHEasterBiblicalJoinEntity(
    @PrimaryKey
    val groupID: Int
)