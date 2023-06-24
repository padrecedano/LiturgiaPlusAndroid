package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.RESTRICT
import org.deiverbum.app.util.Constants

/**
 * Clase de entidad para Completas.
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
@Entity(
    tableName = Constants.LH_NIGHT_PRAYER,
    indices = [Index(
        value = ["kyrieFK","hymnFK","psalmodyFK","readingFK","nuncDimitisFK","prayerFK","virginFK"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = KyrieEntity::class,
        parentColumns = arrayOf("kyrieID"),
        childColumns = arrayOf("kyrieFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnEntity::class,
        parentColumns = arrayOf("hymnID"),
        childColumns = arrayOf("hymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("psalmodyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHGospelCanticleEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nuncDimitisFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = PrayerEntity::class,
        parentColumns = arrayOf("prayerID"),
        childColumns = arrayOf("prayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = VirginAntiphonEntity::class,
        parentColumns = arrayOf("antiphonID"),
        childColumns = arrayOf("virginFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
)
    ]
)
class NightPrayerEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var groupID = 0

    @JvmField
    @ColumnInfo(name = "kyrieFK", index = true)
    var kyrieFK = 0


    @JvmField
    @ColumnInfo(name = "hymnFK", index = true)
    var hymnFK = 0

    @JvmField
    @ColumnInfo(name = "psalmodyFK", index = true)
    var psalmodyFK = 0

    @JvmField
    @ColumnInfo(name = "readingFK", index = true)
    var readingFK = 0

    @JvmField
    @ColumnInfo(name = "nuncDimitisFK", index = true)
    var nuncDimitisFK = 0

    @JvmField
    @ColumnInfo(name = "prayerFK", index = true)
    var prayerFK = 0

    @JvmField
    @ColumnInfo(name = "virginFK", index = true)
    var virginFK = 0

}