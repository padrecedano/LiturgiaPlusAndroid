package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.BibleComment;
import org.deiverbum.app.model.BibleCommentList;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
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
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public List<MisaWithComentarios> comentarios;

    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        theModel.setFecha(String.valueOf(today.hoy));
        //theModel.setIdHour(2);
        theModel.setHasSaint(false);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdTiempo(today.getTiempoId());
        return theModel;
    }

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        //dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        return dm;
    }

    public BibleCommentList getDomainModel(){
        BibleCommentList dm=new BibleCommentList();
        dm.setHoy(getToday());
        List<List<BibleComment>> listModel = new ArrayList<>();
        dm.setMetaLiturgia(getMetaLiturgia());
        for(MisaWithComentarios item : comentarios){
            listModel.add(item.getDomainModel());
        }
        dm.setAllComentarios(listModel);
        return dm;
    }

}
