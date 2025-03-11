package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.liturgia.Liturgy

/**
 * Entidad para la tabla **`liturgy`** de la base de datos, que se ocupa de gestionar la Liturgia.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = "liturgy",
    indices = [Index(value = ["timeFK", "typeFK", "week", "day"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = LiturgyTimeEntity::class,
        parentColumns = arrayOf("timeID"),
        childColumns = arrayOf("timeFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LiturgyColorEntity::class,
        parentColumns = arrayOf("colorID"),
        childColumns = arrayOf("colorFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LiturgyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "liturgyID")
    val liturgiaId: Int,

    @ColumnInfo(name = "timeFK")
    val tiempoFK: Int,

    @ColumnInfo(name = "typeFK")
    val tipoFK: Int,

    @ColumnInfo(name = "week")
    var semana: Int,

    @ColumnInfo(name = "day")
    var dia: Int,

    @ColumnInfo(name = "colorFK", index = true)
    var colorFK: Int,

    @ColumnInfo(name = "name")
    var nombre: String

)

fun LiturgyEntity.asExternalModel() = Liturgy(
    semana,
    dia,
    colorFK,
    nombre,

    )
