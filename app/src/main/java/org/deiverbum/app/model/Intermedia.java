package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_NONA;
import static org.deiverbum.app.utils.Constants.TITLE_SEXTA;
import static org.deiverbum.app.utils.Constants.TITLE_TERCIA;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Intermedia extends BreviaryHour {
    private BiblicalShort lecturaBreve;

    public Intermedia() {
    }

    public String getTituloHora() {
        switch (typeID) {
            case 3:
                return TITLE_TERCIA;
            case 4:
                return TITLE_SEXTA;
            case 5:
                return TITLE_NONA;
            default:
                return "";
        }
    }
    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(getTituloHora());
    }

    public SpannableStringBuilder getTituloHoraForView() {
        return Utils.toH1Red(getTituloHora());
    }

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            salmodia.normalizeByTime(liturgyTime.getTimeID());
            //sb.append(today.getAllForView());
            sb.append(Utils.LS2);

            sb.append(getTituloHoraForView());
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);

            sb.append(getSaludoDiosMio());
            sb.append(LS2);

            sb.append(himno.getAll());
            sb.append(LS2);

            sb.append(salmodia.getAll(getHourIndex()));
            sb.append(LS);

            sb.append(lecturaBreve.getAllWithHourCheck(hourId));
            sb.append(LS2);

            sb.append(oracion.getAll());
            sb.append(LS2);

            sb.append(getConclusionHoraMenor());

        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            //sb.append(today.getAllForRead());
            sb.append(getTituloHoraForRead());
            sb.append(getSaludoDiosMioForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead(getHourIndex()));
            sb.append(lecturaBreve.getAllForRead());
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHoraMenorForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    /**
     * Devuelve el índice de la hora para fines de LHPsalmody
     * por ejemplo, para determinar la antífona única en los
     * tiempos litúrgicos en que ésta aplica
     *
     * @return Un entero con el índice 0
     * @since 2022.1
     */
    private int getHourIndex() {
        switch (typeID) {
            case 4:
                return 1;
            case 5:
                return 2;
            case 3:
            default:
                return 0;
        }
    }

    @SuppressWarnings("unused")
    public BiblicalShort getLecturaBreve() {
        return lecturaBreve;
    }

    public void setLecturaBreve(BiblicalShort lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

}