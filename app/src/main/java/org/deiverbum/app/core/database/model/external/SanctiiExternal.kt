package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.AlteriSanctii
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis

/**
 * Representación de la vida de los Santos para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class SanctiiExternal(
    @Embedded
    var saint: SaintEntity,

    @Relation(entity = SaintLifeEntity::class, parentColumn = "saintID", entityColumn = "saintFK")
    var saintLife: SaintLifeEntity,
)

fun SanctiiExternal.asExternalModel(): Universalis {
    val alteri =
        AlteriSanctii(
            saintLife.asExternalModel(saint.theMonth, saint.theDay, saint.theName),
            "sanctii"
        )
    return Universalis(
        Liturgy(saint.theName, alteri)
    )

}