package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.model.LHOfficeBiblicalEaster
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_OFFICE_BIBLICAL_EASTER,
    primaryKeys = ["groupFK", "readingFK", "psalmodyFK", "prayerFK"],
    foreignKeys = [ForeignKey(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("groupFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = BibleReadingEntity::class,
        parentColumns = arrayOf("readingID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("psalmodyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = PrayerEntity::class,
        parentColumns = arrayOf("prayerID"),
        childColumns = arrayOf("prayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHOfficeBiblicalEasterEntity {
    @JvmField
    @ColumnInfo(name = "groupFK", index = true)
    var groupFK = 0

    @JvmField
    @ColumnInfo(name = "readingFK", index = true)
    var readingFK = 0

    @JvmField
    @ColumnInfo(name = "psalmodyFK", index = true)
    var psalmodyFK = 0

    @JvmField
    @ColumnInfo(name = "prayerFK", index = true)
    var prayerFK = 0

    @JvmField
    @ColumnInfo(name = "theme")
    var theme = ""

    @JvmField
    @ColumnInfo(name = "theOrder", defaultValue = "1")
    var theOrder = 1
    val domainModel: LHOfficeBiblicalEaster
        get() {
            val dm = LHOfficeBiblicalEaster()
            dm.theme = theme
            return dm
        }
}