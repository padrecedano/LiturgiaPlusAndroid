package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Responsorio;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHPatristica {
    @Embedded
    public LHPatristicaEntity lhPatristica;

    @Relation(
            parentColumn = "homiliaFK",
            entityColumn = "homiliaId",
            entity = HomiliaEntity.class
    )
    public HomiliaWithAll homiliaAll;
/*
    @Relation(
            parentColumn = "obraFK",
            entityColumn = "obraId",
            entity = ObraEntity.class
    )
    public ObraEntity obra;

    @Relation(
            parentColumn = "padreFK",
            entityColumn = "padreId",
            entity = PadreEntity.class
    )
    public PadreEntity padre;
*/
    @Relation(
            parentColumn = "responsorioFK",
            entityColumn = "responsorioId",
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
