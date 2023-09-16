package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_intercessions_join`** de la base de datos, que se ocupa de gestionar las relaciones asociativas de los Himnos de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_INTERCESSIONS_JOIN,
    foreignKeys = [ForeignKey(
        entity = LHIntercessionsEntity::class,
        parentColumns = arrayOf("intercessionID"),
        childColumns = arrayOf("intercessionFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHIntercessionsJoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId: Int,

    @ColumnInfo(name = "intercessionFK", index = true)
    var precesFK: Int
)