package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.model.MassReading;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
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
