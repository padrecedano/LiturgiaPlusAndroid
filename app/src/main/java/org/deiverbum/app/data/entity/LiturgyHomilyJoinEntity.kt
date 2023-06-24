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
    tableName = Constants.LITURGY_HOMILY_JOIN,
    primaryKeys = ["liturgyFK", "homilyFK"],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
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
class LiturgyHomilyJoinEntity {
    @JvmField
    @ColumnInfo(name = "liturgyFK")
    var liturgiaFK = 0

    @JvmField
    @ColumnInfo(name = "homilyFK", index = true)
    var homiliaFK = 0

    @JvmField
    @ColumnInfo(name = "theme", defaultValue = "")
    var tema = ""
}