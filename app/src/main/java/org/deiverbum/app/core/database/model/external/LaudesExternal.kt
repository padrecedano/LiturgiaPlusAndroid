package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHAntiphonAssoc
import org.deiverbum.app.core.database.model.relation.LHGospelCanticleWithAntiphon
import org.deiverbum.app.core.database.model.relation.LHHymnAssoc
import org.deiverbum.app.core.database.model.relation.LHIntercessionsWithAll
import org.deiverbum.app.core.database.model.relation.LHInvitatoryAll
import org.deiverbum.app.core.database.model.relation.LHPrayerAll
import org.deiverbum.app.core.database.model.relation.LHPsalmsAssoc
import org.deiverbum.app.core.database.model.relation.LHReadingShortAll
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.SaintShortWithAll
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.data.Breviarium
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis

/**
 * Representación de Laudes para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 * @see Breviarium
 * @see LHLaudes
 */
data class LaudesExternal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = LiturgySaintJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var sanctus: SaintShortWithAll?,

    @Relation(
        entity = LHInvitatoryJoinEntity::class,
        parentColumn = "invitatoryFK",
        entityColumn = "groupID"
    )
    var invitatorium: LHInvitatoryAll,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "lBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "lIntercessionsFK",
        entityColumn = "groupID"
    )
    var preces: LHIntercessionsWithAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "lPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "lCanticumFK",
        entityColumn = "groupID"
    )
    var canticumEvangelicum: LHGospelCanticleWithAntiphon
)

fun LaudesExternal.asExternalModel(): Universalis {
    val breviarium = LHLaudes(
        universalis.hasSaint == 1,
        invitatorium.asExternalModel(),
        hymnus.entity.asExternalModel(),
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel(), psalmus.join.theType),
        lectioBrevis.asExternalModel(),
        canticumEvangelicum.asExternalModel(2),
        preces.entity.asExternalModel(),
        oratio.asExternalModel(),
        "laudes"
    )
    if (universalis.hasSaint == 1) {
        breviarium.sanctus = sanctus!!.asExternalModel()
    }
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