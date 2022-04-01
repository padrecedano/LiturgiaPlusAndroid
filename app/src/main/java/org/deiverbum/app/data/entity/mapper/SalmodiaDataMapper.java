package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.entity.TodayWithOficio;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link Salmodia} (in the data layer) to
 * {@link Salmodia} in the
 * domain layer.
 */
public class SalmodiaDataMapper {

    SalmodiaDataMapper() {

    }


    public List<Salmo> transformSalmos(List<SalmodiaWithSalmos> salmos) {
        final List<Salmo> salmosList = new ArrayList<>();
        for (SalmodiaWithSalmos salmoEntity : salmos) {
            final Salmo s = new Salmo();
            s.setSalmo(salmoEntity.salmo.getSalmo());
            s.setRef(salmoEntity.salmo.getSalmoRef());
            s.setAntifona(salmoEntity.antifonaEntity.getAntifona());
            if(salmoEntity.tema!=null){
                s.setTema(salmoEntity.tema.getTema());
            }

            if(salmoEntity.epigrafe!=null){
                s.setEpigrafe(salmoEntity.epigrafe.getEpigrafe());
            }

                s.setParte(salmoEntity.salmodia.getParte());


            salmosList.add(s);
        }
return salmosList;
    }


}
