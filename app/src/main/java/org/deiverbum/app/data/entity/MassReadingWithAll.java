package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;

import java.util.List;

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
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public BibleReadingWithBook lectura;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public List<BibleReadingEntity> lecturass;


    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public List<MassReadingWithBook> lecturas;


    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID",
            entity = LiturgyEntity.class
    )
    public LiturgyWithTime liturgia;

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
