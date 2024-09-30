package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`liturgy_saint_join`** de la base de datos, que se ocupa de gestionar la relación asociativa entre las tablas `liturgy` y `saint`.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LITURGY_SAINT_JOIN,
    //indices = [Index(value = ["liturgyFK","saintFK"], unique = true)],
    primaryKeys = ["liturgyFK", "saintFK"],

    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LiturgySaintJoinEntity(
    //@PrimaryKey
    @ColumnInfo(name = "liturgyFK")
    val liturgyFK: Int,

    @ColumnInfo(name = "saintFK", index = true)
    val saintFK: Int
)