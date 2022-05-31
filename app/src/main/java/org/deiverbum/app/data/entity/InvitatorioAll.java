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
            parentColumn = "grupoFK",
            entityColumn = "grupoId",
            entity = InvitatorioEntity.class
    )
    public InvitatorioWithAntifona joinEntity;

    @Relation(
            parentColumn = "salmoFK",
            entityColumn = "salmoId",
            entity = SalmoEntity.class
    )
    public SalmoEntity salmoEntity;

    public Invitatorio getDomainModel(){
        Invitatorio dm=new Invitatorio();
        dm.setAntifona(joinEntity.getAntifona());
        dm.setTexto(salmoEntity.getSalmo());
        dm.setSalmo(salmoEntity.getSalmo());

        return dm;

    }
/*
    @Relation(
            parentColumn = "antifonaFK",
            entityColumn = "antifonaId",
            entity = AntifonaEntity.class
    )
    public AntifonaEntity antifonaEntity;
*/

/*
    public String getEpigrafe(){
            return epigrafe.getEpigrafe();
    }

    public String getSalmoText(){
            return (salmoEntity!=null) ? salmoEntity.getSalmo() : "";
    }

    public String getRef(){
            return (salmoEntity!=null) ? salmoEntity.getSalmoRef() : "";
    }

    public String getAntifona(){
            return (antifonaEntity!=null) ? antifonaEntity.getAntifona() : "";
    }

    public String getTema(){
        return (tema!=null) ? tema.getTema() : "";
    }

    public String getParte(){
            return (salmodia!=null) ? salmodia.getParte() : "";
    }
*/

}
