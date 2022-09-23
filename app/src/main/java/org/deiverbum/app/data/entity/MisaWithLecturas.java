package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.MassReading;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaWithLecturas {
    @Embedded
    public MassReadingEntity misaLectura;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public MassReadingWithBook lectura;

    public MassReading getDomainModel() {
        MassReading theModel = lectura.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

}
