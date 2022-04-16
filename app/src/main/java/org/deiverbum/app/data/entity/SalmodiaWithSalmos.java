package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class SalmodiaWithSalmos {
    @Embedded
    public SalmodiaEntity salmodia;
    @Relation(
            parentColumn = "grupoFK",
            entityColumn = "grupoId",
            entity = LHSalmodiaJoinEntity.class
    )
    public LHSalmodiaJoinEntity salmodiaEntity;

    @Relation(
            parentColumn = "pericopaFK",
            entityColumn = "pericopaId",
            entity = BibliaLecturaEntity.class
    )
    public BibliaLecturaEntity salmoEntity;

    @Relation(
            parentColumn = "antifonaFK",
            entityColumn = "antifonaId",
            entity = AntifonaEntity.class
    )
    public AntifonaEntity antifonaEntity;

    @Relation(
            parentColumn = "temaFK",
            entityColumn = "temaId",
            entity = TemaEntity.class
    )
    public TemaEntity tema;

    @Relation(
            parentColumn = "epigrafeFK",
            entityColumn = "epigrafeId",
            entity = EpigrafeEntity.class
    )
    public EpigrafeEntity epigrafe;

    public String getEpigrafe(){
            return epigrafe.getEpigrafe();
    }

    public String getSalmoText(){
            return (salmoEntity!=null) ? salmoEntity.getTexto() : "";
    }

    public String getRef(){
            return (salmoEntity!=null) ? salmoEntity.getCita() : "";
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


}
