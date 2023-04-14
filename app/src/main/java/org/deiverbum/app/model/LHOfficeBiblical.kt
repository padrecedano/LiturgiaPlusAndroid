package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_RESPONSORY;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class LHOfficeBiblical extends Biblical {
    private String tema;
    private LHResponsory responsorioLargo;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTemaForRead() {
        return tema + ".";
    }

    public LHResponsory getResponsorioLargo() {
        return responsorioLargo;
    }

    public void setResponsorioLargo(LHResponsory responsorioLargo) {
        this.responsorioLargo = responsorioLargo;
    }


    public String getHeader() {
        return "PRIMERA LECTURA";
    }


    public String getResponsorioHeaderForRead() {
        return Utils.pointAtEnd(TITLE_RESPONSORY);
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     *
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    @Override
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.formatTitle(getHeader()));
        sb.append(LS2);
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        sb.append(responsorioLargo.getAll());
        return sb;
    }

    /**
     * <p>Obtiene la lectura bíblica completa formateada para la lectura de voz.</p>
     *
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    @Override
    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.pointAtEnd(getHeader()));
        sb.append(book.getForRead());
        sb.append(getTemaForRead());
        sb.append(getTextoForRead());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        sb.append(getResponsorioLargo().getAllForRead());
        return sb;
    }

    public Integer getOrden() {
        return this.order;
    }

    public void setOrden(Integer orden) {
        this.order = orden;
    }
}