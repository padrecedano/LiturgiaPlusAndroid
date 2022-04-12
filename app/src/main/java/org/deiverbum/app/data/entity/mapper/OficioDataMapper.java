package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.LaudesOfToday;
import org.deiverbum.app.data.entity.OficioOfToday;
import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.entity.UserWithPlaylistsAndSongs;
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.TeDeum;

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
    public MutableLiveData<Oficio> transform(OficioOfToday theEntity) {

        if (theEntity != null) {
            MetaLiturgia meta=new MetaLiturgia();
            meta.setFecha("20220325");
            Oficio oficio = new Oficio();
            oficio.setMetaLiturgia(meta);
            //oficio.setHimno(new Himno(theEntity.himno.getHimno()));
            InvitatorioDataMapper dmInvitatorio = new InvitatorioDataMapper();
            BiblicaDataMapper dmBiblica=new BiblicaDataMapper();
            PatristicaDataMapper dmPatristica=new PatristicaDataMapper();
            oficio.setInvitatorio(dmInvitatorio.transform(theEntity.invitatorio));
            Salmodia salmodia = new Salmodia();
            SalmodiaDataMapper dmSalmodia = new SalmodiaDataMapper();

            //salmodia.setSalmos(dmSalmodia.transformSalmos(theEntity
            // .salmodia));
            oficio.setSalmodia(salmodia);
            Patristica olPatristica=new Patristica();
            oficio.setOficioLecturas(new OficioLecturas(dmBiblica.transform(theEntity.biblica),olPatristica));
            mData.setValue(oficio);
        }
        return mData;
    }

    public MutableLiveData<Laudes> transformLaudes(LaudesOfToday theEntity) {
        MutableLiveData<Laudes> liveData = new MutableLiveData<>();;
        Laudes laudes=new Laudes();
        MetaLiturgia meta=new MetaLiturgia();
        meta.setFecha("20220325");
        laudes.setMetaLiturgia(theEntity.getMetaLiturgia());
        laudes.setHimno(theEntity.getHimno());
        SalmodiaDataMapper dmSalmodia = new SalmodiaDataMapper();
        laudes.setSalmodia(dmSalmodia.transformSalmodia(theEntity.salmos));
        LHOracionDataMapper dmOracion=new LHOracionDataMapper();
        laudes.setOracion(dmOracion.transform(theEntity.lhOracion));

        //oficio.
        liveData.setValue(laudes);
        return  liveData;
    }
    public MutableLiveData<Oficio> transformB(OficioOfToday theEntity) {
        MetaLiturgia meta=new MetaLiturgia();
        meta.setFecha("20220325");
        Oficio oficio = new Oficio();
        oficio.setMetaLiturgia(theEntity.getMetaLiturgia());
        InvitatorioDataMapper dmInvitatorio = new InvitatorioDataMapper();
        oficio.setInvitatorio(dmInvitatorio.transform(theEntity.invitatorio));
        oficio.setHimno(theEntity.getHimno());
        SalmodiaDataMapper dmSalmodia = new SalmodiaDataMapper();
        oficio.setSalmodia(dmSalmodia.transformSalmodia(theEntity.salmos));
        BiblicaDataMapper dmBiblica=new BiblicaDataMapper();
        PatristicaDataMapper dmPatristica=new PatristicaDataMapper();
        TeDeum teDeum=new TeDeum(theEntity.today.oTeDeum);
        oficio.setTeDeum(teDeum);
        LHOracionDataMapper dmOracion=new LHOracionDataMapper();
        oficio.setOracion(dmOracion.transform(theEntity.lhOracion));
        OficioLecturas ol=
                new OficioLecturas(theEntity.getBiblica(),theEntity.getPatristica());
        ol.setResponsorio(theEntity.getOficioResponsorio());
        oficio.setOficioLecturas(ol);
        //oficio.
        mData.setValue(oficio);
        return  mData;
    }
}
