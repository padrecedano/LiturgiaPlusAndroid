package org.deiverbum.app.data.entity;

import android.util.Log;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.CanticoEvangelico;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oracion;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.model.Visperas;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayVisperas {

    @Embedded
    public Today today;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID"
    )
    public SantoWithAll santo;

    @Relation(
            entity = LHInvitatorioJoinEntity.class,
            parentColumn = "invitatoryFK",
            entityColumn = "groupID"
    )
    public InvitatorioAll invitatorio;

    @Relation(
            entity = LHHimnoJoinEntity.class,
            parentColumn = "vHymnFK",
            entityColumn = "groupID"
    )
    public HimnoWithAll himno;

    @Relation(
            entity = LHBiblicaBreveJoinEntity.class,
            parentColumn = "vBiblicalFK",
            entityColumn = "groupID"
    )
    public BiblicaBreveAll biblica;

    @Relation(
            entity = LHSalmodiaJoinEntity.class,
            parentColumn = "vPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHSalmodia salmodia;

    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "vPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<SalmodiaWithSalmos> salmos;

    @Relation(
            entity = LHPrecesJoinEntity.class,
            parentColumn = "vIntercessionsFK",
            entityColumn = "groupID"

    )
    public LHPreces lhPreces;

    @Relation(
            entity = LHOracionEntity.class,
            parentColumn = "vPrayerFK",
            entityColumn = "groupID"
    )
    public LHOracion lhOracion;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "weekDayFK",
            entityColumn = "liturgyID"
    )
    public LiturgiaWithTiempo feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previousFK",
            entityColumn = "liturgyID"
    )
    public LiturgiaWithTiempo previo;

    @Relation(
            entity = LHCanticoEvangelicoEntity.class,
            parentColumn = "vMagnificatFK",
            entityColumn = "groupID"
    )
    public CanticoEvangelicoWithAntifona magnificat;

    @Relation(
            entity = MisaLecturaEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public List<MisaWithLecturas> lecturas;



    public Himno getHimno(){
        return himno.getDomainModel();
    }

//TODO incluir algo como hasPriority en Today
    public BiblicaBreve getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }

    public CanticoEvangelico getMagnificat(){
        return  magnificat.getDomainModel(6);
    }

    public Preces getPreces(){
        return  lhPreces.getDomainModel();
    }

    public Santo getSanto(){
        return  santo.getDomainModelLH();
    }

    public Invitatorio getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public Salmodia getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Oracion getOracion() {
        return lhOracion.getDomainModel();
    }


    public Hoy getToday(){
        try {
            Hoy dm = new Hoy();
            dm.setIdHour(6);
            dm.setFeria(feria.getDomainModel());
            dm.setFecha(String.valueOf(today.getHoy()));
            dm.setCalendarTime(today.tiempoId);
            dm.setHasSaint(true);
            dm.setMLecturasFK(today.mLecturasFK);
            if (today.previoId != 0) {
                dm.setPrevio(previo.getDomainModel());
            }
            dm.setTitulo(feria.getDomainModel().getNombre());
            return dm;
        }catch (Exception e){
            Log.d("ERR",e.getMessage());
            return null;
        }
    }

    public Visperas getDomainModel(){
        Visperas dm=new Visperas();
        dm.setHourId(6);
        dm.setHoy(getToday());
        //dm.setSanto(santo.getDomainModelLH());
        dm.setHimno(getHimno());
        dm.setSalmodia(getSalmodia());
        dm.setLecturaBreve(getBiblica());
        dm.setMagnificat(getMagnificat());
        dm.setPreces(getPreces());
        dm.setOracion(getOracion());
        return dm;
    }
}
