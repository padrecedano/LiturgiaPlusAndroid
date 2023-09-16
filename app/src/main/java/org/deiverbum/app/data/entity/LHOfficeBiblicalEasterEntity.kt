package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.model.LHOfficeBiblicalEaster
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_office_biblical_easter`** de la base de datos, que se ocupa de gestionar el Oficio de Lectura específico del día de Pascua.
 *
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
data class LHOfficeBiblicalEasterEntity(
    @ColumnInfo(name = "groupFK", index = true)
    var groupFK: Int,

    @ColumnInfo(name = "readingFK", index = true)
    var readingFK: Int,

    @JvmField
    @ColumnInfo(name = "psalmodyFK", index = true)
    var psalmodyFK: Int,

    @JvmField
    @ColumnInfo(name = "prayerFK", index = true)
    var prayerFK: Int,

    @ColumnInfo(name = "theme")
    var theme: String,

    @ColumnInfo(name = "theOrder", defaultValue = "1")
    var theOrder: Int = 1
) {
    fun getDomainModel(): LHOfficeBiblicalEaster {
        val dm = LHOfficeBiblicalEaster()
        dm.theme = this.theme
        return dm
    }
}
