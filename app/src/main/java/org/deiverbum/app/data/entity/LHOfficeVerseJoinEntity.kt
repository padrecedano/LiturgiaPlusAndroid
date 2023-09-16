package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_office_verse_join`** de la base de datos, que se ocupa de gestionar la relación asociativa del versículo inicial del Oficio de Lectura de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_OFFICE_VERSE_JOIN,
    foreignKeys = [ForeignKey(
        entity = LHOfficeVerseEntity::class,
        parentColumns = arrayOf("verseID"),
        childColumns = arrayOf("verseFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHOfficeVerseJoinEntity (
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    val grupoId:Int,

    @ColumnInfo(name = "verseFK", index = true)
    val responsorioFK:Int
)