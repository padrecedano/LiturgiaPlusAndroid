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
    tableName = Constants.BIBLE_HOMILY_JOIN,
    primaryKeys = ["readingFK", "homilyFK"],
    foreignKeys = [ForeignKey(
        entity = BibleReadingEntity::class,
        parentColumns = arrayOf("readingID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = HomilyEntity::class,
        parentColumns = arrayOf("homilyID"),
        childColumns = arrayOf("homilyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class BibleHomilyJoinEntity {
    @JvmField
    @ColumnInfo(name = "readingFK")
    var readingFK = 0

    @JvmField
    @ColumnInfo(name = "homilyFK", index = true)
    var homilyFK = 0
}