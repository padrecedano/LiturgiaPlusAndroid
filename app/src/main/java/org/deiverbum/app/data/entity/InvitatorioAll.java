package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Invitatorio;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class InvitatorioAll {
    @Embedded
    public LHInvitatorioJoinEntity salmodia;
    @Relation(
            parentColumn = "caseFK",
            entityColumn = "caseID",
            entity = InvitatorioEntity.class
    )
    public InvitatorioWithSalmo salmo;

    @Relation(
            parentColumn = "antiphonFK",
            entityColumn = "antiphonID",
            entity = AntifonaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public AntifonaEntity antifona;

    public Invitatorio getDomainModel(){
        Invitatorio dm=new Invitatorio();
        dm.setAntifona(antifona.getAntifona());
        dm.setSalmo(salmo.getSalmo());
        return dm;
    }
}
