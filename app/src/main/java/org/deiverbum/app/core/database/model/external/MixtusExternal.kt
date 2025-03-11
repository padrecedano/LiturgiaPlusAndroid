package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHAntiphonAssoc
import org.deiverbum.app.core.database.model.relation.LHGospelCanticleWithAntiphon
import org.deiverbum.app.core.database.model.relation.LHHymnAssoc
import org.deiverbum.app.core.database.model.relation.LHIntercessionsWithAll
import org.deiverbum.app.core.database.model.relation.LHInvitatoryAll
import org.deiverbum.app.core.database.model.relation.LHOfficeBiblicalAssoc
import org.deiverbum.app.core.database.model.relation.LHOfficePatristicAssoc
import org.deiverbum.app.core.database.model.relation.LHOficceVerseAll
import org.deiverbum.app.core.database.model.relation.LHPrayerAll
import org.deiverbum.app.core.database.model.relation.LHPsalmsAssoc
import org.deiverbum.app.core.database.model.relation.LHReadingShortAll
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.MassReadingWithAll
import org.deiverbum.app.core.database.model.relation.SaintShortWithAll
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.data.breviarium.Breviarium
import org.deiverbum.app.core.model.data.breviarium.BreviariumMixtus
import org.deiverbum.app.core.model.data.breviarium.LHOfficiumLectionis
import org.deiverbum.app.core.model.data.breviarium.LHPsalmody
import org.deiverbum.app.core.model.data.missae.MissaeLectionum
import org.deiverbum.app.core.model.data.missae.MissaeLectionumList
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.util.Constants

/**
 * Representación de Mixto para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 * @see Breviarium
 * @see BreviariumMixtus
 */
data class MixtusExternal(
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
    var canticumEvangelicum: LHGospelCanticleWithAntiphon,

    @Relation(
        entity = LHOfficeVerseJoinEntity::class,
        parentColumn = "oVerseFK",
        entityColumn = "groupID"
    )
    var officeVerse: LHOficceVerseAll,

    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioPrima: LHOfficeBiblicalAssoc,

    @Relation(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupID"
    )
    var lectioAltera: LHOfficePatristicAssoc,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var missaeLectionum: List<MassReadingWithAll>,
)

fun MixtusExternal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    if (universalis.oBiblicalFK == Constants.EASTER_CODE) {
        extModel.oBiblicalFK = universalis.oBiblicalFK
        return extModel
    }
    val evangelii: MutableList<MissaeLectionum?> = ArrayList()
    for (item in missaeLectionum) {
        if (item.entity.orden >= 40) {
            evangelii.add(item.asExternalModel())
        }
    }
    val breviarium = BreviariumMixtus(
        universalis.hasSaint == 1,
        invitatorium.asExternalModel(),
        hymnus.entity.asExternalModel(),
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel(), psalmus.join.theType),
        lectioBrevis.asExternalModel(),
        LHOfficiumLectionis(
            lectioPrima.asExternalModel(),
            lectioAltera.asExternalModel(),
            officeVerse.theEntity.verse,
            universalis.oTeDeum == 1
        ),
        canticumEvangelicum.asExternalModel(2),
        MissaeLectionumList(evangelii, -1),
        preces.entity.asExternalModel(),
        oratio.asExternalModel(),
        "mixtus"
    )
    if (universalis.hasSaint == 1) {
        breviarium.sanctus = sanctus!!.asExternalModel()
    }

    extModel.liturgia = Liturgy(
        liturgia.parent.semana,
        liturgia.parent.dia,
        liturgia.parent.nombre,
        liturgia.entity.asExternalModel(),
        breviarium
    )
    return extModel
}
