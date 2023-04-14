package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHInvitatoryEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.PsalmEntity;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class InvitatoryWithPsalm {
    @Embedded
    public LHInvitatoryEntity invitatorio;

    @Relation(
            parentColumn = "caseID",
            entityColumn = "caseFK",
            entity = LHInvitatoryJoinEntity.class
    )
    public LHInvitatoryJoinEntity invitatorioEntity;

    @Relation(
            parentColumn = "psalmFK",
            entityColumn = "psalmID",
            entity = PsalmEntity.class
    )
    public PsalmEntity salmo;

    public String getSalmo() {
        return salmo.getSalmo();
    }
}
