package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_OFFICE_OF_READING;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class LHOfficeOfReading {
    @SuppressWarnings("unused")
    private String responsorio;
    private List<LHOfficeBiblical> biblica;
    private List<LHOfficePatristic> patristica;
    private TeDeum teDeum;

    public LHOfficeOfReading() {
    }

    public String getResponsorio() {
        return responsorio;
    }

    public String getResponsorioForRead() {
        String r;
        if (responsorio.contains("|")) {
            r = responsorio.replaceAll("\\|", "");
        } else {
            r = responsorio;
        }
        return Utils.pointAtEnd(r);
    }

    public SpannableStringBuilder getResponsorioSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        if (responsorio.contains("|")) {
            String[] textParts = responsorio.split("\\|");
            if (textParts.length == 2) {
                ssb.append(Utils.toRed("V. "));
                ssb.append(textParts[0]);
                ssb.append(Utils.LS);
                ssb.append(Utils.toRed("R. "));
                ssb.append(textParts[1]);
            } else {
                ssb.append(responsorio);
            }
        } else {
            ssb.append(responsorio);
        }
        return ssb;
    }

    @SuppressWarnings("unused")
    public List<LHOfficePatristic> getPatristica() {
        return patristica;
    }

    @SuppressWarnings("unused")
    public void setPatristica(List<LHOfficePatristic> patristica) {
        this.patristica = patristica;
    }

    @SuppressWarnings("unused")
    public List<LHOfficeBiblical> getBiblica() {
        return biblica;
    }

    @SuppressWarnings("unused")
    public void setBiblica(List<LHOfficeBiblical> biblica) {
        this.biblica = biblica;
    }

    @SuppressWarnings("unused")
    public TeDeum getTeDeum() {
        return teDeum;
    }

    @SuppressWarnings("unused")
    public void setTeDeum(TeDeum teDeum) {
        this.teDeum = teDeum;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatSubTitle(Utils.toLower(TITLE_OFFICE_OF_READING));
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_OFFICE_OF_READING);
    }

    public SpannableStringBuilder getAll(int calendarTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getResponsorioSpan());
        sb.append(LS2);
        sb.append(getAllBiblica(calendarTime));
        sb.append(getAllPatristica(calendarTime));
        return sb;
    }

    public SpannableStringBuilder getAllBiblica(int calendarTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (LHOfficeBiblical oneBiblica : this.biblica) {
            oneBiblica.getResponsorioLargo().normalizeByTime(calendarTime);
            sb.append(oneBiblica.getAll());
        }
        return sb;
    }

    public SpannableStringBuilder getAllBiblicaForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (LHOfficeBiblical oneBiblica : this.biblica) {
            sb.append(oneBiblica.getAllForRead());
        }
        return sb;
    }

    public SpannableStringBuilder getAllPatristica(int calendarTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (LHOfficePatristic theModel : this.patristica) {
            theModel.getResponsorioLargo().normalizeByTime(calendarTime);
            sb.append(theModel.getAll());
        }
        return sb;
    }

    public SpannableStringBuilder getAllPatristicaForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (LHOfficePatristic theModel : this.patristica) {
            sb.append(theModel.getAllForRead());
        }
        return sb;
    }

    public String getAllForRead() {
        return getHeaderForRead() +
                getResponsorioForRead() +
                getAllBiblicaForRead() +
                getAllPatristicaForRead();
    }

    public void setResponsorio(String responsorio) {
        this.responsorio=responsorio;
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */

    public void normalizeByTime(int calendarTime) {
        for (LHOfficeBiblical oneBiblica : this.biblica) {
            oneBiblica.getResponsorioLargo().normalizeByTime(calendarTime);
        }
        responsorio=Utils.replaceByTime(responsorio,calendarTime);
    }
}