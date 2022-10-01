package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHPsalmodyEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.relation.PsalmodyWithPsalms;
import org.deiverbum.app.model.LHPsalm;
import org.deiverbum.app.model.LHPsalmody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHPsalmodyAll {
    @Embedded
    public LHPsalmodyJoinEntity salmodia;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = LHPsalmodyEntity.class
    )
    public List<PsalmodyWithPsalms> salmos;

    public LHPsalmody getDomainModel(){
        LHPsalmody theModel=new LHPsalmody();
        theModel.setTipo(salmodia.getTipo());
        List<LHPsalm> salmosList = new ArrayList<>();
        for (PsalmodyWithPsalms salmo : salmos) {
            LHPsalm s = new LHPsalm();
            s.setSalmo(salmo.getSalmoText());
            s.setRef(salmo.getRef());
            s.setAntifona(salmo.getAntifona());
            s.setTema(salmo.getTema());
            s.setEpigrafe(salmo.getEpigrafe());
            s.setParte(salmo.getParte());
            s.setOrden(salmo.getOrden());
            salmosList.add(s);
        }

        theModel.setSalmos(salmosList);
        theModel.sort();
        return theModel;

    }


}
