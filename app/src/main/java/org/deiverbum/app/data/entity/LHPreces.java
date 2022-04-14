package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Preces;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHPreces {
    @Embedded
    public LHPrecesJoinEntity lhPatristica;

    @Relation(
            parentColumn = "precesFK",
            entityColumn = "precesId",
            entity = LHPrecesEntity.class
    )
    public LHPrecesEntity preces;
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



    public Preces getDomainModel() {
        Preces theModel=new Preces();
        theModel.setIntro(preces.intro);
        theModel.setTexto(preces.preces);
        return theModel;
    }
}
