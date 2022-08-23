package org.deiverbum.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.deiverbum.app.data.entity.HimnoEntity;
import org.deiverbum.app.data.entity.LHCanticoEvangelicoEntity;
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
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.LHAntifona;
import org.deiverbum.app.model.LHBiblicaBreveJoin;
import org.deiverbum.app.model.LHCanticoEvangelico;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaHomiliaJoin;
import org.deiverbum.app.model.MisaLectura;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
@Dao
public interface TodayDao {

    String todayByDate = "SELECT * FROM today AS t WHERE t.hoy =:theDate";

    @Query("SELECT * FROM today WHERE hoy =  :hoy LIMIT 1")
    LiveData<Today> findByDate(Integer hoy);


    @Query("SELECT hoy FROM today ORDER BY hoy DESC LIMIT 1")
    Integer findLastDate();

    @Query("SELECT liturgiaId FROM liturgia ORDER BY liturgiaId DESC LIMIT 1")
    Integer findLastLiturgia();

    @Query("SELECT homiliaId FROM homilia ORDER BY homiliaId DESC LIMIT 1")
    Integer findLastHomilia();

    @Insert(entity = org.deiverbum.app.data.entity.Today.class,
            onConflict = OnConflictStrategy.REPLACE)
    void insertAllTodays(List<Today> today);

    @Insert(entity = org.deiverbum.app.data.entity.LiturgiaEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void liturgiaInsertAll(List<Liturgia> today);

    @Insert(entity = org.deiverbum.app.data.entity.HomiliaEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homiliaInsertAll(List<Homilia> list);

    @Insert(entity = org.deiverbum.app.data.entity.TodayTest.class,
            onConflict = OnConflictStrategy.REPLACE)
    void insertAllTodaysTest(List<Today> today);

    @Insert(entity = org.deiverbum.app.data.entity.HimnoEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void insertAllHimnos(List<Himno> today);

    @Insert(entity = org.deiverbum.app.data.entity.Today.class)
    void insertTodayX(Today today);


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
            "JOIN homilia h ON t.feriaFK=h.homiliaId " +
            "WHERE t.hoy =:theDate")
    TodayHomilias getHomilias(Integer theDate);

    @Transaction
    /*@Query("SELECT * FROM today AS t " +
            "JOIN homilia h ON t.feriaFK=h.homiliaId JOIN misa_lectura ml ON t.mLecturasFK=ml.liturgiaFK " +
            "WHERE t.hoy =:theDate AND ml.orden >= 40")*/
    @Query("SELECT * FROM today AS t " +
            "JOIN liturgy_group lg ON t.mLecturasFK=lg.groupID "+
            "JOIN mass_reading mr ON lg.groupID=mr.groupFK "+
            "JOIN bible_homily_join bhj ON bhj.readingFK=mr.readingFK "+
            "JOIN homilia h ON bhj.homilyFK=h.homiliaId "+
            "WHERE t.hoy =:theDate AND mr.`order` >= 40")
    TodayComentarios getComentarios(Integer theDate);

    @Insert(entity = org.deiverbum.app.data.entity.LiturgiaHomiliaJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void homiliaJoinInsertAll(List<LiturgiaHomiliaJoin> list);

    @Insert(entity = org.deiverbum.app.data.entity.LHCanticoEvangelicoEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void canticoEvangelicoInsertAll(List<LHCanticoEvangelico> list);

    @Insert(entity = org.deiverbum.app.data.entity.LHBiblicaBreveJoinEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void biblicaBreveJoinInsertAll(List<LHBiblicaBreveJoin> list);

    @Update(entity = org.deiverbum.app.data.entity.BibliaLecturaEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void bibleReadingUpdateAll(List<Biblica> list);

    @Update(entity = org.deiverbum.app.data.entity.AntifonaEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void lhAntifonaUpdateAll(List<LHAntifona> list);

    @Update(entity = org.deiverbum.app.data.entity.Today.class,
            onConflict = OnConflictStrategy.REPLACE)
    void todayUpdateAll(List<Today> list);

    @Update(entity = org.deiverbum.app.data.entity.MisaLecturaEntity.class,
            onConflict = OnConflictStrategy.REPLACE)
    void misaLecturaUpdateAll(List<MisaLectura> misaLectura);

    @Insert(entity = org.deiverbum.app.data.entity.MisaLecturaEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void misaLecturaInsertAll(List<MisaLectura> misaLectura);

    @Insert(entity = org.deiverbum.app.data.entity.BibliaLecturaEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void bibleReadingInsertAll(List<Biblica> bibleReading);

    @Insert(entity = org.deiverbum.app.data.entity.SaintLifeEntity.class,
            onConflict = OnConflictStrategy.IGNORE)
    void saintLifeInsertAll(List<SaintLife> saintLife);

    /*
    @Insert
    void insertAll(Today... todays);

    @Delete
    void delete(Today today);

     */
}

