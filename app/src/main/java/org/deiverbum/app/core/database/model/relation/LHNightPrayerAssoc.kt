package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHKyrieJoinEntity
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LHVirginAntiphonJoinEntity
import org.deiverbum.app.core.model.breviarium.LHAntiphon
import org.deiverbum.app.core.model.breviarium.LHPsalm
import org.deiverbum.app.core.model.breviarium.LHPsalmody

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHNightPrayerAssoc(
    @Embedded
    var nightPrayer: LHNightPrayerEntity,

    @Relation(entity = LHKyrieJoinEntity::class, parentColumn = "kyrieFK", entityColumn = "groupID")
    var kyrie: KyrieWithAll,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "hymnFK", entityColumn = "groupID")
    var hymnus: LHHymnWithAll,

    @Relation(
        entity = LHAntiphonJoinEntity::class,
        parentColumn = "antiphonFK",
        entityColumn = "groupFK"
    )
    var antiphonae: List<LHAntiphonWithAll>,

    @Relation(
        entity = LHPsalmJoinEntity::class,
        parentColumn = "psalmFK",
        entityColumn = "groupFK"
    )
    var psalmus: List<LHPsalmWithAll>,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "readingFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "nuncDimitisFK",
        entityColumn = "groupID"
    )
    var nuncDimitis: LHGospelCanticleWithAntiphon,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "prayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll,

    @Relation(
        entity = LHVirginAntiphonJoinEntity::class,
        parentColumn = "virginFK",
        entityColumn = "groupID"
    )
    var virgin: LHVirginAntiphonWithAll
)

fun LHNightPrayerAssoc.asExternalModelPsalmodia(): LHPsalmody {
    val lstAntiphonae: MutableList<LHAntiphon> = ArrayList()
    val lstPsalmus: MutableList<LHPsalm> = ArrayList()

    for (a in antiphonae) {
        lstAntiphonae.add(a.asExternalModel())
    }

    for (ps in psalmus) {
        lstPsalmus.add(ps.asExternalModel())
    }
    return LHPsalmody(lstPsalmus, lstAntiphonae, 0)
}
