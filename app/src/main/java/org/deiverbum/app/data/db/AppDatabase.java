package org.deiverbum.app.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.AntifonaEntity;
import org.deiverbum.app.data.entity.BibliaLecturaEntity;
import org.deiverbum.app.data.entity.BibliaLibroEntity;
import org.deiverbum.app.data.entity.EpigrafeEntity;
import org.deiverbum.app.data.entity.HimnoEntity;
import org.deiverbum.app.data.entity.HomiliaEntity;
import org.deiverbum.app.data.entity.InvitatorioEntity;
import org.deiverbum.app.data.entity.LHBiblicaBreveEntity;
import org.deiverbum.app.data.entity.LHBiblicaBreveJoinEntity;
import org.deiverbum.app.data.entity.LHBiblicaOficioEntity;
import org.deiverbum.app.data.entity.LHBiblicaOficioJoinEntity;
import org.deiverbum.app.data.entity.LHCanticoEvangelicoEntity;
import org.deiverbum.app.data.entity.LHHimnoJoinEntity;
import org.deiverbum.app.data.entity.LHInvitatorioJoinEntity;
import org.deiverbum.app.data.entity.LHOficioResponsorioEntity;
import org.deiverbum.app.data.entity.LHOficioResponsorioJoinEntity;
import org.deiverbum.app.data.entity.LHOficioVersoEntity;
import org.deiverbum.app.data.entity.LHOficioVersoJoinEntity;
import org.deiverbum.app.data.entity.LHOracionEntity;
import org.deiverbum.app.data.entity.LHPatristicaEntity;
import org.deiverbum.app.data.entity.LHPatristicaOficioEntity;
import org.deiverbum.app.data.entity.LHPatristicaOficioJoinEntity;
import org.deiverbum.app.data.entity.LHPrecesEntity;
import org.deiverbum.app.data.entity.LHPrecesJoinEntity;
import org.deiverbum.app.data.entity.LHResponsorioBreveEntity;
import org.deiverbum.app.data.entity.LHResponsorioEntity;
import org.deiverbum.app.data.entity.LHSalmodiaJoinEntity;
import org.deiverbum.app.data.entity.LHSantoEntity;
import org.deiverbum.app.data.entity.LiturgiaEntity;
import org.deiverbum.app.data.entity.LiturgiaHomiliaJoinEntity;
import org.deiverbum.app.data.entity.LiturgiaTiempoEntity;
import org.deiverbum.app.data.entity.MisaLecturaEntity;
import org.deiverbum.app.data.entity.ObraEntity;
import org.deiverbum.app.data.entity.OracionEntity;
import org.deiverbum.app.data.entity.PadreEntity;
import org.deiverbum.app.data.entity.SalmoEntity;
import org.deiverbum.app.data.entity.SalmodiaEntity;
import org.deiverbum.app.data.entity.SantoEntity;
import org.deiverbum.app.data.entity.TemaEntity;
import org.deiverbum.app.data.entity.Today;
import org.deiverbum.app.data.entity.TodayTest;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
@Database(entities =
        {
                Today.class,
                TodayTest.class,
                LiturgiaEntity.class,
                LiturgiaTiempoEntity.class,
                SantoEntity.class,
                InvitatorioEntity.class,
                LHInvitatorioJoinEntity.class,
                HimnoEntity.class,
                LHHimnoJoinEntity.class,
                SalmodiaEntity.class,
                LHSalmodiaJoinEntity.class,
                SalmoEntity.class,
                AntifonaEntity.class,
                TemaEntity.class,
                EpigrafeEntity.class,
                LHOficioVersoEntity.class,
                LHOficioVersoJoinEntity.class,
                LHBiblicaOficioJoinEntity.class,

                LHPatristicaEntity.class,
                LHPatristicaOficioEntity.class,
                LHPatristicaOficioJoinEntity.class,

                HomiliaEntity.class,
                PadreEntity.class,
                ObraEntity.class,
                LHResponsorioEntity.class,
                LHResponsorioBreveEntity.class,
                BibliaLibroEntity.class,
                BibliaLecturaEntity.class,
                LHBiblicaBreveEntity.class,
                LHBiblicaBreveJoinEntity.class,
                LHBiblicaOficioEntity.class,
                LHCanticoEvangelicoEntity.class,
                LHPrecesEntity.class,
                LHPrecesJoinEntity.class,
                OracionEntity.class,
                LHOracionEntity.class,
                MisaLecturaEntity.class,
                LHSantoEntity.class,
                LiturgiaHomiliaJoinEntity.class


                },
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodayDao todayDao();
}

