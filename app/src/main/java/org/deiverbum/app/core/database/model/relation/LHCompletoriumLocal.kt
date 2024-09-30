package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.ConclusioCompletorium
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Deprecated("Desde 2024.1.", ReplaceWith("PopulatedCompletoriumResource"))

data class LHCompletoriumLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = LHNightPrayerEntity::class,
        parentColumn = "nightPrayerFK",
        entityColumn = "groupID"
    )
    var nightPrayer: LHNightPrayerAssoc,

    )

fun PopulatedCompletoriumResource.asExternalModel(): Universalis {
    val conclusion = ConclusioCompletorium(nightPrayer.virgin.virginEntity.asExternalModel())
    //conclusion.setAntifonaVirgen(nightPrayer.virgin.virginEntity.antiphon)
    val breviarium = LHCompletorium(
        nightPrayer.kyrie.entity.asExternalModel(),
        nightPrayer.hymnus.himno.asExternalModel(),
        nightPrayer.asExternalModelPsalmodia(),
        nightPrayer.lectioBrevis.asExternalModel(),
        nightPrayer.nuncDimitis.asExternalModel(7),
        nightPrayer.prayer.asExternalModel(),
        conclusion, "completorium"
    )

    return Universalis(
        universalis.todayDate,
        //universalis.timeFK,
        Liturgy(
            liturgia.parent.semana,
            liturgia.parent.dia,
            liturgia.parent.nombre,
            liturgia.entity.asExternalModel(),
            breviarium
        )
    )
}
