package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class NightPrayerWithAll {
    @Embedded
    var nightPrayer: LHNightPrayerEntity? = null

    @Relation(entity = LHKyrieJoinEntity::class, parentColumn = "kyrieFK", entityColumn = "groupID")
    var kyrie: KyrieWithAll? = null

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "hymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll? = null

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "psalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "readingFK",
        entityColumn = "groupID"
    )
    var readingFK: LHReadingShortAll? = null

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "nuncDimitisFK",
        entityColumn = "groupID"
    )
    var nuncDimitisFK: LHGospelCanticleWithAntiphon? = null

    @Relation(entity = LHPrayerEntity::class, parentColumn = "prayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll? = null

    @Relation(
        entity = LHVirginAntiphonJoinEntity::class,
        parentColumn = "virginFK",
        entityColumn = "groupID"
    )
    var virgin: LHVirginAntiphonWithAll? = null
}