package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MassReadingWithComment {
    @Embedded
    public LiturgyGroupEntity groupEntity;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = MassReadingEntity.class
    )

    public MassReadingEntity massReadingEntity;


    @Relation(
            parentColumn = "readingFK",
            entityColumn = "lecturaId",
            entity = BibleReadingEntity.class
    )
    public BibleHomilyJoinEntity lectura;

    public BiblicaMisa getDomainModel() {
        //BiblicaMisa theModel=new BiblicaMisa();
        BiblicaMisa theModel = null;//lectura.getDomainModelMisa();
        theModel.setTema(massReadingEntity.getTema());
        theModel.setOrden(massReadingEntity.getOrden());
        //theModel.setLibro(lectura.getDomainModel().getLibro());
        //theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        //theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        //theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        //theModel.setCita(lectura.getCita());
        //theModel.setTema(misaLectura.getTema());
        //theModel.setOrden(misaLectura.getOrden());
        //theModel.setTexto(lectura.getTexto());
        return theModel;
    }


}
