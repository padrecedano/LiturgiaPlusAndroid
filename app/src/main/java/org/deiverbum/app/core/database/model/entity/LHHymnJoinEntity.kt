package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_hymn_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de los Himnos de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_HYMN_JOIN,
    indices = [Index(value = ["groupID", "hymnFK"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = LHHymnEntity::class,
        parentColumns = arrayOf("hymnID"),
        childColumns = arrayOf("hymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHHymnJoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId: Int,

    @ColumnInfo(name = "hymnFK", index = true)
    var himnoFK: Int
)