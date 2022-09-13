package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
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
