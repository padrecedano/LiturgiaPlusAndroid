package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.alteri.AlteriSanctii
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
data class SanctiLocal(
    @Embedded
    var saint: SaintEntity,

    @Relation(entity = SaintLifeEntity::class, parentColumn = "saintID", entityColumn = "saintFK")
    var saintLife: SaintLifeEntity,
)

fun SanctiLocal.asExternalModel(): Universalis {
    val extModel = Universalis()
    val alteri =
        AlteriSanctii(
            saintLife.asExternalModel(saint.theMonth, saint.theDay, saint.theName),
            "sanctii"
            //LiturgyTime()
        )
    //extModel.liturgia = Liturgy(alteri, saint.theName, 11)
    return Universalis(
        Liturgy(saint.theName, alteri)
    )
}