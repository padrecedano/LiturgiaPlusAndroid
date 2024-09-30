package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHAntiphonAssoc
import org.deiverbum.app.core.database.model.relation.LHHymnAssoc
import org.deiverbum.app.core.database.model.relation.LHPrayerAll
import org.deiverbum.app.core.database.model.relation.LHPsalmsAssoc
import org.deiverbum.app.core.database.model.relation.LHReadingShortAll
import org.deiverbum.app.core.database.model.relation.LHTertiamLocal
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.data.Breviarium
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource

/**
 * Representación de Tercia para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 * @see Breviarium
 * @see LHIntermedia
 */
data class TertiamExternal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "tHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "tBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "tAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "tPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "tPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll
)

fun LHTertiamLocal.asExternalModel() = Universalis(
    universalis.todayDate,
    //universalis.timeFK,
    Liturgy(
        liturgia.parent.semana,
        liturgia.parent.dia,
        liturgia.parent.nombre,
        liturgia.entity.asExternalModel(),
        LHIntermedia(
            hymnus.entity.asExternalModel(),
            LHPsalmody(
                psalmus.asExternalModel(),
                antiphonae.asExternalModel(),
                psalmus.join.theType
            ),
            lectioBrevis.asExternalModel(),
            oratio.asExternalModel(),
            4,
            "intermedia"
        )
    )
)

fun TertiamExternal.asExternalModel() = UniversalisResource(
    data =
    listOf(
        Universalis(
            universalis.todayDate,
            Liturgy(
                liturgia.parent.semana,
                liturgia.parent.dia,
                liturgia.parent.nombre,
                liturgia.entity.asExternalModel(),
                LHIntermedia(
                    hymnus.entity.asExternalModel(),
                    LHPsalmody(
                        psalmus.asExternalModel(),
                        antiphonae.asExternalModel(),
                        psalmus.join.theType
                    ),
                    lectioBrevis.asExternalModel(),
                    oratio.asExternalModel(),
                    4,
                    "intermedia"
                )
            )
        )
    )
)