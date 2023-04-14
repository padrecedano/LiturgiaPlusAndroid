package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

/**
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
class LiturgyEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "liturgyID")
    var liturgiaId = 0

    @JvmField
    @ColumnInfo(name = "timeFK")
    var tiempoFK = 0

    @JvmField
    @ColumnInfo(name = "typeFK")
    var tipoFK = 0

    @JvmField
    @ColumnInfo(name = "week")
    var semana = 0

    @JvmField
    @ColumnInfo(name = "day")
    var dia = 0

    @JvmField
    @ColumnInfo(name = "colorFK", index = true)
    var colorFK = 0

    @JvmField
    @ColumnInfo(name = "name")
    var nombre = ""
}