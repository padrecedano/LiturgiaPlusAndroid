package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_office_patristic_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de las lecturas patrísticas del Oficio de Lectura de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_OFFICE_PATRISTIC_JOIN)
data class LHOfficePatristicJoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId: Int
)