package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.utils.Constants

/**
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
class LHReadingShortJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0

    @JvmField
    @ColumnInfo(name = "readingFK", index = true)
    var lecturaFK = 0

    @JvmField
    @ColumnInfo(name = "responsoryFK", index = true)
    var responsorioFK = 0
}