package org.deiverbum.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.database.dao.UniversalisDao
import org.deiverbum.app.core.database.model.UIGroupEntity
import org.deiverbum.app.core.database.model.UITopicEntity
import org.deiverbum.app.core.database.model.entity.BibleBookEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyThemeEntity
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.DBTableEntity
import org.deiverbum.app.core.database.model.entity.EpigraphEntity
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.KyrieEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LHEasterBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHEasterBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHKyrieJoinEntity
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryShortEntity
import org.deiverbum.app.core.database.model.entity.LHThemeEntity
import org.deiverbum.app.core.database.model.entity.LHVirginAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyColorEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyGroupEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyTimeEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingJoinEntity
import org.deiverbum.app.core.database.model.entity.PaterEntity
import org.deiverbum.app.core.database.model.entity.PaterOpusEntity
import org.deiverbum.app.core.database.model.entity.PrayerEntity
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.database.model.entity.SaintShortLifeEntity
import org.deiverbum.app.core.database.model.entity.SyncStatusEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.VirginAntiphonEntity
import org.deiverbum.app.core.database.util.RoomConverter


/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Database(
    entities = [
        LHEasterBiblicalJoinEntity::class, LHEasterBiblicalEntity::class, UniversalisEntity::class, LiturgyEntity::class, LiturgyTimeEntity::class, LiturgySaintJoinEntity::class, SaintEntity::class, SaintLifeEntity::class, SaintShortLifeEntity::class, LHInvitatoryEntity::class, LHInvitatoryJoinEntity::class, LHHymnEntity::class, LHHymnJoinEntity::class, LHPsalmodyJoinEntity::class, LHPsalmJoinEntity::class, LHPsalmEntity::class, LHAntiphonJoinEntity::class, LHAntiphonEntity::class, LHThemeEntity::class, EpigraphEntity::class, LHOfficeVerseEntity::class, LHOfficeVerseJoinEntity::class, LHOfficeBiblicalJoinEntity::class, LHOfficeBiblicalEntity::class, LHOfficePatristicEntity::class, LHOfficePatristicJoinEntity::class, HomilyEntity::class, PaterEntity::class, PaterOpusEntity::class, LHResponsoryEntity::class, LHResponsoryShortEntity::class, BibleBookEntity::class, BibleReadingEntity::class, LHReadingShortEntity::class, LHReadingShortJoinEntity::class, LHGospelCanticleEntity::class, LHIntercessionsEntity::class, LHIntercessionsJoinEntity::class, PrayerEntity::class, LHPrayerEntity::class, LiturgyHomilyJoinEntity::class, BibleHomilyJoinEntity::class, BibleHomilyThemeEntity::class, LiturgyGroupEntity::class, LiturgyColorEntity::class, MassReadingEntity::class, MassReadingJoinEntity::class, SyncStatusEntity::class, DBTableEntity::class,
        KyrieEntity::class, LHKyrieJoinEntity::class, VirginAntiphonEntity::class, LHVirginAntiphonJoinEntity::class, LHNightPrayerEntity::class,
        UITopicEntity::class, UIGroupEntity::class
    ],
    version = 2
)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todayDao(): TodayDao
    abstract fun universalisDaoo(): UniversalisDao

}

