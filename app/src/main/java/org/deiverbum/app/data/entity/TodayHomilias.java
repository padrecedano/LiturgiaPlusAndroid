package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Santo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayHomilias {

    @Embedded
    public Today today;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "feriaFK",
            entityColumn = "liturgiaId"
    )
    public LiturgiaEntity feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previoId",
            entityColumn = "liturgiaId"
    )
    public LiturgiaEntity previo;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId"
    )
    public SantoWithAll santo;



    @Relation(
            entity = LiturgiaHomiliaJoinEntity.class,
            parentColumn = "feriaFK",
            entityColumn = "liturgiaFK"
    )
    public List<LiturgiaWithHomilias> homilias;


    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        //theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.previoId!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.hoy));
        theModel.setColor(feria.colorFK);
        theModel.setIdHour(2);
        theModel.setCalendarTime(feria.colorFK);
        theModel.setHasSaint(false);
        theModel.setIdBreviario(feria.colorFK);
        theModel.setIdDia(feria.colorFK);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(today.getTiempoId());
        theModel.setIdTiempoPrevio(1);
        theModel.setTitulo(feria.nombre);
        return theModel;
    }


    public Santo getSanto(){
        return  santo.getDomainModelLH();
    }







    public Homilias getDomainModel(){
        Homilias dm=new Homilias();
        List<Homilia> listModel = new ArrayList<>();

        dm.setMetaLiturgia(getMetaLiturgia());
        for (LiturgiaWithHomilias item : homilias) {
            listModel.add(item.getDomainModel());
        }
        dm.setHomilias(listModel);
        return dm;
    }

}
