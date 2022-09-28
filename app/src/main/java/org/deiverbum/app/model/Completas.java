package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_COMPLETAS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.util.SparseIntArray;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Completas extends BreviaryHour {
    private RitosIniciales ritosIniciales;
    private NuncDimitis nuncDimitis;
    private Conclusion conclusion;
    List<CompletasHimno> completasHimno;
    private List<CompletasDia> completasDias;
    private List<LHResponsoryShort> responsorio;
    private LiturgyTime finalTime;

    public List<LHResponsoryShort> getResponsorio() {
        return responsorio;
    }

    public Completas() {
    }

    public RitosIniciales getRitosIniciales() {
        return ritosIniciales;
    }

    public void setRitosIniciales(RitosIniciales ritosIniciales) {
        this.ritosIniciales = ritosIniciales;
    }

    public Prayer getOracionByDay() {
        return completasDias.get(hoy.getWeekDay()).getOracion();
    }

    public NuncDimitis getNuncDimitis() {
        return nuncDimitis;
    }

    public void setResponsorio(List<LHResponsoryShort> responsorio) {
        this.responsorio = responsorio;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(Conclusion conclusion) {
        this.conclusion = conclusion;
    }

    public LHHymn getHimno() {
        SparseIntArray mMap = new SparseIntArray();
        switch (finalTime.getTiempoId()) {
            case 1:
            case 2:
                mMap.put(0, 0);
                mMap.put(1, 1);
                mMap.put(2, 0);
                mMap.put(3, 1);
                mMap.put(4, 0);
                mMap.put(5, 1);
                mMap.put(6, 0);
                mMap.put(7, 0);
                break;
            case 3:
            case 4:
            case 5:
                mMap.put(0, 2);
                mMap.put(1, 3);
                mMap.put(2, 2);
                mMap.put(3, 3);
                mMap.put(4, 2);
                mMap.put(5, 3);
                mMap.put(6, 2);
                mMap.put(7, 2);
                break;
            case 6:
                mMap.put(0, 4);
                mMap.put(1, 5);
                mMap.put(2, 4);
                mMap.put(3, 5);
                mMap.put(4, 4);
                mMap.put(5, 5);
                mMap.put(6, 4);
                mMap.put(7, 4);
                break;
            default:
                mMap.put(0, 0);
                mMap.put(1, 6);
                mMap.put(2, 0);
                mMap.put(3, 6);
                mMap.put(4, 0);
                mMap.put(5, 6);
                mMap.put(6, 0);
                mMap.put(7, 0);
                break;
        }
        return completasHimno.get(hoy.weekDay).getHimno();
    }

    public List<CompletasDia> getCompletasDias() {
        return this.completasDias;
    }

    /**
     * Devuelve Lectura Breve y Responsorio Breve formateados para vista
     * Para el responsorio, determina el que corresponda, según sea o no
     * tiempo de Pascua (timeID=6)
     *
     * @return una cadena formateada con la Lectura Breve y el Responsorio
     */
    public SpannableStringBuilder getLecturaSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        int mIndex = (hoy.getFinalTime().getTiempoId() == 6) ? 1 : 0;
        LHResponsoryShort mResponsorio = responsorio.get(mIndex);
        BiblicalShort mLectura = completasDias.get(hoy.weekDay).getLecturaBreve();
        mLectura.setResponsorio(mResponsorio);
        ssb.append(mLectura.getAll());
        return ssb;
    }

    public SpannableStringBuilder getLecturaForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int mIndex = (finalTime.getTiempoId() == 6) ? 1 : 0;
        LHResponsoryShort mResponsorio = responsorio.get(mIndex);
        BiblicalShort mLectura = completasDias.get(hoy.weekDay).getLecturaBreve();
        ssb.append(mLectura.getAllForRead());
        return ssb;
    }

    public SpannableStringBuilder getForView() {
       // try {
            this.finalTime = hoy.getFinalTime();
            SpannableStringBuilder sb = new SpannableStringBuilder();
            RitosIniciales ri = getRitosIniciales();
            Kyrie kyrie = ri.getKyrie();
            this.himno = getHimno();
            this.salmodia = getCompletasDias().get(hoy.weekDay).getSalmodia();
            NuncDimitis nuncDimitis = getNuncDimitis();
            Conclusion conclusion = getConclusion();
            sb.append(hoy.getAllForView());
            sb.append(Utils.LS2);
            sb.append(getTituloHora());
            sb.append(Utils.LS2);
            sb.append(getSaludoDiosMio());
            sb.append(Utils.LS2);
            sb.append(kyrie.getAll());
            sb.append(Utils.LS2);
            sb.append(himno.getAll());
            sb.append(LS2);
            sb.append(salmodia.getAll());
            sb.append(Utils.LS2);
            sb.append(getLecturaSpan());
            sb.append(Utils.LS2);
            sb.append(nuncDimitis.getAll(finalTime.getTiempoId()));
            sb.append(Utils.LS2);
            sb.append(getOracionByDay().getAll());
            sb.append(Utils.LS2);
            sb.append(conclusion.getAll(finalTime.getTiempoId()));
            sb.append(Utils.LS2);
            return sb;
       /* } catch (Exception e) {
            return new SpannableStringBuilder(e.toString());
        }*/
    }

    public SpannableStringBuilder getTituloHora() {
        return Utils.toH1Red(TITLE_COMPLETAS);
    }

    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(TITLE_COMPLETAS);
    }

    public StringBuilder getForRead() {
        try {
            StringBuilder sb = new StringBuilder();
            RitosIniciales ri = getRitosIniciales();
            Kyrie kyrie = ri.getKyrie();
            this.himno = getHimno();
            this.salmodia = getCompletasDias().get(hoy.weekDay).getSalmodia();
            NuncDimitis nuncDimitis = getNuncDimitis();
            Conclusion conclusion = getConclusion();
            sb.append(hoy.getAllForRead());
            sb.append(getTituloHoraForRead());
            sb.append(getSaludoDiosMioForRead());
            sb.append(kyrie.getAllForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());
            sb.append(getLecturaForRead());
            sb.append(nuncDimitis.getAllForRead());
            sb.append(getOracionByDay().getAllForRead());
            sb.append(conclusion.getAllForRead(finalTime.getTiempoId()));
            return sb;
        } catch (Exception e) {
            return new StringBuilder(e.toString());
        }
    }
}