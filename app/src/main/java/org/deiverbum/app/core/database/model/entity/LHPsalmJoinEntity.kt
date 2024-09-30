package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.SET_NULL
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_psalm_join`** de la base de datos, que se ocupa de gestionar la salmodia de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
@Entity(
    tableName = Constants.LH_PSALM_JOIN,
    primaryKeys = ["groupFK", "readingFK"],
    foreignKeys =
    [
        ForeignKey(
            entity = LHPsalmodyJoinEntity::class,
            parentColumns = arrayOf("groupID"),
            childColumns = arrayOf("groupFK"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        ),
        ForeignKey(
            entity = LHPsalmEntity::class,
            parentColumns = arrayOf("psalmID"),
            childColumns = arrayOf("readingFK"),
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
data class LHPsalmJoinEntity(
    @ColumnInfo(name = "groupFK", index = true)
    var groupFK: Int,

    @ColumnInfo(name = "readingFK", index = true)
    var readingFK: Int,

    @ColumnInfo(name = "theOrder")
    var theOrder: Int,

    @ColumnInfo(name = "themeFK", defaultValue = "NULL", index = true)
    var themeFK: Int?,

    @ColumnInfo(name = "epigraphFK", defaultValue = "NULL", index = true)
    var epigraphFK: Int?,

    @ColumnInfo(name = "thePart", defaultValue = "NULL")
    var thePart: Int?
)

