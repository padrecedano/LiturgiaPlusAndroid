package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_office_patristic`** de la base de datos, que se ocupa de gestionar las lecturas patrísticas del Oficio de Lectura de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_OFFICE_PATRISTIC,
    primaryKeys = ["groupFK", "theOrder"],
    foreignKeys = [ForeignKey(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("groupFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = HomilyEntity::class,
        parentColumns = arrayOf("homilyID"),
        childColumns = arrayOf("homilyFK"),
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
data class LHOfficePatristicEntity(
    @ColumnInfo(name = "groupFK", index = true)
    var grupoFK: Int,

    @ColumnInfo(name = "homilyFK", index = true)
    var homiliaFK: Int,

    @ColumnInfo(name = "theme")
    var tema: String,

    @ColumnInfo(name = "source", defaultValue = "")
    var fuente: String,

    @ColumnInfo(name = "theOrder", defaultValue = "1")
    var orden: Int,

    @ColumnInfo(name = "responsoryFK", index = true)
    var responsorioFK: Int
)