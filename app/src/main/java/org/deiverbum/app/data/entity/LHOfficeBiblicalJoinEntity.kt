package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_office_biblical_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de las lecturas bíblicas del Oficio de Lectura de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_OFFICE_BIBLICAL_JOIN)
class LHOfficeBiblicalJoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId: Int
)