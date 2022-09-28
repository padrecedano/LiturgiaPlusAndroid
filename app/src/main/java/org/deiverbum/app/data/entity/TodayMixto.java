package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHInvitatory;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.LHOfficeBiblical;
import org.deiverbum.app.model.LHGospelCanticle;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.LHOfficeOfReading;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.LHOfficePatristic;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.TeDeum;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayMixto {

    @Embedded
    public TodayEntity today;

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
    public org.deiverbum.app.data.entity.LHPsalmody salmodia;

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
    public org.deiverbum.app.data.entity.LHOfficePatristic patristica;


    @Relation(
            entity = LHOfficePatristicEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public List<PatristicaOficioWithResponsorio> patristicaOficioWithResponsorio;

    @Relation(
            entity = LHGospelCanticleJoinEntity.class,
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


    public List<MassReading> getEvangelios(){
        List<MassReading> listModel=new ArrayList<>();
        for (MisaWithComentariosRename item : comentarios) {
            if(item.misaLectura.getOrden()>=40) {
                listModel.add(item.getBiblicaMisa());
            }
        }
        return listModel;
    }

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        return dm;
    }
    public Today getTodayy(){
        //getMisaLecturass();
        Today dm = new Today();
        dm.setFeria(feria.getDomainModel());
        dm.setFecha(String.valueOf(today.getHoy()));
        //theModel.setColor(feria.getColorFK());
        dm.setIdHour(0);
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(today.hasSaint);
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


    public LHHymn getHimno(){
        return himno.getDomainModel();
    }

    //TODO incluir algo como hasPriority en TodayEntity
    public BiblicalShort getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }

    public LHGospelCanticle getBenedictus(){
        return  benedictus.getDomainModel(2);
    }

    public LHIntercession getPreces(){
        return  lhIntercessionsDM.getDomainModel();
    }

    public Saint getSanto(){
        return  santo.getDomainModelLH();
    }

    public LHInvitatory getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }

    public List<LHOfficeBiblical> getBiblicas() {
        return biblicas.getDomainModel(today.getTiempoId());
    }

    public List<LHOfficePatristic> getPatristicas() {
        List<org.deiverbum.app.model.LHOfficePatristic> theList = new ArrayList<>();
        for (PatristicaOficioWithResponsorio item :
                patristicaOficioWithResponsorio) {
            theList.add(item.getDomainModelOficio(today.getTiempoId()));
        }
        return theList;
    }
    public String getOficioVerso(){
        return oficioVerso.getDomainModel();
    }

    public Liturgy getDomainModel(){
        Liturgy dm= feria.getDomainModel();
        dm.typeID=0;
        dm.setHoy(getToday());
        BreviaryHour bh=new BreviaryHour();
        Mixto mixto=new Mixto();
        Oficio oficio=new Oficio();
        Laudes laudes=new Laudes();

        mixto.setHoy(getToday());
        //laudes.setHoy(getToday());

        if(santo!=null) {
            dm.setSanto(santo.getDomainModelLH());
        }
        mixto.setInvitatorio(getInvitatorio());
        laudes.setHimno(getHimno());
        laudes.setSalmodia(getSalmodia());
        laudes.setLecturaBreve(getBiblica());
        laudes.setBenedictus(getBenedictus());
        laudes.setPreces(getPreces());
        laudes.setOracion(getOracion());
        LHOfficeOfReading ol=new LHOfficeOfReading();
        ol.setBiblica(getBiblicas());
        ol.setPatristica(getPatristicas());
        ol.setResponsorio(getOficioVerso());
        oficio.setOficioLecturas(ol);
        oficio.setTeDeum(new TeDeum(today.oTeDeum));
        laudes.setLecturaBreve(getBiblica());
        laudes.setBenedictus(getBenedictus());
        laudes.setPreces(getPreces());
        laudes.setOracion(getOracion());
        mixto.setOficio(oficio);
        mixto.setLaudes(laudes);
        bh.setMixto(mixto);
        mixto.setEvangelios(getEvangelios());
        dm.setBreviaryHour(bh);
        return dm;
    }



    public Mixto getDomainModelX(){
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
        LHOfficeOfReading ol=new LHOfficeOfReading();
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
