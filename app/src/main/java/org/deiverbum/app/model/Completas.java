package org.deiverbum.app.model;

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


    public List<LHResponsoryShort> getResponsorio() {
        return responsorio;
    }

    public Completas() {
    }

    public RitosIniciales getRitosIniciales() {
        return ritosIniciales;
    }


        public Prayer getOracionByDay() {
        return completasDias.get(metaLiturgia.weekDay).getOracion();
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

        switch (metaLiturgia.idTiempo) {
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
        int i = mMap.get(metaLiturgia.weekDay);
        return completasHimno.get(i).getHimno();
    }


    public List<CompletasDia> getCompletasDias() {
        return this.completasDias;
    }




    /**
     * Devuelve BibleReading Breve y LHResponsoryShort formateados para vista
     * Para el responsorio, determina el que corresponda, según sea o no
     * tiempo de Pascua (timeID=6)
     * @return una cadena formateada con BibleReading y LHResponsoryShort
     */
    public SpannableStringBuilder getLecturaSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        int mIndex = (metaLiturgia.idTiempo == 6) ? 1 : 0;
        LHResponsoryShort mResponsorio = responsorio.get(mIndex);
        BiblicalShort mLectura = completasDias.get(metaLiturgia.weekDay).getLecturaBreve();
        mLectura.setResponsorio(mResponsorio);
        mLectura.setForma(String.valueOf(mResponsorio.getForma()));
        ssb.append(mLectura.getAll());
        return ssb;
    }

    public SpannableStringBuilder getLecturaForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        int mIndex = (metaLiturgia.idTiempo == 6) ? 1 : 0;
        LHResponsoryShort mResponsorio = responsorio.get(mIndex);
        BiblicalShort mLectura = completasDias.get(metaLiturgia.weekDay).getLecturaBreve();
        //mLectura.setResponsorio(mResponsorio);
        mLectura.setForma(String.valueOf(mResponsorio.getForma()));
        ssb.append(mLectura.getAllForRead());
        return ssb;
    }
/*
    @SuppressWarnings("unused")
    public BiblicalShort getLecturaBreve() {
        return lecturaBreve;
    }

    @SuppressWarnings("unused")
    public void setLecturaBreve(BiblicalShort lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

 */

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        RitosIniciales ri = getRitosIniciales();
        Kyrie kyrie = ri.getKyrie();
        this.himno = getHimno();
        this.salmodia = getCompletasDias().get(metaLiturgia.weekDay).getSalmodia();

        NuncDimitis nuncDimitis = getNuncDimitis();
        Conclusion conclusion = getConclusion();
        sb.append(metaLiturgia.getAll());
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

        sb.append(nuncDimitis.getAll(metaLiturgia.idTiempo));
        sb.append(Utils.LS2);

        sb.append(getOracionByDay().getAll());
        sb.append(Utils.LS2);

        sb.append(conclusion.getAll(metaLiturgia.idTiempo));
        sb.append(Utils.LS2);



        return sb;
    }

    private SpannableStringBuilder getTituloHora() {
        return Utils.toH3Red("COMPLETAS");
    }

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        RitosIniciales ri = getRitosIniciales();
        Kyrie kyrie = ri.getKyrie();
        this.himno = getHimno();
        this.salmodia = getCompletasDias().get(metaLiturgia.weekDay).getSalmodia();

        NuncDimitis nuncDimitis = getNuncDimitis();
        Conclusion conclusion = getConclusion();
        sb.append(metaLiturgia.getAllForRead());
        sb.append(getTituloHora());
        sb.append(".");

        sb.append(getSaludoDiosMioForRead());

        sb.append(kyrie.getAllForRead());

        sb.append(himno.getAllForRead());

        sb.append(salmodia.getAllForRead());

        sb.append(getLecturaForRead());

        sb.append(nuncDimitis.getAllForRead());

        sb.append(getOracionByDay().getAllForRead());

        sb.append(conclusion.getAllForRead(metaLiturgia.idTiempo));
        sb.append(Utils.LS2);

        return sb;
    }


}
