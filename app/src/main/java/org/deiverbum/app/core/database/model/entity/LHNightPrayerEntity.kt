package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Clase de entidad para Completas.
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
@Entity(
    tableName = Constants.LH_NIGHT_PRAYER,
    indices = [Index(
        value = ["kyrieFK", "hymnFK", "antiphonFK", "psalmFK", "readingFK", "nuncDimitisFK", "prayerFK", "virginFK"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = LHKyrieJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("kyrieFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("hymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("antiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("psalmFK"),
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
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("prayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHVirginAntiphonJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("virginFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )
    ]
)
class LHNightPrayerEntity {
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var groupID = 0

    @ColumnInfo(name = "kyrieFK", index = true)
    var kyrieFK = 0


    @ColumnInfo(name = "hymnFK", index = true)
    var hymnFK = 0

    @ColumnInfo(name = "antiphonFK", index = true)
    var antiphonFK = 0

    @ColumnInfo(name = "psalmFK", index = true)
    var psalmFK = 0

    @ColumnInfo(name = "readingFK", index = true)
    var readingFK = 0

    @ColumnInfo(name = "nuncDimitisFK", index = true)
    var nuncDimitisFK = 0

    @ColumnInfo(name = "prayerFK", index = true)
    var prayerFK = 0

    @ColumnInfo(name = "virginFK", index = true)
    var virginFK = 0

}