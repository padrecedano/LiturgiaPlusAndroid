package org.deiverbum.app.data.db.dao;

import static org.deiverbum.app.utils.Constants.TODAY_TABLE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.deiverbum.app.data.entity.BibleHomilyJoinEntity;
import org.deiverbum.app.data.entity.BibleHomilyThemeEntity;
import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.EpigraphEntity;
import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHAntiphonEntity;
import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.data.entity.LHHymnEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHIntercessionsEntity;
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
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
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity;
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
import org.deiverbum.app.data.entity.relation.TodayComentarios;
import org.deiverbum.app.data.entity.relation.TodayCompletas;
import org.deiverbum.app.data.entity.relation.TodayHomilias;
import org.deiverbum.app.data.entity.relation.TodayLaudes;
import org.deiverbum.app.data.entity.relation.TodayMisaLecturas;
import org.deiverbum.app.data.entity.relation.TodayMixto;
import org.deiverbum.app.data.entity.relation.TodayNona;
import org.deiverbum.app.data.entity.relation.TodayOficio;
import org.deiverbum.app.data.entity.relation.TodaySanto;
import org.deiverbum.app.data.entity.relation.TodaySexta;
import org.deiverbum.app.data.entity.relation.TodayTercia;
import org.deiverbum.app.data.entity.relation.TodayVisperas;
import org.deiverbum.app.model.BibleHomilyJoin;
import org.deiverbum.app.model.BibleHomilyTheme;
import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHAntiphon;
import org.deiverbum.app.model.LHEpigraph;
import org.deiverbum.app.model.LHGospelCanticleTable;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHHymnJoin;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.LHIntercessionsJoin;
import org.deiverbum.app.model.LHInvitatoryJoin;
import org.deiverbum.app.model.LHOfficeBiblicalJoin;
import org.deiverbum.app.model.LHOfficeBiblicalTable;
import org.deiverbum.app.model.LHOfficePatristic;
import org.deiverbum.app.model.LHOfficePatristicJoin;
import org.deiverbum.app.model.LHOfficeVerse;
import org.deiverbum.app.model.LHOfficeVerseJoin;
import org.deiverbum.app.model.LHPrayer;
import org.deiverbum.app.model.LHPsalm;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.LHPsalmodyJoin;
import org.deiverbum.app.model.LHReadingShort;
import org.deiverbum.app.model.LHReadingShortJoin;
import org.deiverbum.app.model.LHResponsoryShort;
import org.deiverbum.app.model.LHResponsoryTable;
import org.deiverbum.app.model.LHTheme;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHomilyJoin;
import org.deiverbum.app.model.LiturgySaintJoin;
import org.deiverbum.app.model.MassReadingJoin;
import org.deiverbum.app.model.MassReadingTable;
import org.deiverbum.app.model.Pater;
import org.deiverbum.app.model.PaterOpus;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.SaintShortLife;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Dao
public interface TodayDao {

    String todayByDate = "SELECT * FROM today AS t WHERE t.todayDate =:theDate";
    String t=TODAY_TABLE;

    @Query("SELECT * FROM today WHERE todayDate =  :todayDate LIMIT 1")
    LiveData<Today> findByDate(Integer todayDate);

    @Query("SELECT * FROM sync_status")
    SyncStatus getAllSyncStatus();

    @Query("SELECT todayDate FROM today ORDER BY todayDate DESC LIMIT 1")
    Integer findLastDate();

    @Query("SELECT todayDate FROM today ORDER BY todayDate DESC LIMIT 1")
    Integer findLastToday();

    @Query("SELECT liturgyID FROM liturgy ORDER BY liturgyID DESC LIMIT 1")
    Integer findLastLiturgia();

    @Query("SELECT homilyID FROM homily ORDER BY homilyID DESC LIMIT 1")
    Integer findLastHomilia();

    @Insert(entity = TodayEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void insertAllTodays(List<Today> today);

    @Insert(entity = TodayEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void todayInsertAll(List<Today> today);

    @Insert(entity = LiturgyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgiaInsertAll(List<Liturgy> today);

    @Insert(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homiliaInsertAll(List<HomilyList> list);

    @Insert(entity = LHHymnEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void insertAllHimnos(List<LHHymn> today);

    @Insert(entity = TodayEntity.class)
    void insertTodayX(Today today);

    @Transaction
    @Query(todayByDate)
    TodayOficio getOficioOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayLaudes getLaudesOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayTercia getTerciaOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodaySexta getSextaOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayNona getNonaOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayMixto getMixtoOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayVisperas getVisperasOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayCompletas getCompletasOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayMisaLecturas getMisaLecturas(Integer theDate);

    //@Query(todayByDate)
    String sqlSaint = "SELECT * FROM saint AS s WHERE s.theMonth=:theMonth AND theDay =:theDay";
    @Transaction
    @Query(sqlSaint)
    TodaySanto getSantoOfToday(int theMonth, int theDay);

    @Transaction
    @Query(todayByDate)
    TodayHomilias getHomilias(Integer theDate);

    @Transaction
    @Query("SELECT ss.lastUpdate,ss.versionDB,"+
            "(SELECT max(todayDate) FROM today) tableName "+
    "FROM sync_status ss;")
    LiveData<SyncStatus> getSyncInfo();


    @Transaction
    /*@Query("SELECT * FROM today AS t " +
            "JOIN homilia h ON t.feriaFK=h.homiliaId JOIN misa_lectura ml ON t.mLecturasFK=ml.liturgiaFK " +
            "WHERE t.hoy =:theDate AND ml.orden >= 40")*/
    /*@Query("SELECT * FROM today AS t " +
            "JOIN mass_reading mr ON t.massReadingFK=mr.liturgyFK "+
            "JOIN bible_homily_join bhj ON bhj.readingFK=mr.readingFK "+
            "JOIN homily h ON bhj.homilyFK=h.homilyID "+
            "WHERE t.todayDate =:theDate AND mr.`theOrder` >= 40")*/
    @Query(todayByDate)

    TodayComentarios getComentarios(Integer theDate);

    @Insert(entity = LHReadingShortJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void biblicaBreveJoinInsertAll(List<LHReadingShortJoin> list);

    @Update(entity = LHAntiphonEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhAntifonaUpdateAll(List<LHAntiphon> list);

    @Update(entity = TodayEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void todayUpdateAll(List<Today> list);

    @Delete(entity = TodayEntity.class)
    void todayDeleteAll(List<Today> list);

    @Insert(entity = MassReadingEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void massReadingInsertAll(List<MassReadingTable> misaLectura);

    @Update(entity = MassReadingEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void massReadingUpdateAll(List<MassReadingTable> misaLectura);

    @Delete(entity = MassReadingEntity.class)
    void massReadingDeleteAll(List<MassReadingTable> misaLectura);

    @Insert(entity = MassReadingJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void massReadingJoinInsertAll(List<MassReadingJoin> c);

    @Update(entity = MassReadingJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void massReadingJoinUpdateAll(List<MassReadingJoin> u);

    @Delete(entity = MassReadingJoinEntity.class)
    void massReadingJoinDeleteAll(List<MassReadingJoin> d);

    @Insert(entity = BibleReadingEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void bibleReadingInsertAll(List<Biblical> bibleReading);
    @Update(entity = BibleReadingEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleReadingUpdateAll(List<Biblical> list);

    @Delete(entity = BibleReadingEntity.class)
    void bibleReadingDeleteAll(List<Biblical> modelList);


    @Query("SELECT lastUpdate FROM sync_status WHERE tableName=:tableName LIMIT 1")
    String syncLastVersion(String tableName);

    @Query("SELECT MAX(lastUpdate) FROM sync_status")
    LiveData<String> liveLastSync();

    @Query("SELECT MAX(todayDate) FROM today")
    LiveData<String> liveLastToday();

    @Query("SELECT MAX(lastUpdate) FROM sync_status")
    String getLastUpdate();

    @Update(entity = SyncStatusEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void syncUpdateAll(List<SyncStatus> list);

    @Insert(entity = LiturgyEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void liturgyInsertAll(List<Liturgy> c);

    @Update(entity = LiturgyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgyUpdateAll(List<Liturgy> u);

    @Delete(entity = LiturgyEntity.class)
    void liturgyDeleteAll(List<Liturgy> d);

    @Insert(entity = LiturgyHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgyHomilyJoinInsertAll(List<LiturgyHomilyJoin> list);

    @Update(entity = LiturgyHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgyHomilyJoinUpdateAll(List<LiturgyHomilyJoin> list);

    @Delete(entity = LiturgyHomilyJoinEntity.class)
    void liturgyHomilyJoinDeleteAll(List<LiturgyHomilyJoin> modelList);

    @Insert(entity = LiturgySaintJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgySaintJoinInsertAll(List<LiturgySaintJoin> list);

    @Update(entity = LiturgySaintJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgySaintJoinUpdateAll(List<LiturgySaintJoin> list);

    @Delete(entity = LiturgySaintJoinEntity.class)
    void liturgySaintJoinDeleteAll(List<LiturgySaintJoin> modelList);


    @Query("UPDATE sync_status SET lastUpdate=:lastUpdate")
    void syncUpdate(String lastUpdate);

    @Insert(entity = BibleHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyJoinInsertAll(List<BibleHomilyJoin> list);

    @Update(entity = BibleHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyJoinUpdateAll(List<BibleHomilyJoin> list);

    @Delete(entity = BibleHomilyJoinEntity.class)
    void bibleHomilyJoinDeleteAll(List<BibleHomilyJoin> modelList);

    @Insert(entity = BibleHomilyThemeEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyThemeInsertAll(List<BibleHomilyTheme> list);

    @Update(entity = BibleHomilyThemeEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyThemeUpdateAll(List<BibleHomilyTheme> list);

    @Delete(entity = BibleHomilyThemeEntity.class)
    void bibleHomilyThemeDeleteAll(List<BibleHomilyTheme> modelList);

    @Insert(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyInsertAll(List<Homily> list);

    @Update(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyUpdateAll(List<Homily> list);

    @Delete(entity = HomilyEntity.class)
    void homilyDeleteAll(List<Homily> modelList);

    @Insert(entity = LHInvitatoryJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhInvitatoryJoinInsertAll(List<LHInvitatoryJoin> c);

    @Update(entity = LHInvitatoryJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhInvitatoryJoinUpdateAll(List<LHInvitatoryJoin> u);

    @Delete(entity = LHInvitatoryJoinEntity.class)
    void lhInvitatoryJoinDeleteAll(List<LHInvitatoryJoin> d);

    @Insert(entity = SaintEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void saintInsertAll(List<Saint> c);

    @Update(entity = SaintEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void saintUpdateAll(List<Saint> u);

    @Delete(entity = SaintEntity.class)
    void saintDeleteAll(List<Saint> d);

    @Insert(entity = SaintShortLifeEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void saintShortLifeInsertAll(List<SaintShortLife> c);

    @Update(entity = SaintShortLifeEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void saintShortLifeUpdateAll(List<SaintShortLife> u);

    @Delete(entity = SaintShortLifeEntity.class)
    void saintShortLifeDeleteAll(List<SaintShortLife> d);

    @Insert(entity = SaintLifeEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void saintLifeInsertAll(List<SaintLife> c);

    @Update(entity = SaintLifeEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void saintLifeUpdateAll(List<SaintLife> u);

    @Delete(entity = SaintLifeEntity.class)
    void saintLifeDeleteAll(List<SaintLife> d);

    @Insert(entity = LHHymnJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhHymnJoinInsertAll(List<LHHymnJoin> c);

    @Update(entity = LHHymnJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhHymnJoinUpdateAll(List<LHHymnJoin> u);

    @Delete(entity = LHHymnJoinEntity.class)
    void lhHymnJoinDeleteAll(List<LHHymnJoin> d);

    @Insert(entity = LHOfficeVerseEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhOfficeVerseInsertAll(List<LHOfficeVerse> c);

    @Update(entity = LHOfficeVerseEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhOfficeVerseUpdateAll(List<LHOfficeVerse> u);

    @Delete(entity = LHOfficeVerseEntity.class)
    void lhOfficeVerseDeleteAll(List<LHOfficeVerse> d);

    @Insert(entity = LHOfficeVerseJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhOfficeVerseJoinInsertAll(List<LHOfficeVerseJoin> c);

    @Update(entity = LHOfficeVerseJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhOfficeVerseJoinUpdateAll(List<LHOfficeVerseJoin> u);

    @Delete(entity = LHOfficeVerseJoinEntity.class)
    void lhOfficeVerseJoinDeleteAll(List<LHOfficeVerseJoin> d);

    @Insert(entity = LHHymnEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhHymnInsertAll(List<LHHymn> c);

    @Update(entity = LHHymnEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhHymnUpdateAll(List<LHHymn> u);

    @Delete(entity = LHHymnEntity.class)
    void lhHymnDeleteAll(List<LHHymn> d);

    @Insert(entity = PaterEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void paterInsertAll(List<Pater> c);

    @Update(entity = PaterEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void paterUpdateAll(List<Pater> u);

    @Delete(entity = PaterEntity.class)
    void paterDeleteAll(List<Pater> d);

    @Insert(entity = PaterOpusEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void paterOpusInsertAll(List<PaterOpus> c);

    @Update(entity = PaterOpusEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void paterOpusUpdateAll(List<PaterOpus> u);

    @Delete(entity = PaterOpusEntity.class)
    void paterOpusDeleteAll(List<PaterOpus> d);


    @Insert(entity = LHPsalmodyEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhPsalmodyInsertAll(List<LHPsalmody> c);

    @Update(entity = LHPsalmodyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhPsalmodyUpdateAll(List<LHPsalmody> u);

    @Delete(entity = LHPsalmodyEntity.class)
    void lhPsalmodyDeleteAll(List<LHPsalmody> d);

    @Insert(entity = LHPsalmodyJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhPsalmodyJoinInsertAll(List<LHPsalmodyJoin> c);

    @Update(entity = LHPsalmodyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhPsalmodyJoinUpdateAll(List<LHPsalmodyJoin> u);

    @Delete(entity = LHPsalmodyJoinEntity.class)
    void lhPsalmodyJoinDeleteAll(List<LHPsalmodyJoin> d);

    @Insert(entity = LHAntiphonEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhAntiphonInsertAll(List<LHAntiphon> c);

    @Update(entity = LHAntiphonEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhAntiphonUpdateAll(List<LHAntiphon> u);

    @Delete(entity = LHAntiphonEntity.class)
    void lhAntiphonDeleteAll(List<LHAntiphon> d);

    @Insert(entity = LHThemeEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhThemeInsertAll(List<LHTheme> c);

    @Update(entity = LHThemeEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhThemeUpdateAll(List<LHTheme> u);

    @Delete(entity = LHThemeEntity.class)
    void lhThemeDeleteAll(List<LHTheme> d);

    @Insert(entity = EpigraphEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhEpigraphInsertAll(List<LHEpigraph> c);

    @Update(entity = EpigraphEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhEpigraphUpdateAll(List<LHEpigraph> u);

    @Delete(entity = EpigraphEntity.class)
    void lhEpigraphDeleteAll(List<LHEpigraph> d);

    @Insert(entity = PsalmEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhPsalmInsertAll(List<LHPsalm> c);

    @Update(entity = PsalmEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhPsalmUpdateAll(List<LHPsalm> u);

    @Delete(entity = PsalmEntity.class)
    void lhPsalmDeleteAll(List<LHPsalm> d);

    @Insert(entity = LHOfficeBiblicalEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhOfficeBiblicalInsertAll(List<LHOfficeBiblicalTable> c);

    @Update(entity = LHOfficeBiblicalEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhOfficeBiblicalUpdateAll(List<LHOfficeBiblicalTable> u);

    @Delete(entity = LHOfficeBiblicalEntity.class)
    void lhOfficeBiblicalDeleteAll(List<LHOfficeBiblicalTable> d);

    @Insert(entity = LHOfficeBiblicalJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhOfficeBiblicalJoinInsertAll(List<LHOfficeBiblicalJoin> c);

    @Update(entity = LHOfficeBiblicalJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhOfficeBiblicalJoinUpdateAll(List<LHOfficeBiblicalJoin> u);

    @Delete(entity = LHOfficeBiblicalJoinEntity.class)
    void lhOfficeBiblicalJoinDeleteAll(List<LHOfficeBiblicalJoin> d);

    @Insert(entity = LHResponsoryEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhResponsoryInsertAll(List<LHResponsoryTable> c);

    @Update(entity = LHResponsoryEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhResponsoryUpdateAll(List<LHResponsoryTable> u);

    @Delete(entity = LHResponsoryEntity.class)
    void lhResponsoryDeleteAll(List<LHResponsoryTable> d);

    @Insert(entity = LHOfficePatristicEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhOfficePatristicInsertAll(List<LHOfficePatristic> c);

    @Update(entity = LHOfficePatristicEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhOfficePatristicUpdateAll(List<LHOfficePatristic> u);

    @Delete(entity = LHOfficePatristicEntity.class)
    void lhOfficePatristicDeleteAll(List<LHOfficePatristic> d);

    @Insert(entity = LHOfficePatristicJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhOfficePatristicJoinInsertAll(List<LHOfficePatristicJoin> c);

    @Update(entity = LHOfficePatristicJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhOfficePatristicJoinUpdateAll(List<LHOfficePatristicJoin> u);

    @Delete(entity = LHOfficePatristicJoinEntity.class)
    void lhOfficePatristicJoinDeleteAll(List<LHOfficePatristicJoin> d);

    @Insert(entity = LHReadingShortEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhReadingShortInsertAll(List<LHReadingShort> c);

    @Update(entity = LHReadingShortEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhReadingShortUpdateAll(List<LHReadingShort> u);

    @Delete(entity = LHReadingShortEntity.class)
    void lhReadingShortDeleteAll(List<LHReadingShort> d);

    @Insert(entity = LHResponsoryShortEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhResponsoryShortInsertAll(List<LHResponsoryShort> c);

    @Update(entity = LHResponsoryShortEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhResponsoryShortUpdateAll(List<LHResponsoryShort> u);

    @Delete(entity = LHResponsoryShortEntity.class)
    void lhResponsoryShortDeleteAll(List<LHResponsoryShort> d);

    @Insert(entity = LHReadingShortJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhReadingShortJoinInsertAll(List<LHReadingShortJoin> c);

    @Update(entity = LHReadingShortJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhReadingShortJoinUpdateAll(List<LHReadingShortJoin> u);

    @Delete(entity = LHReadingShortJoinEntity.class)
    void lhReadingShortJoinDeleteAll(List<LHReadingShortJoin> d);

    @Insert(entity = LHGospelCanticleEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void gospelCanticleInsertAll(List<LHGospelCanticleTable> list);

    @Update(entity = LHGospelCanticleEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void gospelCanticleUpdateAll(List<LHGospelCanticleTable> list);

    @Delete(entity = LHGospelCanticleEntity.class)
    void gospelCanticleDeleteAll(List<LHGospelCanticleTable> modelList);

    @Insert(entity = LHIntercessionsEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhIntercessionsInsertAll(List<LHIntercession> list);

    @Update(entity = LHIntercessionsEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhIntercessionsUpdateAll(List<LHIntercession> list);

    @Delete(entity = LHIntercessionsEntity.class)
    void lhIntercessionsDeleteAll(List<LHIntercession> list);

    @Insert(entity = LHIntercessionsJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhIntercessionsJoinInsertAll(List<LHIntercessionsJoin> list);

    @Update(entity = LHIntercessionsJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhIntercessionsJoinUpdateAll(List<LHIntercessionsJoin> list);

    @Delete(entity = LHIntercessionsJoinEntity.class)
    void lhIntercessionsJoinDeleteAll(List<LHIntercessionsJoin> list);


    @Insert(entity = PrayerEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void prayerInsertAll(List<Prayer> c);

    @Update(entity = PrayerEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void prayerUpdateAll(List<Prayer> u);

    @Delete(entity = PrayerEntity.class)
    void prayerDeleteAll(List<Prayer> d);

    @Insert(entity = LHPrayerEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhPrayerInsertAll(List<LHPrayer> c);

    @Update(entity = LHPrayerEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhPrayerUpdateAll(List<LHPrayer> u);

    @Delete(entity = LHPrayerEntity.class)
    void lhPrayerDeleteAll(List<LHPrayer> d);


}

