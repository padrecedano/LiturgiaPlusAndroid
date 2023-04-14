package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.utils.Constants

/**
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
class MassReadingJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "liturgyFK")
    var liturgyFK = 0

    @JvmField
    @ColumnInfo(name = "type")
    var type = 0
}