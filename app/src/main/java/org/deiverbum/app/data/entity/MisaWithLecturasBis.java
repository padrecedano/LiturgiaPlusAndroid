package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.MassReading;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaWithLecturasBis {
    @Embedded
    public LiturgyGroupEntity misaLectura;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "liturgyFK",
            entity = MassReadingEntity.class
    )
    public List<MassReadingWithAll> lectura;

    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID",
            entity = LiturgyEntity.class
    )
    public LiturgyWithTime liturgia;

    public MassReading getDomainModel() {
        MassReading theModel = null;//lectura.getDomainModelMisa();
        //theModel.setTema(misaLectura.getTema());
        //theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

    public Liturgy getLiturgia() {
        return liturgia.getDomainModel();
    }

}
