package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_OFICIO;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class OficioEaster extends BreviaryHour
{

    private LHInvitatory invitatorio;
    private LHOfficeOfReadingEaster officeOfReadingEaster;

    public OficioEaster() {
    }


    @SuppressWarnings("unused")
    public void setOficioLecturas(LHOfficeOfReadingEaster officeOfReadingEaster) {
        this.officeOfReadingEaster = officeOfReadingEaster;
    }

    public SpannableStringBuilder getTituloHora() {

        return Utils.toH1Red(TITLE_OFICIO);
    }

    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(TITLE_OFICIO);
    }


    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {

//            sb.append(today.getAllForView());
            sb.append(LS2);


            sb.append(getTituloHora());
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);
/*
            sb.append(getSaludoOficio());
            sb.append(LS2);

            sb.append(invitatorio.getAll());
            sb.append(LS2);

            sb.append(himno.getAll());
            sb.append(LS2);
            sb.append(salmodia.getAll(1));
            sb.append(LS2);
*/
            sb.append(officeOfReadingEaster.getAll(6));
/*
            if (teDeum.status) {
                sb.append(teDeum.getAll());
            }

            sb.append(oracion.getAll());
            sb.append(LS2);
            sb.append(getConclusionHorasMayores());*/
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }


    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(officeOfReadingEaster.getAllForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }
}