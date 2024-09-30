package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_antiphon_join_entity`** de la base de datos, que se ocupa de gestionar la relación de las antífonas en la salmodia de la Liturgia de las Horas con las celebraciones correspondientes.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
@Entity(
    tableName = Constants.LH_ANTIPHON_JOIN,
    primaryKeys = ["groupFK", "antiphonFK"],
    foreignKeys =
    [
        ForeignKey(
            entity = LHPsalmodyJoinEntity::class,
            parentColumns = arrayOf("groupID"),
            childColumns = arrayOf("groupFK"),
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
data class LHAntiphonJoinEntity(
    @ColumnInfo(name = "groupFK", index = true)
    var grupoFK: Int,

    @ColumnInfo(name = "antiphonFK", index = true)
    var antiphonFK: Int,

    @ColumnInfo(name = "theOrder")
    var theOrder: Int
)
