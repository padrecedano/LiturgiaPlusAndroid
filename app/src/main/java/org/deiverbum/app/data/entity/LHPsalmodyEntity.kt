package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.SET_NULL
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_PSALMODY,
    primaryKeys = ["groupFK", "readingFK", "antiphonFK"],
    foreignKeys = [ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("groupFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = PsalmEntity::class,
        parentColumns = arrayOf("psalmID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHAntiphonEntity::class,
        parentColumns = arrayOf("antiphonID"),
        childColumns = arrayOf("antiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHThemeEntity::class,
        parentColumns = arrayOf("themeID"),
        childColumns = arrayOf("themeFK"),
        onDelete = SET_NULL,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = EpigraphEntity::class,
        parentColumns = arrayOf("epigraphID"),
        childColumns = arrayOf("epigraphFK"),
        onDelete = SET_NULL,
        onUpdate = CASCADE
    )]
)
class LHPsalmodyEntity {
    @JvmField
    @ColumnInfo(name = "groupFK")
    var grupoFK = 0

    @JvmField
    @ColumnInfo(name = "readingFK", index = true)
    var salmoFK = 0

    @JvmField
    @ColumnInfo(name = "order")
    var orden = 0

    @JvmField
    @ColumnInfo(name = "antiphonFK", defaultValue = "0", index = true)
    var antifonaId: Int = 0

    @JvmField
    @ColumnInfo(name = "themeFK", defaultValue = "NULL", index = true)
    var temaFK: Int? = null

    @JvmField
    @ColumnInfo(name = "epigraphFK", defaultValue = "NULL", index = true)
    var epigrafeFK: Int? = null

    @JvmField
    @ColumnInfo(name = "part", defaultValue = "NULL")
    var parte: Int? = 0
    fun getParte(): String {
        return if (parte == null || parte == 0) "" else parte.toString()
    }
}