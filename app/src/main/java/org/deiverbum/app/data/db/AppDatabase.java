package org.deiverbum.app.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.AntiphonEntity;
import org.deiverbum.app.data.entity.BibleHomilyJoinEntity;
import org.deiverbum.app.data.entity.BibleHomilyThemeEntity;
import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.BiblieBookEntity;
import org.deiverbum.app.data.entity.EpigraphEntity;
import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHHymnEntity;
import org.deiverbum.app.data.entity.LHInvitatoryEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.LHOficceVerseJoinEntity;
import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.LHReadingShortEntity;
import org.deiverbum.app.data.entity.LHOficceVerseEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity;
import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicJoinEntity;
import org.deiverbum.app.data.entity.LHIntercessionsEntity;
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity;
import org.deiverbum.app.data.entity.LHResponsoryShortEntity;
import org.deiverbum.app.data.entity.LHResponsoryEntity;
import org.deiverbum.app.data.entity.LHThemeEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.data.entity.LiturgiaTiempoEntity;
import org.deiverbum.app.data.entity.LiturgyColorEntity;
import org.deiverbum.app.data.entity.LiturgyGroupEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.PaterEntity;
import org.deiverbum.app.data.entity.PaterOpusEntity;
import org.deiverbum.app.data.entity.PrayerEntity;
import org.deiverbum.app.data.entity.PsalmEntity;
import org.deiverbum.app.data.entity.PsalmodyEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.Today;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
@Database(entities =
        {
                Today.class,
                //TodayTest.class,
                LiturgyEntity.class,
                LiturgiaTiempoEntity.class,
                SaintEntity.class,
                SaintLifeEntity.class,
                LHInvitatoryEntity.class,
                LHInvitatoryJoinEntity.class,
                LHHymnEntity.class,
                LHHymnJoinEntity.class,
                PsalmodyEntity.class,
                LHPsalmodyJoinEntity.class,
                PsalmEntity.class,
                AntiphonEntity.class,
                LHThemeEntity.class,
                EpigraphEntity.class,
                LHOficceVerseEntity.class,
                LHOficceVerseJoinEntity.class,
                LHOfficeBiblicalJoinEntity.class,

                //LHPatristicaEntity.class,
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
                LHOfficeBiblicalEntity.class,
                LHGospelCanticleEntity.class,
                LHIntercessionsEntity.class,
                LHIntercessionsJoinEntity.class,
                PrayerEntity.class,
                LHPrayerEntity.class,
                //MisaLecturaEntity.class,
                //LHSantoEntity.class,
                LiturgyHomilyJoinEntity.class,
                BibleHomilyJoinEntity.class,
                BibleHomilyThemeEntity.class,
                LiturgyGroupEntity.class,
                LiturgyColorEntity.class,
                MassReadingEntity.class

                },
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodayDao todayDao();
}

