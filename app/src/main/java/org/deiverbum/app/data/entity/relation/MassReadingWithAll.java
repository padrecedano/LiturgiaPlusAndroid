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
public class MassReadingWithAll {
    @Embedded

    public MassReadingEntity massReadingEntity;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public BibleReadingWithBook lectura;

    public MassReading getDomainModel() {
        MassReading theModel = lectura.getDomainModelMisa();
        theModel.setReadingID(massReadingEntity.readingFK);
        theModel.setTema(massReadingEntity.getTema());
        theModel.setOrden(massReadingEntity.getOrden());
        return theModel;
    }


}
