package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`db_table`** de la base de datos, que se ocupa de gestionar las versiones de la base de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.DB_TABLE)
data class DBTableEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tableID")
    val tableID: Int,

    @ColumnInfo(name = "tableName")
    val tableName: String
)