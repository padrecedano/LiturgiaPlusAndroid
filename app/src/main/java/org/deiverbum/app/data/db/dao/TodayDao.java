package org.deiverbum.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import org.deiverbum.app.data.entity.TodayHomilias;
import org.deiverbum.app.data.entity.TodayLaudes;
import org.deiverbum.app.data.entity.TodayMixto;
import org.deiverbum.app.data.entity.TodayNona;
import org.deiverbum.app.data.entity.TodayOficio;
import org.deiverbum.app.data.entity.TodaySexta;
import org.deiverbum.app.data.entity.TodayTercia;
import org.deiverbum.app.data.entity.TodayVisperas;
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


    @Insert(entity = org.deiverbum.app.data.entity.Today.class,
            onConflict = OnConflictStrategy.REPLACE)
    void insertTodays(List<Today> todays);


    @Insert(entity = org.deiverbum.app.data.entity.Today.class)
    void insertToday(Today today);


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
    @Query("SELECT * FROM today AS t " +
            "JOIN misa_lectura m ON t.mLecturasFK=m.liturgiaFK " +
            "WHERE t.hoy =:theDate AND m.orden=40")
    TodayMixto getMixtoOfToday(Integer theDate);

    @Transaction
    @Query(todayByDate)
    TodayVisperas geVisperasOfToday(Integer theDate);

    @Transaction
    @Query("SELECT * FROM today AS t " +
            "JOIN homilia h ON t.feriaFK=h.homiliaId " +
            "WHERE t.hoy =:theDate")
    TodayHomilias getHomilias(Integer theDate);



    /*
    @Insert
    void insertAll(Today... todays);

    @Delete
    void delete(Today today);

     */
}

