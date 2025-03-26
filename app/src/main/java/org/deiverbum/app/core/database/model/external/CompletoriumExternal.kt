package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHNightPrayerAssoc
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.database.model.relation.asExternalModelPsalmodia
import org.deiverbum.app.core.model.breviarium.BreviariumCompletorium
import org.deiverbum.app.core.model.breviarium.ConclusioCompletorium
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis

/**
 * Representación de la hora de Completas para la capa de datos externa.
 *
 * @author A. Cedano
 * @since 2025.1
 * @see BreviariumCompletorium
 */
data class CompletoriumExternal(
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

fun CompletoriumExternal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val conclusion = ConclusioCompletorium(nightPrayer.virgin.virginEntity.asExternalModel())
    val breviarium = BreviariumCompletorium(
        nightPrayer.kyrie.entity.asExternalModel(),
        nightPrayer.hymnus.himno.asExternalModel(),
        nightPrayer.asExternalModelPsalmodia(),
        nightPrayer.lectioBrevis.asExternalModel(),
        nightPrayer.nuncDimitis.asExternalModel(7),
        nightPrayer.prayer.asExternalModel(),
        conclusion, "completorium"
    )
    extModel.liturgia =
        Liturgy(
            liturgia.parent.semana,
            liturgia.parent.dia,
            liturgia.parent.nombre,
            liturgia.entity.asExternalModel(),
            breviarium
        )
    return extModel
}