package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_invitatory_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de los Invitatorios de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_INVITATORY_JOIN,
    foreignKeys = [ForeignKey(
        entity = LHInvitatoryEntity::class,
        parentColumns = arrayOf("caseID"),
        childColumns = arrayOf("caseFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHAntiphonEntity::class,
        parentColumns = arrayOf("antiphonID"),
        childColumns = arrayOf("antiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHInvitatoryJoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId: Int,

    @ColumnInfo(name = "antiphonFK", index = true)
    var salmoFK: Int,

    @ColumnInfo(name = "caseFK", index = true)
    var casoFK: Int
)