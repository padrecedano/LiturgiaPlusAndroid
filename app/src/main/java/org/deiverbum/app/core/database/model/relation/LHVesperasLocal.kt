package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.breviarium.BreviariumVesperas
import org.deiverbum.app.core.model.breviarium.LHPsalmody
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
data class LHVesperasLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var primaVesperas: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "vBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "vIntercessionsFK",
        entityColumn = "groupID"
    )
    var preces: LHIntercessionsWithAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "vPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "vCanticumFK",
        entityColumn = "groupID"
    )
    var canticumEvangelicum: LHGospelCanticleWithAntiphon
)

fun LHVesperasLocal.asExternalModel(): Universalis {
    val liturgiaAssoc: LiturgyTimeAssoc
    var isPrimaVesperas = false
    if (universalis.previousFK > 1) {
        liturgiaAssoc = primaVesperas
        isPrimaVesperas = true
    } else {
        liturgiaAssoc = liturgia
    }

    return Universalis(
        universalis.todayDate,
        //universalis.timeFK,
        Liturgy(
            liturgiaAssoc.parent.semana,
            liturgiaAssoc.parent.dia,
            liturgiaAssoc.parent.nombre,
            liturgiaAssoc.entity.asExternalModel(),
            BreviariumVesperas(
                universalis.hasSaint == 1,
                hymnus.entity.asExternalModel(),
                LHPsalmody(
                    psalmus.asExternalModel(),
                    antiphonae.asExternalModel(),
                    psalmus.join.theType
                ),
                lectioBrevis.asExternalModel(),
                canticumEvangelicum.asExternalModel(6),
                preces.entity.asExternalModel(),
                oratio.asExternalModel(),
                isPrimaVesperas,
                "vesperas"
            )
        )
    )
}