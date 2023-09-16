package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_office_biblical`** de la base de datos, que se ocupa de gestionar las lecturas bíblicas del Oficio de Lectura de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_OFFICE_BIBLICAL,
    primaryKeys = ["groupFK", "theOrder"],
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
        entity = LHResponsoryEntity::class,
        parentColumns = arrayOf("responsoryID"),
        childColumns = arrayOf("responsoryFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHOfficeBiblicalEntity(
    @ColumnInfo(name = "groupFK")
    var groupFK: Int,

    @ColumnInfo(name = "readingFK", index = true)
    var readingFK: Int,

    @ColumnInfo(name = "responsoryFK", index = true)
    var responsoryFK: Int,

    @ColumnInfo(name = "theme")
    var theme: String,

    @ColumnInfo(name = "theOrder", defaultValue = "1")
    var theOrder: Int = 1
)