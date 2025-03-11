package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.breviarium.VirginAntiphon
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`virgin_antiphon`** de la base de datos, que se ocupa de gestionar las antífonas de la Virgen para Completas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
@Entity(tableName = Constants.VIRGIN_ANTIPHON)
data class VirginAntiphonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "antiphonID")
    val antiphonID: Int,

    @ColumnInfo(name = "antiphon")
    val antiphon: String
)

fun VirginAntiphonEntity.asExternalModel() = VirginAntiphon(
    antiphonID = antiphonID,
    antiphon = antiphon
)