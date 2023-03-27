package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class TodayHomilias {

    @Embedded
    public TodayEntity today;

    @Relation(
            entity = LiturgyEntity.class,
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID"
    )
    public LiturgyWithTime feria;

    @Relation(
            entity = LiturgyHomilyJoinEntity.class,
            parentColumn = "liturgyFK",
            entityColumn = "liturgyFK"
    )
    public List<LiturgiaWithHomilias> homilias;

    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        theModel.setFecha(String.valueOf(today.hoy));
        theModel.setIdHour(2);
        theModel.setHasSaint(false);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(today.getTiempoId());
        theModel.setIdTiempoPrevio(1);
        return theModel;
    }

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        return dm;
}


    public Homily getDomainModel(){
        Homily dm=new Homily();
        dm.setHoy(getToday());

        List<HomilyList> listModel = new ArrayList<>();

        dm.setMetaLiturgia(getMetaLiturgia());
        for (LiturgiaWithHomilias item : homilias) {
            listModel.add(item.getDomainModel());
        }
        dm.setHomilias(listModel);
        return dm;
    }

}
