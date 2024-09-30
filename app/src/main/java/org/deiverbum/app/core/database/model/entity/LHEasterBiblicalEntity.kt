package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

/**
 * Entidad para la tabla **`lh_easter_biblical`** de la base de datos, que se ocupa de gestionar el Oficio de Lectura específico del día de Pascua.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = "lh_easter_biblical",
    primaryKeys = ["groupFK", "readingFK", "theOrder"],
    foreignKeys = [
        ForeignKey(
            entity = LHEasterBiblicalJoinEntity::class,
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
        ),
        ForeignKey(
            entity = LHAntiphonEntity::class,
            parentColumns = arrayOf("antiphonID"),
            childColumns = arrayOf("antiphonFK"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        ),

        ForeignKey(
            entity = LHPsalmEntity::class,
            parentColumns = arrayOf("psalmID"),
            childColumns = arrayOf("psalmFK"),
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
data class LHEasterBiblicalEntity(
    @ColumnInfo(name = "groupFK", index = true)
    var groupFK: Int,

    @ColumnInfo(name = "readingFK", index = true)
    var readingFK: Int,

    @ColumnInfo(name = "antiphonFK", index = true)
    var antiphonFK: Int?,

    @ColumnInfo(name = "psalmFK", index = true)
    var psalmFK: Int?,

    @ColumnInfo(name = "prayerFK", index = true)
    var prayerFK: Int?,

    @ColumnInfo(name = "theme")
    var theme: String,

    @ColumnInfo(name = "theOrder", defaultValue = "1", index = true)
    var theOrder: Int = 1
)