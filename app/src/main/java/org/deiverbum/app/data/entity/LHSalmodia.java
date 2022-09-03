package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHSalmodia {
    @Embedded
    public LHSalmodiaJoinEntity salmodia;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = SalmodiaEntity.class
    )
    public List<SalmodiaWithSalmos> salmos;

    public Salmodia getDomainModel(){
        Salmodia theModel=new Salmodia();
        theModel.setTipo(salmodia.getTipo());
        List<Salmo> salmosList = new ArrayList<>();
        for (SalmodiaWithSalmos salmo : salmos) {
            Salmo s = new Salmo();
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
