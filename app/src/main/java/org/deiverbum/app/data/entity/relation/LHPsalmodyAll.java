package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHPsalmodyEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.model.LHPsalm;
import org.deiverbum.app.model.LHPsalmody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
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
            s.setAntiphon(salmo.getAntifona());
            s.setTheme(salmo.getTema());
            s.setEpigraph(salmo.getEpigrafe());
            s.setPart(salmo.getParte());
            s.setTheOrder(salmo.getOrden());
            salmosList.add(s);
        }
        theModel.setSalmos(salmosList);
        theModel.sort();
        return theModel;
    }


}
