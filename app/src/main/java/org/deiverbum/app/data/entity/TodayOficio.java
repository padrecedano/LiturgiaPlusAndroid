package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHInvitatory;
import org.deiverbum.app.model.LHOfficeBiblical;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHOfficeOfReading;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.TeDeum;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayOficio {

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
            parentColumn = "oHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "oPsalmodyFK",
            entityColumn = "groupID"
    )
    public org.deiverbum.app.data.entity.LHPsalmody salmodia;

    @Relation(
            entity = PsalmodyEntity.class,
            parentColumn = "oPsalmodyFK",
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
            entity = LHPrayerEntity.class,
            parentColumn = "oPrayerFK",
            entityColumn = "groupID"
    )
    public LHPrayerAll lhPrayerAll;

    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        //theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.previoId!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.hoy));
        //theModel.setColor(feria.colorFK);
        theModel.setHasSaint(true);
        theModel.setIdHour(1);
        //theModel.setCalendarTime(feria.colorFK);
        //theModel.setIdBreviario(feria.colorFK);
        //theModel.setIdDia(feria.colorFK);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(1);
        theModel.setIdTiempoPrevio(1);
        //theModel.setTitulo(feria.nombre);
        return theModel;
    }

    public Saint getSanto(){
        return  santo.getDomainModelLH();
    }

    public LHInvitatory getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public LHHymn getHimno(){
        return himno.getDomainModel();
    }

    public org.deiverbum.app.model.LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public String getOficioVerso(){
        return oficioVerso.getDomainModel();
    }

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;

        dm.setTodayDate(today.getHoy());
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());
        dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }

    public List<LHOfficeBiblical> getBiblicas() {
        return biblicas.getDomainModel(today.getTiempoId());
    }

    public List<org.deiverbum.app.model.LHOfficePatristic> getPatristicas() {
        List<org.deiverbum.app.model.LHOfficePatristic> theList = new ArrayList<>();
        for (PatristicaOficioWithResponsorio item :
                patristicaOficioWithResponsorio) {
            theList.add(item.getDomainModelOficio(today.getTiempoId()));
        }
        return theList;
    }

    public Liturgy getDomainModel(){
        Liturgy dm= feria.getDomainModel();
        dm.typeID=1;
        dm.setHoy(getToday());
        BreviaryHour bh=new BreviaryHour();
        Oficio oficio=new Oficio();
/*        bh.setHimno(getHimno());
        bh.setSalmodia(getSalmodia());
        bh.setInvitatorio(getInvitatorio());
        bh.setTeDeum(new TeDeum(today.oTeDeum));
        bh.setOracion(lhPrayerAll.getDomainModel());
                bh.setOfficeOfReading(ol);
*/
        //dm.lhPsalmody=getSalmodia();
//dm.setLiturgyTime(feria.getTimeDM());

        LHOfficeOfReading ol=new LHOfficeOfReading();
        ol.setBiblica(getBiblicas());
        ol.setPatristica(getPatristicas());
        ol.setResponsorio(getOficioVerso());
        dm.setSanto(getSanto());
        oficio.setHoy(getToday());
        oficio.setInvitatorio(getInvitatorio());
        oficio.setHimno(getHimno());
        oficio.setOficioLecturas(ol);
        oficio.setSalmodia(getSalmodia());
        oficio.setTeDeum(new TeDeum(today.oTeDeum));
        oficio.setOracion((lhPrayerAll.getDomainModel()));
        bh.setOficio(oficio);
        dm.setBreviaryHour(bh);
        return dm;
    }

}
