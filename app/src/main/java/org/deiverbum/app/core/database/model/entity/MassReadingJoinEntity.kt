package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`mass_reading_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de las lecturas de la misa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.MASS_READING_JOIN,
    indices = [Index(value = ["liturgyFK", "type"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class MassReadingJoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "liturgyFK")
    val liturgyFK: Int,

    @ColumnInfo(name = "type")
    val type: Int
)