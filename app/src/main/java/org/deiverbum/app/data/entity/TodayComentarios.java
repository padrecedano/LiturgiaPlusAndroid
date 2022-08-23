package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.ComentarioBiblico;
import org.deiverbum.app.model.Comentarios;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Santo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayComentarios {

    @Embedded
    public Today today;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "feriaFK",
            entityColumn = "liturgiaId"
    )
    public LiturgiaWithTiempo feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previoId",
            entityColumn = "liturgiaId"
    )
    public LiturgiaWithTiempo previo;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId"
    )
    public SantoWithAll santo;
/*
    @Relation(
            entity = MisaLecturaEntity.class,
            parentColumn = "mLecturasFK",
            entityColumn = "liturgiaFK"
    )
    public List<MisaWithComentarios> misaLecturas;
*/
    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "mLecturasFK",
            entityColumn = "groupFK"
    )
    public MisaWithComentariosBis lecturass;


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


    public Santo getSanto(){
        return  santo.getDomainModelLH();
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

    public Comentarios getDomainModel(){
        Comentarios dm=new Comentarios();
        dm.setHoy(getToday());
        List<List<ComentarioBiblico>> listModel = new ArrayList<>();
        dm.setMetaLiturgia(getMetaLiturgia());
        dm.setBiblica(lecturass.getBiblicaMisa());
listModel.add(lecturass.getDomainModel());
        //lecturass.
        /*for (MisaWithComentariosBis item : lecturass) {
            listModel.add(item.getDomainModel());
        }*/
        dm.setAllComentarios(listModel);
        return dm;
    }

}
