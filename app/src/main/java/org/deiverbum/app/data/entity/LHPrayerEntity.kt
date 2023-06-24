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
class LHPrayerEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0

    @JvmField
    @ColumnInfo(name = "prayerFK", index = true)
    var oracionFK = 0
}