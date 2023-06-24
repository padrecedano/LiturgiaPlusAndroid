package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LITURGY_SAINT_JOIN,
    indices = [Index(value = ["saintFK"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LiturgySaintJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "liturgyFK")
    var liturgyFK = 0

    @JvmField
    @ColumnInfo(name = "saintFK")
    var saintFK = 0
}