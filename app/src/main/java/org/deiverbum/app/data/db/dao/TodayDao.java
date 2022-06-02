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
import org.deiverbum.app.data.entity.UserWithPlaylistsAndSongs;
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
//    @MapInfo(keyColumn = "hoy", valueColumn = "salmoId")
/*
    @Query(
            "SELECT t.hoy, s.salmoId FROM today AS t " +
                    "JOIN lh_salmodia AS s ON  t.olSalmos=s.liturgiaId WHERE " +
                    "t.hoy=20220320 " +
                    "GROUP BY s.liturgiaId"
    )
    public LiveData<Map<Integer, List<Integer>>> loadUserAndBookNames();
*/
    /*
    @Transaction
    @Query("SELECT * FROM lh_salmodia WHERE liturgiaId=300010201 " +
            "ORDER BY orden")
    public LiveData<List<SalmodiaWithSalmos>> getSalmodiaWithSalmos();
*/
    /*
    @Transaction
    @Query("SELECT * FROM lh_salmo AS s " +
            "INNER JOIN lh_salmodia AS l ON l.salmoFK=s.salmoId " +
            "WHERE l.liturgiaId=300010201 " +
            "ORDER BY orden")
    public LiveData<List<SalmosWithSalmodia>> getJoin();
*/

    @Transaction
    @Query("SELECT * FROM today AS t WHERE t.hoy =:theDate")
    public TodayOficio getOficioOfToday(Integer theDate);

    @Transaction
    @Query("SELECT * FROM lh_salmodia")
    public LiveData<List<SalmodiaWithSalmos>> getSalmos();

    @Transaction
    @Query("SELECT * FROM Today t INNER JOIN lh_salmodia a ON " +
            "t.oficioFK=a.grupoFK INNER JOIN lh_salmo s ON a.pericopaFK=s.salmoId " +

            "WHERE " +
            "t.hoy=20220325 ORDER BY a.orden")
    public LiveData<UserWithPlaylistsAndSongs> getUsersWithPlaylistsAndSongs();



    @Transaction
    @Query("SELECT * FROM Today t INNER JOIN lh_salmodia a ON " +
            "t.oficioFK=a.grupoFK INNER JOIN lh_salmo s ON a.pericopaFK=s.salmoId " +
            "WHERE " +
            "t.hoy=20220325 ORDER BY a.orden")
    public UserWithPlaylistsAndSongs getSalmod();

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
    @Query(
            "SELECT b.salmo FROM lh_salmodia AS a " +
                    "JOIN lh_salmo AS b ON a.salmoFK = b.salmoId WHERE a" +
                    ".liturgiaId=300010201"
    )
    public LiveData<Map<Salmodia, List<Salmo>>> loadUserAndBookNames();
*/

    /*
    @Insert
    void insertAll(Today... todays);

    @Delete
    void delete(Today today);

     */
}

