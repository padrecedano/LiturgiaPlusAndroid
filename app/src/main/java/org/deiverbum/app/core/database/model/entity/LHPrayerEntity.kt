package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_prayer`** de la base de datos, que se ocupa de gestionar las oraciones de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_PRAYER,
    indices = [Index(value = ["groupID", "prayerFK"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = PrayerEntity::class,
        parentColumns = arrayOf("prayerID"),
        childColumns = arrayOf("prayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHPrayerEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    val grupoId: Int,

    @ColumnInfo(name = "prayerFK", index = true)
    val oracionFK: Int
)
