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
            parentColumn = "groupFK",
            entityColumn = "groupID",
            entity = LHSalmodiaJoinEntity.class
    )
    public LHSalmodiaJoinEntity salmodiaEntity;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "psalmID",
            entity = SalmoEntity.class
    )
    public SalmoEntity salmoEntity;

    @Relation(
            parentColumn = "antiphonFK",
            entityColumn = "antiphonID",
            entity = AntifonaEntity.class
    )
    public AntifonaEntity antifonaEntity;

    @Relation(
            parentColumn = "themeFK",
            entityColumn = "themeID",
            entity = TemaEntity.class
    )
    public TemaEntity tema;

    @Relation(
            parentColumn = "epigraphFK",
            entityColumn = "epigraphID",
            entity = EpigrafeEntity.class
    )
    public EpigrafeEntity epigrafe;

    public String getEpigrafe(){
        return epigrafe!=null ? epigrafe.getEpigrafe() : "";
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
            return salmodia.getParte() ;
    }

    public String getOrden(){
        return (salmodia!=null) ? String.valueOf(salmodia.getOrden()) : "0";
    }

}
