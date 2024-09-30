package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_office_verse`** de la base de datos, que se ocupa de gestionar el versículo inicial del Oficio de Lectura de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_OFFICE_VERSE, indices = [Index(value = ["verse"], unique = true)])
data class LHOfficeVerseEntity(
    @PrimaryKey
    @ColumnInfo(name = "verseID")
    val versoId: Int,

    @ColumnInfo(name = "verse")
    val verse: String
)