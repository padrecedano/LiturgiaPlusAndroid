package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_gospel_canticle`** de la base de datos, que se ocupa de los cánticos evangélicos y sus antífonas.
 *
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
data class LHGospelCanticleEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId: Int,

    @ColumnInfo(name = "antiphonFK", index = true)
    var antifonaFK: Int
)

