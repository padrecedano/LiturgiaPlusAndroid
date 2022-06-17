package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Himno;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class OficioVersoAll {
    @Embedded
    public LHOficioVersoJoinEntity theJoin;
    @Relation(
            parentColumn = "versoFK",
            entityColumn = "versoId",
            entity = LHOficioVersoEntity.class
    )
    public LHOficioVersoEntity theEntity;

    public String getDomainModel(){
        return theEntity.getResponsorio();
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
