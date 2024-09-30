package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_invitatory`** de la base de datos, que se ocupa de gestionar los Invitatorios de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_INVITATORY,
    foreignKeys = [ForeignKey(
        entity = LHPsalmEntity::class,
        parentColumns = arrayOf("psalmID"),
        childColumns = arrayOf("psalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHInvitatoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "caseID")
    var casoId: Int,

    @ColumnInfo(name = "psalmFK", index = true)
    var salmoFK: Int
)