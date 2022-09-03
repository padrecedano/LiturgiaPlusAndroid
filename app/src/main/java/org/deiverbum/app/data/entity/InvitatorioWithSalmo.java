package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class InvitatorioWithSalmo {
    @Embedded
    public InvitatorioEntity invitatorio;

    @Relation(
            parentColumn = "caseID",
            entityColumn = "caseFK",
            entity = LHInvitatorioJoinEntity.class
    )
    public LHInvitatorioJoinEntity invitatorioEntity;

    @Relation(
            parentColumn = "psalmFK",
            entityColumn = "psalmID",
            entity = SalmoEntity.class
    )
    public SalmoEntity salmo;

    public String getSalmo() {

        return salmo.getSalmo();
    }
}
