package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_OFFICE_BIBLICAL,
    primaryKeys = ["groupFK", "theOrder"],
    foreignKeys = [ForeignKey(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("groupFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = BibleReadingEntity::class,
        parentColumns = arrayOf("readingID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHResponsoryEntity::class,
        parentColumns = arrayOf("responsoryID"),
        childColumns = arrayOf("responsoryFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHOfficeBiblicalEntity {
    @JvmField
    @ColumnInfo(name = "groupFK")
    var groupFK = 0

    @JvmField
    @ColumnInfo(name = "readingFK", index = true)
    var readingFK = 0

    @JvmField
    @ColumnInfo(name = "responsoryFK", index = true)
    var responsoryFK = 0

    @JvmField
    @ColumnInfo(name = "theme")
    var theme = ""

    @JvmField
    @ColumnInfo(name = "theOrder", defaultValue = "1")
    var theOrder = 1
}