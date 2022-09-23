package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblical;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MassReadingAll {
    @Embedded
    public LiturgyGroupEntity lectura;


    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = MassReadingEntity.class
    )
    public MassReadingWithAll libro;

    public Biblical getDomainModel() {
        Biblical theModel=new Biblical();
        /*theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        //theModel.setTema(lectura.getTema());
        theModel.setTexto(lectura.getTexto());*/
        return theModel;
    }


}
