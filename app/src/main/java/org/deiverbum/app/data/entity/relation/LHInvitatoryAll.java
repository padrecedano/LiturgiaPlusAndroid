package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHAntiphonEntity;
import org.deiverbum.app.data.entity.LHInvitatoryEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.model.LHInvitatory;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class LHInvitatoryAll {
    @Embedded
    public LHInvitatoryJoinEntity salmodia;
    @Relation(
            parentColumn = "caseFK",
            entityColumn = "caseID",
            entity = LHInvitatoryEntity.class
    )
    public InvitatoryWithPsalm salmo;

    @Relation(
            parentColumn = "antiphonFK",
            entityColumn = "antiphonID",
            entity = LHAntiphonEntity.class
    )
    public LHAntiphonEntity antifona;

    public LHInvitatory getDomainModel(){
        LHInvitatory dm=new LHInvitatory();
        dm.setAntifona(antifona.getAntifona());
        dm.setSalmo(salmo.getSalmo());
        dm.setRef(salmo.salmo.getSalmoRef());
        return dm;
    }
}
