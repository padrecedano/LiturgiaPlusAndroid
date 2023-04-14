package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

/**
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
class LiturgyGroupEntity {
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var groupID = 0

    @ColumnInfo(name = "liturgyFK")
    var liturgyFK = 0
}