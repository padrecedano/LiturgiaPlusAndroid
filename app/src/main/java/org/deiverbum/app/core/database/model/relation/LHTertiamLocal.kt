package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.breviarium.BreviariumIntermedia
import org.deiverbum.app.core.model.data.breviarium.LHPsalmody
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis

/**
 * Obtiene el contenido de la hora **`Tercia`** desde la base de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHTertiamLocal(
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
        BreviariumIntermedia(
            hymnus.entity.asExternalModel(),
            LHPsalmody(
                psalmus.asExternalModel(),
                antiphonae.asExternalModel(),
                psalmus.join.theType
            ),
            lectioBrevis.asExternalModel(),
            oratio.asExternalModel(),
            3,
            "intermedia"
        )
    )
)
