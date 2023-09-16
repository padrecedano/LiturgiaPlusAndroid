package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_reading_short_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de las Lecturas Breves de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_READING_SHORT_JOIN,
    foreignKeys = [ForeignKey(
        entity = LHReadingShortEntity::class,
        parentColumns = arrayOf("readingID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHResponsoryShortEntity::class,
        parentColumns = arrayOf("responsoryID"),
        childColumns = arrayOf("responsoryFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHReadingShortJoinEntity (
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    val grupoId:Int,

    @ColumnInfo(name = "readingFK", index = true)
    val lecturaFK:Int,

    @ColumnInfo(name = "responsoryFK", index = true)
    val responsorioFK:Int
)