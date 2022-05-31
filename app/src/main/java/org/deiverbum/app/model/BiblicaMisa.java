package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;
import static org.deiverbum.app.utils.Utils.normalizeEnd;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class BiblicaMisa extends Biblica{
    private String tema;

    public String getTema() {
        return tema;
    }

    public String getTemaForRead() {
        return normalizeEnd(tema);
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(LS);
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(libro.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getReferencia()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS2);
        return sb;
    }

    /**
     * <p>Obtiene la lectura bíblica completa formateada para la lectura de voz.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    @Override
    public SpannableStringBuilder getAllForRead(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.normalizeEnd(findOrden(getOrden())));
        sb.append(getLibroForRead());
        sb.append(getTemaForRead());
        sb.append(getTextoForRead());
        return sb;
    }
    @Override
    public SpannableStringBuilder getHeader() {

        String header="";
        if (this.orden >= 1 && this.orden <= 19) {
            header="PRIMERA LECTURA";
        }

        if (this.orden >= 20 && this.orden <= 29) {
            header="SALMO RESPONSORIAL";
        }

        if (this.orden >= 30 && this.orden <= 39) {
            header="SEGUNDA LECTURA";
        }
        if (this.orden >= 40 ) {
            header="EVANGELIO";
        }

        return Utils.formatTitle(header);
    }

    public String findOrden(int orden) {
        String orderText;
        if (orden <= 19) {
            orderText = "Primera Lectura";
        } else if (orden <= 29) {
            orderText = "Salmo Responsorial";
        } else if (orden <= 39) {
            orderText = "Segunda Lectura";
        } else {
            orderText = "Evangelio";
        }
        return orderText;
    }

}
