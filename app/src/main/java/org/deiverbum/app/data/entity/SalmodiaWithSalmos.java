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
            parentColumn = "pericopaFK",
            entityColumn = "pericopaId",
            entity = BibliaLecturaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public BibliaLecturaEntity salmoEntity;

    @Relation(
            parentColumn = "antifonaFK",
            entityColumn = "antifonaId",
            entity = AntifonaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public AntifonaEntity antifonaEntity;

    @Relation(
            parentColumn = "temaFK",
            entityColumn = "temaId",
            entity = TemaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public TemaEntity tema;

    @Relation(
            parentColumn = "epigrafeFK",
            entityColumn = "epigrafeId",
            entity = EpigrafeEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public EpigrafeEntity epigrafe;

    public String getEpigrafe(){
        if(epigrafe!=null) {
            return epigrafe.getEpigrafe();
        }else{
            return "*";
        }
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
