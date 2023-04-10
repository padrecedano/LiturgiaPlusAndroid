package org.deiverbum.app.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.BibleHomilyJoinEntity;
import org.deiverbum.app.data.entity.BibleHomilyThemeEntity;
import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.BiblieBookEntity;
import org.deiverbum.app.data.entity.DBTableEntity;
import org.deiverbum.app.data.entity.EpigraphEntity;
import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHAntiphonEntity;
import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.data.entity.LHHymnEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHIntercessionsEntity;
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity;
import org.deiverbum.app.data.entity.LHInvitatoryEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalEasterEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicJoinEntity;
import org.deiverbum.app.data.entity.LHOfficeVerseEntity;
import org.deiverbum.app.data.entity.LHOfficeVerseJoinEntity;
import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.LHPsalmodyEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LHReadingShortEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LHResponsoryEntity;
import org.deiverbum.app.data.entity.LHResponsoryShortEntity;
import org.deiverbum.app.data.entity.LHThemeEntity;
import org.deiverbum.app.data.entity.LiturgyColorEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyGroupEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity;
import org.deiverbum.app.data.entity.LiturgyTimeEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.MassReadingJoinEntity;
import org.deiverbum.app.data.entity.PaterEntity;
import org.deiverbum.app.data.entity.PaterOpusEntity;
import org.deiverbum.app.data.entity.PrayerEntity;
import org.deiverbum.app.data.entity.PsalmEntity;
import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
import org.deiverbum.app.data.entity.SaintShortLifeEntity;
import org.deiverbum.app.data.entity.SyncStatusEntity;
import org.deiverbum.app.data.entity.TodayEntity;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Database(entities =
        {
                TodayEntity.class,
                LiturgyEntity.class,
                LiturgyTimeEntity.class,
                LiturgySaintJoinEntity.class,
                SaintEntity.class,
                SaintLifeEntity.class,
                SaintShortLifeEntity.class,
                LHInvitatoryEntity.class,
                LHInvitatoryJoinEntity.class,
                LHHymnEntity.class,
                LHHymnJoinEntity.class,
                LHPsalmodyEntity.class,
                LHPsalmodyJoinEntity.class,
                PsalmEntity.class,
                LHAntiphonEntity.class,
                LHThemeEntity.class,
                EpigraphEntity.class,
                LHOfficeVerseEntity.class,
                LHOfficeVerseJoinEntity.class,
                LHOfficeBiblicalJoinEntity.class,
                LHOfficeBiblicalEntity.class,
                LHOfficeBiblicalEasterEntity.class,
                LHOfficePatristicEntity.class,
                LHOfficePatristicJoinEntity.class,

                HomilyEntity.class,
                PaterEntity.class,
                PaterOpusEntity.class,
                LHResponsoryEntity.class,
                LHResponsoryShortEntity.class,
                BiblieBookEntity.class,
                BibleReadingEntity.class,
                LHReadingShortEntity.class,
                LHReadingShortJoinEntity.class,
                LHGospelCanticleEntity.class,
                LHIntercessionsEntity.class,
                LHIntercessionsJoinEntity.class,
                PrayerEntity.class,
                LHPrayerEntity.class,
                LiturgyHomilyJoinEntity.class,
                BibleHomilyJoinEntity.class,
                BibleHomilyThemeEntity.class,
                LiturgyGroupEntity.class,
                LiturgyColorEntity.class,
                MassReadingEntity.class,
                MassReadingJoinEntity.class,
                SyncStatusEntity.class,
                DBTableEntity.class


        },
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodayDao todayDao();
}

