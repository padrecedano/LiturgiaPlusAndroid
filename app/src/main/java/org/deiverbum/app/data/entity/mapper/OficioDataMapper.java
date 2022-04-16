package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.TodayLaudes;
import org.deiverbum.app.data.entity.TodayMixto;
import org.deiverbum.app.data.entity.TodayOficio;
import org.deiverbum.app.data.entity.TodayTercia;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Misa;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.TeDeum;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Transforma entidades  {@link TodayOficio} (capa de datos)
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
     * {@link TodayOficio} válido.
     */
    public MutableLiveData<Oficio> transform(TodayOficio theEntity) {

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

    public MutableLiveData<Laudes> transformLaudes(TodayLaudes theEntity) {
        MutableLiveData<Laudes> liveData = new MutableLiveData<>();;
        Laudes laudes=new Laudes();
        laudes.setInvitatorio(theEntity.getInvitatorio());
        laudes.setMetaLiturgia(theEntity.getMetaLiturgia());
        laudes.setSanto(theEntity.getSanto());
        laudes.setHimno(theEntity.getHimno());
        MisaLecturas bm=theEntity.getMisaLecturas();
        Log.d("LecturasABC", theEntity.getMisaLecturas().getAllForView().toString());

        laudes.setSalmodia(theEntity.getSalmodia());
        laudes.setLecturaBreve(theEntity.getBiblica());
        laudes.setBenedictus(theEntity.getBenedictus());
        laudes.setPreces(theEntity.getPreces());
        LHOracionDataMapper dmOracion=new LHOracionDataMapper();
        laudes.setOracion(dmOracion.transform(theEntity.lhOracion));

        //oficio.
        liveData.setValue(laudes);
        return  liveData;
    }

    public MutableLiveData<Mixto> transformMixto(TodayMixto theEntity) {
        MutableLiveData<Mixto> liveData = new MutableLiveData<>();;
        Mixto mixto=new Mixto();
        Oficio oficio = new Oficio();
        OficioLecturas ol=
                new OficioLecturas(theEntity.getOficioBiblica(),
                        theEntity.getOficioPatristica());
        ol.setResponsorio(theEntity.getOficioResponsorio());
        oficio.setOficioLecturas(ol);
        TeDeum teDeum=new TeDeum(theEntity.today.oTeDeum);
        oficio.setTeDeum(teDeum);

        Laudes laudes=new Laudes();
        mixto.setMetaLiturgia(theEntity.getMetaLiturgia());
        mixto.setSanto(theEntity.getSanto());

        mixto.setInvitatorio(theEntity.getInvitatorio());

        laudes.setHimno(theEntity.getHimno());
        laudes.setInvitatorio(theEntity.getInvitatorio());
        laudes.setHimno(theEntity.getHimno());
        SalmodiaDataMapper dmSalmodia = new SalmodiaDataMapper();
        MisaLecturas bm=theEntity.getMisaLecturas();
        //Log.d("LecturasABC",theEntity.getMisaLecturas().getAllForView().toString());
Misa misa=new Misa();
misa.setMisaLecturas(bm);

        laudes.setSalmodia(theEntity.getSalmodia());
        laudes.setLecturaBreve(theEntity.getBiblica());
        laudes.setBenedictus(theEntity.getBenedictus());
        laudes.setPreces(theEntity.getPreces());
        LHOracionDataMapper dmOracion=new LHOracionDataMapper();
        laudes.setOracion(dmOracion.transform(theEntity.lhOracion));
        mixto.setLaudes(laudes);
        mixto.setOficio(oficio);
        mixto.setMisa(misa);
        //oficio.
        liveData.setValue(mixto);
        return  liveData;
    }

    public MutableLiveData<Intermedia> transformTercia(TodayTercia theEntity) {
        MutableLiveData<Intermedia> liveData = new MutableLiveData<>();;
        Intermedia laudes=new Intermedia();
        laudes.setMetaLiturgia(theEntity.getMetaLiturgia());
        laudes.setSanto(theEntity.getSanto());
        laudes.setHimno(theEntity.getHimno());
        SalmodiaDataMapper dmSalmodia = new SalmodiaDataMapper();
        laudes.setSalmodia(theEntity.getSalmodia());
        laudes.setLecturaBreve(theEntity.getBiblica());
        LHOracionDataMapper dmOracion=new LHOracionDataMapper();
        laudes.setOracion(dmOracion.transform(theEntity.lhOracion));
        //oficio.
        liveData.setValue(laudes);
        return  liveData;
    }
    public MutableLiveData<Oficio> transformB(TodayOficio theEntity) {
        MetaLiturgia meta=new MetaLiturgia();
        meta.setFecha("20220325");
        Oficio oficio = new Oficio();
        oficio.setMetaLiturgia(theEntity.getMetaLiturgia());
        InvitatorioDataMapper dmInvitatorio = new InvitatorioDataMapper();
        oficio.setInvitatorio(dmInvitatorio.transform(theEntity.invitatorio));
        oficio.setHimno(theEntity.getHimno());
        oficio.setSalmodia(theEntity.getSalmodia());

        OficioLecturas ol=
                new OficioLecturas(theEntity.getBiblica(),theEntity.getPatristica());
        ol.setResponsorio(theEntity.getOficioResponsorio());
        oficio.setOficioLecturas(ol);
        TeDeum teDeum=new TeDeum(theEntity.today.oTeDeum);
        oficio.setTeDeum(teDeum);
        LHOracionDataMapper dmOracion=new LHOracionDataMapper();
        oficio.setOracion(dmOracion.transform(theEntity.lhOracion));

        //oficio.
        mData.setValue(oficio);
        return  mData;
    }
}
