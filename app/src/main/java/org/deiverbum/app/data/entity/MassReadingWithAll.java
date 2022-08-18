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
public class MassReadingWithAll {
    @Embedded
    /*public LiturgyGroupEntity groupEntity;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = MassReadingEntity.class
    )
*/
    public MassReadingEntity massReadingEntity;


    @Relation(
            parentColumn = "readingFK",
            entityColumn = "lecturaId",
            entity = BibliaLecturaEntity.class
    )
    public LecturaWithLibro lectura;

    public BiblicaMisa getDomainModel() {
        //BiblicaMisa theModel=new BiblicaMisa();
        BiblicaMisa theModel = lectura.getDomainModelMisa();
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
