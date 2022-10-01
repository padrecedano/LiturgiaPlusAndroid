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

import org.deiverbum.app.data.entity.AntiphonEntity;
import org.deiverbum.app.data.entity.BibleHomilyJoinEntity;
import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHGospelCanticleJoinEntity;
import org.deiverbum.app.data.entity.LHHymnEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.LHOficceVerseJoinEntity;
import org.deiverbum.app.data.entity.LHPsalmodyEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.PaterOpusEntity;
import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
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
import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHAntiphon;
import org.deiverbum.app.model.LHGospelCanticleJoin;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHHymnJoin;
import org.deiverbum.app.model.LHInvitatoryJoin;
import org.deiverbum.app.model.LHOfficeVerseJoin;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.LHReadingShortJoin;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHomilyJoin;
import org.deiverbum.app.model.MassReadingOLD;
import org.deiverbum.app.model.PaterOpus;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
@Dao
public interface TodayDao {

    String todayByDate = "SELECT * FROM today AS t WHERE t.todayDate =:theDate";
String t=TODAY_TABLE;

    @Query("SELECT * FROM today WHERE todayDate =  :todayDate LIMIT 1")
    LiveData<Today> findByDate(Integer todayDate);

    @Query("SELECT * FROM sync_status")
    List<SyncStatus> getAllSyncStatus();

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

    @Transaction
    @Query(todayByDate)
    TodaySanto getSantoOfToday(Integer theDate);

    @Transaction
    @Query("SELECT * FROM today AS t " +
            "JOIN `homily` h ON t.liturgyFK=h.homilyID " +
            "WHERE t.todayDate =:theDate")
    TodayHomilias getHomilias(Integer theDate);

    @Transaction
    @Query("SELECT ss.lastUpdate,"+
            "(SELECT max(todayDate) FROM today) tableName "+
    "FROM sync_status ss;")
    LiveData<SyncStatus> getSyncInfo();


    @Transaction
    /*@Query("SELECT * FROM today AS t " +
            "JOIN homilia h ON t.feriaFK=h.homiliaId JOIN misa_lectura ml ON t.mLecturasFK=ml.liturgiaFK " +
            "WHERE t.hoy =:theDate AND ml.orden >= 40")*/
    @Query("SELECT * FROM today AS t " +
            "JOIN mass_reading mr ON t.massReadingFK=mr.liturgyFK "+
            "JOIN bible_homily_join bhj ON bhj.readingFK=mr.readingFK "+
            "JOIN homily h ON bhj.homilyFK=h.homilyID "+
            "WHERE t.todayDate =:theDate AND mr.`theOrder` >= 40")
    TodayComentarios getComentarios(Integer theDate);

    @Insert(entity = LHReadingShortJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void biblicaBreveJoinInsertAll(List<LHReadingShortJoin> list);

    @Update(entity = AntiphonEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhAntifonaUpdateAll(List<LHAntiphon> list);

    @Update(entity = TodayEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void todayUpdateAll(List<Today> list);

    @Delete(entity = TodayEntity.class)
    void todayDeleteAll(List<Today> list);

    @Update(entity = MassReadingEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void massReadingUpdateAll(List<MassReadingOLD> misaLectura);

    @Insert(entity = MassReadingEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void massReadingInsertAll(List<MassReadingOLD> misaLectura);

    @Delete(entity = MassReadingEntity.class)
    void massReadingDeleteAll(List<MassReadingOLD> misaLectura);

    @Insert(entity = BibleReadingEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void bibleReadingInsertAll(List<Biblical> bibleReading);
    @Update(entity = BibleReadingEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleReadingUpdateAll(List<Biblical> list);

    @Delete(entity = BibleReadingEntity.class)
    void bibleReadingDeleteAll(List<Biblical> homilyJoin);


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

    @Insert(entity = LiturgyHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyJoinInsertAll(List<LiturgyHomilyJoin> list);

    @Update(entity = LiturgyHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyJoinUpdateAll(List<LiturgyHomilyJoin> list);

    @Delete(entity = LiturgyHomilyJoinEntity.class)
    void homilyJoinDeleteAll(List<LiturgyHomilyJoin> homilyJoin);

    @Query("UPDATE sync_status SET lastUpdate=:lastUpdate")
    void syncUpdate(String lastUpdate);

    @Insert(entity = BibleHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyJoinInsertAll(List<BibleHomilyJoin> list);

    @Update(entity = BibleHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyJoinUpdateAll(List<BibleHomilyJoin> list);

    @Delete(entity = BibleHomilyJoinEntity.class)
    void bibleHomilyJoinDeleteAll(List<BibleHomilyJoin> homilyJoin);

    @Insert(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyInsertAll(List<Homily> list);

    @Update(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyUpdateAll(List<Homily> list);

    @Delete(entity = HomilyEntity.class)
    void homilyDeleteAll(List<Homily> homilyJoin);

    @Insert(entity = LHGospelCanticleJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void gospelCanticleInsertAll(List<LHGospelCanticleJoin> list);

    @Update(entity = LHGospelCanticleJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void gospelCanticleUpdateAll(List<LHGospelCanticleJoin> list);

    @Delete(entity = LHGospelCanticleJoinEntity.class)
    void gospelCanticleDeleteAll(List<LHGospelCanticleJoin> homilyJoin);

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

    @Insert(entity = LHOficceVerseJoinEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhOfficeVerseJoinInsertAll(List<LHOfficeVerseJoin> c);

    @Update(entity = LHOficceVerseJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhOfficeVerseJoinUpdateAll(List<LHOfficeVerseJoin> u);

    @Delete(entity = LHOficceVerseJoinEntity.class)
    void lhOfficeVerseJoinDeleteAll(List<LHOfficeVerseJoin> d);

    @Insert(entity = LHHymnEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void lhHymnInsertAll(List<LHHymn> c);

    @Update(entity = LHHymnEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhHymnUpdateAll(List<LHHymn> u);

    @Delete(entity = LHHymnEntity.class)
    void lhHymnDeleteAll(List<LHHymn> d);

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
}

