package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.CanticoEvangelico;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Oracion;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.model.TeDeum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayMixto {

    @Embedded
    public Today today;

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
            entity = LHOficceVerseJoinEntity.class,
            parentColumn = "oVerseFK",
            entityColumn = "groupID"
    )
    public OficceVerseAll oficioVerso;


    @Relation(
            entity = LHOfficeBiblicalJoinEntity.class,
            parentColumn = "oBiblicalFK",
            entityColumn = "groupID"
    )
    public LHOfficeBiblicalAll biblicas;

    @Relation(
            entity = LHOfficePatristicEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public LHOfficePatristic patristica;


    @Relation(
            entity = LHOfficePatristicEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public List<PatristicaOficioWithResponsorio> patristicaOficioWithResponsorio;

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
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public List<MisaWithComentariosRename> comentarios;


    public List<BiblicaMisa> getEvangelios(){
        List<BiblicaMisa> listModel=new ArrayList<>();
        for (MisaWithComentariosRename item : comentarios) {
            if(item.misaLectura.getOrden()>=40) {
                listModel.add(item.getBiblicaMisa());
            }
        }
        return listModel;
    }


    public Hoy getToday(){
        //getMisaLecturass();
        Hoy dm = new Hoy();
        dm.setFeria(feria.getDomainModel());
        dm.setFecha(String.valueOf(today.getHoy()));
        //theModel.setColor(feria.getColorFK());
        dm.setIdHour(0);
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        //theModel.setIdBreviario(feria.colorFK);
        //theModel.setIdDia(feria.colorFK);
        dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());//.setIdPrevio(1);
        //theModel.setIdSemana(1);
        //theModel.setIdTiempo(9);
        //theModel.setIdTiempoPrevio(1);
        dm.setTitulo(feria.getDomainModel().getNombre());
        //theModel.setLiturgiaFeria(feria.getDomainModel());
        return dm;
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

    public List<BiblicaOficio> getBiblicas() {
        return biblicas.getDomainModel(today.getTiempoId());
    }

    public List<Patristica> getPatristicas() {
        List<Patristica> theList = new ArrayList<>();
        for (PatristicaOficioWithResponsorio item :
                patristicaOficioWithResponsorio) {
            theList.add(item.getDomainModelOficio(today.getTiempoId()));
        }
        return theList;
    }
    public String getOficioVerso(){
        return oficioVerso.getDomainModel();
    }

    public Mixto getDomainModel(){
        Mixto dm=new Mixto();
        Laudes laudes=new Laudes();
        Oficio oficio=new Oficio();
        dm.setHoy(getToday());
        laudes.setHoy(getToday());

        if(santo!=null) {
            dm.setSanto(santo.getDomainModelLH());
        }
        dm.setInvitatorio(getInvitatorio());
        laudes.setHimno(getHimno());
        laudes.setSalmodia(getSalmodia());
        laudes.setLecturaBreve(getBiblica());
        laudes.setBenedictus(getBenedictus());
        laudes.setPreces(getPreces());
        laudes.setOracion(getOracion());
        OficioLecturas ol=new OficioLecturas();
        ol.setBiblica(getBiblicas());
        ol.setPatristica(getPatristicas());
        ol.setResponsorio(getOficioVerso());
        oficio.setOficioLecturas(ol);
        oficio.setTeDeum(new TeDeum(today.oTeDeum));
        laudes.setLecturaBreve(getBiblica());
        laudes.setBenedictus(getBenedictus());
        laudes.setPreces(getPreces());
        laudes.setOracion(getOracion());
        dm.setOficio(oficio);
        dm.setLaudes(laudes);
        dm.setEvangelios(getEvangelios());
        return dm;
    }

}
