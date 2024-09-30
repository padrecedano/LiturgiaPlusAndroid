package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.SET_NULL
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_psalmody`** de la base de datos, que se ocupa de gestionar la salmodia de la Liturgia de las Horas.
 *
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
        entity = LHPsalmEntity::class,
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
data class LHPsalmodyEntity(
    @ColumnInfo(name = "groupFK")
    var grupoFK: Int,

    @ColumnInfo(name = "readingFK", index = true)
    var salmoFK: Int,

    @ColumnInfo(name = "order")
    var orden: Int,

    @ColumnInfo(name = "antiphonFK", defaultValue = "0", index = true)
    var antifonaId: Int,

    @ColumnInfo(name = "themeFK", defaultValue = "NULL", index = true)
    var temaFK: Int?,

    @ColumnInfo(name = "epigraphFK", defaultValue = "NULL", index = true)
    var epigrafeFK: Int?,

    @ColumnInfo(name = "part", defaultValue = "NULL")
    var parte: Int?
)

