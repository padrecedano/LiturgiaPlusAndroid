package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad para la tabla **`liturgy_group`** de la base de datos, que se ocupa de gestionar los grupos de Liturgia.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = "liturgy_group",
    indices = [Index(value = ["liturgyFK"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LiturgyGroupEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    val groupID: Int,

    @ColumnInfo(name = "liturgyFK")
    val liturgyFK: Int
)