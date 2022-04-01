package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.entity.UserWithPlaylistsAndSongs;
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

    /**
     * Transform a {@link org.deiverbum.app.data.entity.SalmodiaEntity} into an
     * {@link org.deiverbum.app.model.Salmodia}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link org.deiverbum.app.model.Salmodia} if valid
     * {@link org.deiverbum.app.data.entity.SalmodiaEntity}
     * otherwise null.
     */
    public MutableLiveData<Salmodia> transform(UserWithPlaylistsAndSongs userEntity) {

        if (userEntity != null) {

            Salmodia salmodia = new org.deiverbum.app.model.Salmodia();
            salmodia.setSalmos(transformSalmos(userEntity.salmos));
            mData.setValue(salmodia);

        }
        return mData;
    }

    private List<Salmo> transformSalmos(List<SalmodiaWithSalmos> salmos) {
        final List<Salmo> salmosList = new ArrayList<>();
        for (SalmodiaWithSalmos salmoEntity : salmos) {
            final Salmo s = new Salmo();
            s.setSalmo(salmoEntity.salmo.getSalmo());
            s.setRef(salmoEntity.salmo.getSalmoRef());
            //Log.d("a",s.)
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
