package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BibleCommentList;
import org.deiverbum.app.model.BibleComment;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayComentarios {

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
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public MisaWithComentarios comentarios;


    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public List<MisaWithComentariosRename> comentariosA;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public MisaWithComentariosRename comentariosB;

    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        //theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.previoId!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.hoy));
        //theModel.setColor(feria.colorFK);
        theModel.setIdHour(2);
        //theModel.setCalendarTime(feria.colorFK);
        theModel.setHasSaint(false);
        //theModel.setIdBreviario(feria.colorFK);
        //theModel.setIdDia(feria.colorFK);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(today.getTiempoId());
        theModel.setIdTiempoPrevio(1);
        //theModel.setTitulo(feria.nombre);
        return theModel;
    }


    public Saint getSanto(){
        return  santo.getDomainModelLH();
    }

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        return dm;
    }

    public BibleCommentList getDomainModel(){
        BibleCommentList dm=new BibleCommentList();
        dm.setHoy(getToday());
        List<List<BibleComment>> listModel = new ArrayList<>();
        dm.setMetaLiturgia(getMetaLiturgia());
        dm.setBiblica(comentarios.getBiblicaMisa());
        //listModel.add(comentarios.getDomainModel());
        for(MisaWithComentariosRename item : comentariosA){
            listModel.add(item.getDomainModel());
        }

        dm.setAllComentarios(listModel);
        return dm;
    }

}
