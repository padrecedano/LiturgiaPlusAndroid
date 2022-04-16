package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.Evangelio;
import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaWithLecturas {
    @Embedded
    public MisaLecturaEntity misaLectura;

    @Relation(
            parentColumn = "pericopaFK",
            entityColumn = "pericopaId",
            entity = BibliaLecturaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public LecturaWithLibro lectura;
/*
    @Relation(
            parentColumn = "libroFK",
            entityColumn = "libroId",
            entity = BibliaLibroEntity.class
    )
    public BibliaLibroEntity lecturaa;

*/
public BiblicaMisa getDomainModel() {
    BiblicaMisa theModel= lectura.getDomainModelMisa();
    theModel.setTema(misaLectura.getTema());
    theModel.setOrden(misaLectura.getOrden());
    return theModel;
}

    public Evangelio getDomainModelEvangelio() {
        Evangelio theModel= lectura.getDomainModelMisaEvangelio();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }
}
