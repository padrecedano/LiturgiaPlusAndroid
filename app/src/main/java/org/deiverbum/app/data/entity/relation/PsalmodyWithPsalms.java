package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHAntiphonEntity;
import org.deiverbum.app.data.entity.EpigraphEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LHThemeEntity;
import org.deiverbum.app.data.entity.PsalmEntity;
import org.deiverbum.app.data.entity.LHPsalmodyEntity;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class PsalmodyWithPsalms {
    @Embedded
    public LHPsalmodyEntity salmodia;
    @Relation(
            parentColumn = "groupFK",
            entityColumn = "groupID",
            entity = LHPsalmodyJoinEntity.class
    )
    public LHPsalmodyJoinEntity salmodiaEntity;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "psalmID",
            entity = PsalmEntity.class
    )
    public PsalmEntity psalmEntity;

    @Relation(
            parentColumn = "antiphonFK",
            entityColumn = "antiphonID",
            entity = LHAntiphonEntity.class
    )
    public LHAntiphonEntity antiphonEntity;

    @Relation(
            parentColumn = "themeFK",
            entityColumn = "themeID",
            entity = LHThemeEntity.class
    )
    public LHThemeEntity tema;

    @Relation(
            parentColumn = "epigraphFK",
            entityColumn = "epigraphID",
            entity = EpigraphEntity.class
    )
    public EpigraphEntity epigrafe;

    public String getEpigrafe(){
        return epigrafe!=null ? epigrafe.getEpigrafe() : "";
    }

    public String getSalmoText(){
            return (psalmEntity !=null) ? psalmEntity.getSalmo() : "";
    }

    public String getRef(){
            return (psalmEntity !=null) ? psalmEntity.getSalmoRef() : "";
    }

    public String getAntifona(){
            return (antiphonEntity !=null) ? antiphonEntity.getAntifona() : "";
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
