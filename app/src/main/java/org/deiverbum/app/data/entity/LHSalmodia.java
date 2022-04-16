package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
            parentColumn = "grupoId",
            entityColumn = "grupoFK",
            entity = SalmodiaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
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
            salmosList.add(s);
        }


        //salmosList.sort(Comparator.comparing(Classname::getName));
        //Collections.sort(list, comparing(ClassName::getName).reversed());
        theModel.setSalmos(salmosList);
        return theModel;

    }


}
