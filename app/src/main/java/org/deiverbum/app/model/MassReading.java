package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;
import static org.deiverbum.app.utils.Utils.normalizeEnd;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class MassReading extends Biblical implements Comparable<MassReading>{
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
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(LS2);
        if(tema!=null) {
            sb.append(Utils.toRed(getTema()));
            sb.append(LS2);
        }
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
        if (this.order >= 1 && this.order <= 19) {
            header="PRIMERA LECTURA";
        }

        if (this.order >= 20 && this.order <= 29) {
            header="SALMO RESPONSORIAL";
        }

        if (this.order >= 30 && this.order <= 39) {
            header="SEGUNDA LECTURA";
        }
        if (this.order >= 40 ) {
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

    @Override
    public int compareTo(MassReading e) {
        return this.getOrden().compareTo(e.getOrden());
    }
}