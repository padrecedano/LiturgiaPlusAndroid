package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.entity.TodayWithOficio;
import org.deiverbum.app.data.entity.UserWithPlaylistsAndSongs;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Transforma entidades  {@link TodayWithOficio} (capa de datos)
 * en objetos {@link Oficio} (capa de dominio).
 */
@Singleton
public class OficioDataMapper {
    private MutableLiveData<Oficio> mData;

    @Inject
    OficioDataMapper() {
        mData = new MutableLiveData<>();
    }

    /**
     * Método principal de transformación.
     *
     * @param theEntity Entidad a transformar.
     * @return {@link MutableLiveData<Oficio>} si hay un objeto
     * {@link TodayWithOficio} válido.
     */
    public MutableLiveData<Oficio> transform(TodayWithOficio theEntity) {

        if (theEntity != null) {
            MetaLiturgia meta=new MetaLiturgia();
            meta.setFecha("20220325");
            Oficio oficio = new Oficio();
            oficio.setMetaLiturgia(meta);
            oficio.setHimno(new Himno(theEntity.himno.getHimno()));
            Salmodia salmodia = new Salmodia();
            SalmodiaDataMapper dmSalmodia = new SalmodiaDataMapper();
            salmodia.setSalmos(dmSalmodia.transformSalmos(theEntity.salmodia));
            oficio.setSalmodia(salmodia);
            mData.setValue(oficio);
        }
        return mData;
    }
}
