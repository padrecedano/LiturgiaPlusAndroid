package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.Evangelio;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaLecturaWithAll {
    @Embedded
    public LiturgyGroupEntity lectura;


    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = MassReadingEntity.class
    )
    public MassReadingWithAll libro;

    public Biblica getDomainModel() {
        Biblica theModel=new Biblica();
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
