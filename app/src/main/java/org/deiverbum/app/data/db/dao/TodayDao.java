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
import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.data.entity.LHHymnEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.data.entity.SyncStatusEntity;
import org.deiverbum.app.data.entity.TodayComentarios;
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
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.LHAntifona;
import org.deiverbum.app.model.LHBiblicaBreveJoin;
import org.deiverbum.app.model.LHCanticoEvangelico;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaHomiliaJoin;
import org.deiverbum.app.model.MisaLectura;
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

    @Query("SELECT liturgyID FROM liturgy ORDER BY liturgyID DESC LIMIT 1")
    Integer findLastLiturgia();

    @Query("SELECT homilyID FROM homily ORDER BY homilyID DESC LIMIT 1")
    Integer findLastHomilia();

    @Insert(entity = org.deiverbum.app.data.entity.Today.class,
            onConflict = OnConflictStrategy.REPLACE)
    void insertAllTodays(List<Today> today);

    @Insert(entity = LiturgyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgiaInsertAll(List<Liturgia> today);

    @Insert(entity = HomilyEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homiliaInsertAll(List<Homilia> list);


    @Insert(entity = LHHymnEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void insertAllHimnos(List<Himno> today);

    @Insert(entity = org.deiverbum.app.data.entity.Today.class)
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
    TodayTercia geTerciaOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodaySexta geSextaOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayNona geNonaOfToday(Integer theDate);

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
            "JOIN HOMILY h ON t.weekDayFK=h.homilyID " +
            "WHERE t.todayDate =:theDate")
    TodayHomilias getHomilias(Integer theDate);

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



    @Insert(entity = LHGospelCanticleEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void canticoEvangelicoInsertAll(List<LHCanticoEvangelico> list);

    @Insert(entity = LHReadingShortJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void biblicaBreveJoinInsertAll(List<LHBiblicaBreveJoin> list);

    @Update(entity = BibleReadingEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleReadingUpdateAll(List<Biblica> list);

    @Update(entity = AntiphonEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhAntifonaUpdateAll(List<LHAntifona> list);

    @Update(entity = org.deiverbum.app.data.entity.Today.class,
            onConflict = OnConflictStrategy.REPLACE)
    void todayUpdateAll(List<Today> list);

    @Update(entity = org.deiverbum.app.data.entity.MassReadingEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void misaLecturaUpdateAll(List<MisaLectura> misaLectura);

    @Insert(entity = org.deiverbum.app.data.entity.MassReadingEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void misaLecturaInsertAll(List<MisaLectura> misaLectura);

    @Delete(entity = org.deiverbum.app.data.entity.MassReadingEntity.class)
    void massReadingDeleteAll(List<MisaLectura> misaLectura);

    @Insert(entity = BibleReadingEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void bibleReadingInsertAll(List<Biblica> bibleReading);

    @Insert(entity = org.deiverbum.app.data.entity.SaintLifeEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void saintLifeInsertAll(List<SaintLife> saintLife);
/*
    @Query("UPDATE sync_status SET version=version+1 WHERE tableName=:tableName")
    void syncUpdate(String tableName);
*/

    @Query("SELECT lastUpdate FROM sync_status WHERE tableName=:tableName LIMIT 1")
    String syncLastVersion(String tableName);

    @Update(entity = SyncStatusEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void syncUpdateAll(List<SyncStatus> list);

    @Insert(entity = LiturgyHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homiliaJoinInsertAll(List<LiturgiaHomiliaJoin> list);

    @Update(entity = LiturgyHomilyJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homiliaJoinUpdateAll(List<LiturgiaHomiliaJoin> list);

    @Delete(entity = org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity.class)
    Integer homiliaJoinDeleteAll(List<LiturgiaHomiliaJoin> homilyJoin);

    /*
    @Insert
    void insertAll(Today... todays);

    @Delete
    void delete(Today today);

     */
}

