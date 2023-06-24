package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_GOSPEL_CANTICLE,
    foreignKeys = [ForeignKey(
        entity = LHAntiphonEntity::class,
        parentColumns = arrayOf("antiphonID"),
        childColumns = arrayOf("antiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHGospelCanticleEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0

    @JvmField
    @ColumnInfo(name = "antiphonFK", index = true)
    var antifonaFK = 0
}