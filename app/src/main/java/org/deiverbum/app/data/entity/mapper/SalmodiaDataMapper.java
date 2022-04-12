package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
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
    public Salmodia transformSalmodia(List<SalmodiaWithSalmos> salmos) {
        Salmodia salmodia=new Salmodia();
        salmodia.setSalmos(transformSalmos(salmos));
        return salmodia;
    }


    public List<Salmo> transformSalmos(List<SalmodiaWithSalmos> salmos) {
        final List<Salmo> salmosList = new ArrayList<>();
        for (SalmodiaWithSalmos salmo : salmos) {
            final Salmo s = new Salmo();
            s.setSalmo(salmo.getSalmoText());
            s.setRef(salmo.getRef());
            s.setAntifona(salmo.getAntifona());
                s.setTema(salmo.getTema());
                s.setEpigrafe(salmo.getEpigrafe());
                s.setParte(salmo.getParte());
            salmosList.add(s);
        }
return salmosList;
    }


}
