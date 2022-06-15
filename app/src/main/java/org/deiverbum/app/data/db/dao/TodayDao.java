package org.deiverbum.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import org.deiverbum.app.data.entity.TodayLaudes;
import org.deiverbum.app.data.entity.TodayMixto;
import org.deiverbum.app.data.entity.TodayOficio;
import org.deiverbum.app.data.entity.SalmoEntity;
import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.entity.TodayTercia;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
@Dao
public interface TodayDao {


    @Query("SELECT * FROM today WHERE hoy =  :hoy LIMIT 1")
    LiveData<Today> findByDate(Integer hoy);

    @Query("SELECT hoy FROM today ORDER BY hoy DESC LIMIT 1")
    LiveData<Integer> findLastLive();

    @Query("SELECT hoy FROM today ORDER BY hoy DESC LIMIT 1")
    Integer findLastDate();


    @Insert(entity = org.deiverbum.app.data.entity.Today.class,
            onConflict =OnConflictStrategy.REPLACE)
    public void insertTodays(List<Today> todays);


    @Insert(entity = org.deiverbum.app.data.entity.Today.class)
    public void insertToday(Today today);


    @Transaction
    @Query("SELECT * FROM today AS t WHERE t.hoy =:theDate")
    public TodayOficio getOficioOfToday(Integer theDate);

    @Transaction
    @Query("SELECT * FROM lh_salmodia")
    public LiveData<List<SalmodiaWithSalmos>> getSalmos();







    @Query("SELECT * FROM lh_salmo LIMIT 1")
    public LiveData<SalmoEntity> getSalmo();

    @Query("SELECT * FROM lh_salmo WHERE salmoId=:salmoId LIMIT 1")
    public SalmoEntity getSalmoById(Integer salmoId);

    @Transaction
    @Query("SELECT * FROM today AS t WHERE t.hoy =:theDate")
    public TodayLaudes getLaudesOfToday(Integer theDate);

    @Query("SELECT * FROM today AS t WHERE t.hoy =:theDate")
    public TodayTercia geTerciaOfToday(Integer theDate);

    @Query("SELECT * FROM today AS t " +
            "JOIN misa_lectura m ON t.mLecturasFK=m.liturgiaFK "+
            "WHERE t.hoy =:theDate AND m.orden=40")
    public TodayMixto getTodayMixto(Integer theDate);



    /*
    @Insert
    void insertAll(Today... todays);

    @Delete
    void delete(Today today);

     */
}

