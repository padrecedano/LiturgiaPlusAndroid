package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHAntiphonAssoc
import org.deiverbum.app.core.database.model.relation.LHHymnAssoc
import org.deiverbum.app.core.database.model.relation.LHInvitatoryAll
import org.deiverbum.app.core.database.model.relation.LHOfficeBiblicalAssoc
import org.deiverbum.app.core.database.model.relation.LHOfficePatristicAssoc
import org.deiverbum.app.core.database.model.relation.LHOficceVerseAll
import org.deiverbum.app.core.database.model.relation.LHPrayerAll
import org.deiverbum.app.core.database.model.relation.LHPsalmsAssoc
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.SaintShortWithAll
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.breviarium.Breviarium
import org.deiverbum.app.core.model.breviarium.BreviariumOfficium
import org.deiverbum.app.core.model.breviarium.LHOfficiumLectionis
import org.deiverbum.app.core.model.breviarium.LHPsalmody
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.util.Constants

/**
 * Representación del Oficio para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 * @see Breviarium
 * @see BreviariumOfficium
 */
data class OfficiumExternal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "oHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

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
    var invitatorio: LHInvitatoryAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

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

    @Relation(entity = LHPrayerEntity::class, parentColumn = "oPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll
)

fun OfficiumExternal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    if (universalis.oBiblicalFK == Constants.EASTER_CODE) {
        extModel.oBiblicalFK = universalis.oBiblicalFK
        return extModel
    }
    val psalmodia =
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel(), psalmus.join.theType)
    val breviarium = BreviariumOfficium(
        universalis.hasSaint == 1,
        invitatorio.asExternalModel(),
        hymnus.entity.asExternalModel(),
        psalmodia,
        LHOfficiumLectionis(
            lectioPrima.asExternalModel(),
            lectioAltera.asExternalModel(),
            officeVerse.theEntity.verse,
            universalis.oTeDeum == 1
        ),
        oratio.asExternalModel(),
        "officium"
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