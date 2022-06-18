package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class InvitatorioWithAntifona {
    @Embedded
    public InvitatorioEntity invitatorio;

    @Relation(
            parentColumn = "casoId",
            entityColumn = "casoFK",
            entity = LHInvitatorioJoinEntity.class
    )
    public LHInvitatorioJoinEntity invitatorioEntity;

    @Relation(
            parentColumn = "salmoFK",
            entityColumn = "salmoId",
            entity = SalmoEntity.class
    )
    public SalmoEntity salmo;

    public String getAntifona() {
        return "antifona.getAntifona();";
    }
}
