package org.deiverbum.app.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.AntifonaEntity;
import org.deiverbum.app.data.entity.EpigrafeEntity;
import org.deiverbum.app.data.entity.HimnoEntity;
import org.deiverbum.app.data.entity.MemberTeamMap;
import org.deiverbum.app.data.entity.OficioEntity;
import org.deiverbum.app.data.entity.SalmoEntity;
import org.deiverbum.app.data.entity.SalmodiaEntity;
import org.deiverbum.app.data.entity.SalmodiaSalmoCrossRef;
import org.deiverbum.app.data.entity.SantoEntity;
import org.deiverbum.app.data.entity.TemaEntity;
import org.deiverbum.app.data.entity.Today;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
@Database(entities =
        {
                Today.class,
                SantoEntity.class,
                HimnoEntity.class,
                SalmodiaEntity.class,
                SalmoEntity.class,
                SalmodiaSalmoCrossRef.class,
                AntifonaEntity.class,
                TemaEntity.class,
                EpigrafeEntity.class,
                OficioEntity.class,

                MemberTeamMap.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodayDao todayDao();
}

