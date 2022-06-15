package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link Salmodia} (in the data layer) to
 * {@link org.deiverbum.app.model.Salmodia} in the
 * domain layer.
 */
@Singleton
public class SalmodiaEntityDataMapper {
    private MutableLiveData<Salmodia> mData;

    @Inject
    SalmodiaEntityDataMapper() {
        mData=new MutableLiveData<>();
    }


    private List<Salmo> transformSalmos(List<SalmodiaWithSalmos> salmos) {
        final List<Salmo> salmosList = new ArrayList<>();
        for (SalmodiaWithSalmos salmoEntity : salmos) {
            final Salmo s = new Salmo();
            s.setSalmo(salmoEntity.getSalmoText());
            s.setRef(salmoEntity.getRef());
            //Log.d("a",s.)
            s.setAntifona(salmoEntity.getAntifona());
            if(salmoEntity.tema!=null){
                s.setTema(salmoEntity.getTema());
            }

            if(salmoEntity.epigrafe!=null){
                s.setEpigrafe(salmoEntity.getEpigrafe());
            }

                s.setParte(salmoEntity.getParte());


            salmosList.add(s);
        }
return salmosList;
    }


}
