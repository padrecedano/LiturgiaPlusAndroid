package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.CanticoEvangelico;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Oracion;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayLaudes {

    @Embedded
    public Today today;

    @Relation(
            entity = SaintEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID"
    )
    public SaintWithAll santo;

    @Relation(
            entity = LHInvitatoryJoinEntity.class,
            parentColumn = "invitatoryFK",
            entityColumn = "groupID"
    )
    public LHInvitatoryAll invitatorio;

    @Relation(
            entity = LHHymnJoinEntity.class,
            parentColumn = "lHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "lBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "lPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHPsalmody salmodia;

    @Relation(
            entity = PsalmodyEntity.class,
            parentColumn = "lPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

    @Relation(
            entity = LHIntercessionsJoinEntity.class,
            parentColumn = "lIntercessionsFK",
            entityColumn = "groupID"

    )
    public LHIntercessionsDM lhIntercessionsDM;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "lPrayerFK",
            entityColumn = "groupID"
    )
    public LHPrayerAll lhPrayerAll;

    @Relation(
            entity = LiturgyEntity.class,
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID"
    )
    public LiturgyWithTime feria;

    @Relation(
            entity = LiturgyEntity.class,
            parentColumn = "previousFK",
            entityColumn = "liturgyID"
    )
    public LiturgyWithTime previo;

    @Relation(
            entity = LHGospelCanticleEntity.class,
            parentColumn = "lBenedictusFK",
            entityColumn = "groupID"
    )
    public LHGospelCanticleWithAntiphon benedictus;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public List<MisaWithLecturas> lecturas;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    //public List<MisaWithComentariosRename> comentarios;
    public MisaWithComentariosRename comentarios;

    @SuppressWarnings("unused")
    public List<MisaLecturas> getMisaLecturas(){
        //MisaLecturas theModel=new MisaLecturas();
        List<MisaLecturas> theModel=new ArrayList<>();

        List<BiblicaMisa> listModel = comentarios.getBiblicaMisas();
        BiblicaMisa listModell = comentarios.getBiblicaMisa();

        /*for (MisaWithComentariosRename item : comentarios) {
            //listModel.add(item.getBiblicaMisa());
        }*/
            //theModel.setLecturas(listModel);
        return theModel;
    }



    public Himno getHimno(){
        return himno.getDomainModel();
    }

//TODO incluir algo como hasPriority en Today
    public BiblicaBreve getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }

    public CanticoEvangelico getBenedictus(){
        return  benedictus.getDomainModel(2);
    }

    public Preces getPreces(){
        return  lhIntercessionsDM.getDomainModel();
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
        return lhPrayerAll.getDomainModel();
    }

    public Hoy getToday(){
        Hoy dm = new Hoy();
        dm.setFeria(feria.getDomainModel());
        dm.setFecha(String.valueOf(today.getHoy()));
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        dm.setMLecturasFK(today.mLecturasFK);
        dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }

    public Laudes getDomainModel(){
        Laudes dm=new Laudes();
        dm.setHoy(getToday());
        dm.setInvitatorio(getInvitatorio());
        dm.setSanto(getSanto());
getMisaLecturas();
        dm.setHimno(getHimno());
        dm.setSalmodia(getSalmodia());
        dm.setLecturaBreve(getBiblica());
        dm.setBenedictus(getBenedictus());
        dm.setPreces(getPreces());
        dm.setOracion(getOracion());
        return dm;
    }
}
