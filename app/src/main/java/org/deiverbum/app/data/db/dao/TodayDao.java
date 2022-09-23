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
import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.data.entity.LHHymnEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
import org.deiverbum.app.data.entity.SyncStatusEntity;
import org.deiverbum.app.data.entity.TodayComentarios;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.data.entity.TodayHomilias;
import org.deiverbum.app.data.entity.TodayLaudes;
import org.deiverbum.app.data.entity.TodayMisaLecturas;
import org.deiverbum.app.data.entity.TodayMixto;
import org.deiverbum.app.data.entity.TodayNona;
import org.deiverbum.app.data.entity.TodayOficio;
import org.deiverbum.app.data.entity.TodaySanto;
import org.deiverbum.app.data.entity.TodaySexta;
import org.deiverbum.app.data.entity.TodayTercia;
import org.deiverbum.app.data.entity.TodayVisperas;
import org.deiverbum.app.model.BibleHomilyJoin;
import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHAntiphon;
import org.deiverbum.app.model.LHGospelCanticle;
import org.deiverbum.app.model.LHReadingShortJoin;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHomilyJoin;
import org.deiverbum.app.model.MassReadingOLD;
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
/*
    @Query("SELECT lastUpdate FROM sync_status WHERE tableName =  :tableName LIMIT 1")
    Integer getLastVersion(String tableName);
*/
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
    TodayMisaLecturas getMisaLecturas(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodaySanto getSantoOfToday(Integer theDate);

    @Transaction
    @Query("SELECT * FROM today AS t " +
            "JOIN `homily` h ON t.weekDayFK=h.homilyID " +
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

    @Transaction
    @Query("SELECT * FROM today t JOIN mass_reading mr ON t.massReadingFK=mr.liturgyFK WHERE t.todayDate =:theDate")
    TodayComentarios getComentarioss(Integer theDate);

    @Insert(entity = LHGospelCanticleEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void canticoEvangelicoInsertAll(List<LHGospelCanticle> list);

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

    @Insert(entity = SaintLifeEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void saintLifeInsertAll(List<SaintLife> saintLife);
/*
    @Query("UPDATE sync_status SET version=version+1 WHERE tableName=:tableName")
    void syncUpdate(String tableName);
*/

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
    void homiliaJoinInsertAll(List<LiturgyHomilyJoin> list);

    @Update(entity = LiturgyHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homiliaJoinUpdateAll(List<LiturgyHomilyJoin> list);

    @Delete(entity = LiturgyHomilyJoinEntity.class)
    Integer homiliaJoinDeleteAll(List<LiturgyHomilyJoin> homilyJoin);

    @Query("UPDATE sync_status SET lastUpdate=:lastUpdate")
    void syncUpdate(String lastUpdate);

    @Insert(entity = BibleHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyJoinInsertAll(List<BibleHomilyJoin> list);

    @Update(entity = BibleHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleHomilyJoinUpdateAll(List<BibleHomilyJoin> list);

    @Delete(entity = BibleHomilyJoinEntity.class)
    Integer bibleHomilyJoinDeleteAll(List<BibleHomilyJoin> homilyJoin);

    @Insert(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyInsertAll(List<Homily> list);

    @Update(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homilyUpdateAll(List<Homily> list);

    @Delete(entity = HomilyEntity.class)
    void homilyDeleteAll(List<Homily> homilyJoin);


    /*
    @Insert
    void insertAll(TodayEntity... todays);

    @Delete
    void delete(TodayEntity today);

     */
}

