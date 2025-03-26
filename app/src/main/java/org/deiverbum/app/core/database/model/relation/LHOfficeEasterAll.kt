package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHEasterBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.database.model.entity.PrayerEntity
import org.deiverbum.app.core.model.breviarium.LHAntiphon
import org.deiverbum.app.core.model.breviarium.LHOfficeBiblicalEaster
import org.deiverbum.app.core.model.breviarium.LHPsalm
import org.deiverbum.app.core.model.liturgia.Oratio

/**
 *
 * Obtiene la lectura bíblica del Oficio de Lecturas
 * con su responsorio, usando la relación expresada en [LHOfficeBiblicalWithAll].
 * Las tablas que se relacionan aquí son **`lh_office_biblical_join`** (`groupID`)  y **`lh_office_biblical`** (`groupFK`).
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOfficeEasterAll(
    @Embedded
    var entity: LHEasterBiblicalEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var lectio: BibleReadingWithBook,

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    var antiphona: LHAntiphonEntity?,

    @Relation(
        parentColumn = "psalmFK",
        entityColumn = "psalmID",
        entity = LHPsalmEntity::class
    )
    var psalmus: LHPsalmEntity?,

    @Relation(
        parentColumn = "prayerFK",
        entityColumn = "prayerID",
        entity = PrayerEntity::class
    )
    var oratio: PrayerEntity?

)

fun LHOfficeEasterAll.asExternalModel() = LHOfficeBiblicalEaster(
    lectio.asExternalModelBook(),
    lectio.lectura.cita,
    lectio.lectura.texto,
    entity.theme,
    entity.theOrder
)

fun LHOfficeEasterAll.asExternalModelAntiphona() = LHAntiphon(
    antiphona!!.antiphonID,
    antiphona!!.antiphon,
    entity.theOrder
)

fun LHOfficeEasterAll.asExternalModelPsalmus() = LHPsalm(
    entity.theOrder, true,
    psalmus!!.salmoRef!!,
    psalmus!!.salmo
)

fun LHOfficeEasterAll.asExternalModelOratio() = Oratio(
    oratio!!.oratioId,
    oratio!!.texto,
    entity.theOrder

)
