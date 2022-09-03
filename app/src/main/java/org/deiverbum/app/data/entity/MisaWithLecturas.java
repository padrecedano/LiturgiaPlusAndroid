package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaWithLecturas {
    @Embedded
    public MisaLecturaEntity misaLectura;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibliaLecturaEntity.class
    )
    public LecturaWithLibro lectura;

    public BiblicaMisa getDomainModel() {
        BiblicaMisa theModel = lectura.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

}
