package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Patristica;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHPatristica {
    @Embedded
    public LHPatristicaOficioEntity lhPatristica;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyID",
            entity = HomiliaEntity.class
    )
    public HomiliaWithAll homiliaAll;

    @Relation(
            parentColumn = "responsoryFK",
            entityColumn = "responsoryID",
            entity = LHResponsorioLargoEntity.class
    )
    public LHResponsorioLargoEntity lhResponsorio;


    public Patristica getDomainModel(Integer timeId) {
        Patristica theModel= homiliaAll.getPatristicaDomainModel();
        theModel.setTema(lhPatristica.tema);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(timeId));
        return theModel;
    }
}
