package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`saint`** de la base de datos, que se ocupa de gestionar los Santos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.SAINT,
    indices = [Index(value = ["theName", "theMonth", "theDay"], unique = true)]
)
data class SaintEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saintID")
    val santoId: Int,

    @ColumnInfo(name = "theName")
    val theName: String,

    @ColumnInfo(name = "theMonth")
    val theMonth: Int,

    @ColumnInfo(name = "theDay")
    val theDay: Int,

    @ColumnInfo(name = "typeFK", defaultValue = "0")
    val tipoId: Int,

    @ColumnInfo(name = "commonFK", defaultValue = "0")
    var comunId: Int
)