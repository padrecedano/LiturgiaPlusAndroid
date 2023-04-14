package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_OFFICE_PATRISTIC,
    primaryKeys = ["groupFK", "theOrder"],
    foreignKeys = [ForeignKey(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("groupFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = HomilyEntity::class,
        parentColumns = arrayOf("homilyID"),
        childColumns = arrayOf("homilyFK"),
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
class LHOfficePatristicEntity {
    @JvmField
    @ColumnInfo(name = "groupFK", index = true)
    var grupoFK = 0

    @JvmField
    @ColumnInfo(name = "homilyFK", index = true)
    var homiliaFK = 0

    @JvmField
    @ColumnInfo(name = "theme")
    var tema = ""

    @JvmField
    @ColumnInfo(name = "source", defaultValue = "")
    var fuente = ""

    @JvmField
    @ColumnInfo(name = "theOrder", defaultValue = "1")
    var orden = 0

    @JvmField
    @ColumnInfo(name = "responsoryFK", index = true)
    var responsorioFK = 0
}