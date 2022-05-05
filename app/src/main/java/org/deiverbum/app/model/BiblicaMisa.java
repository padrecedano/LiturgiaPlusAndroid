package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class BiblicaMisa extends Biblica{
    private String tema;
    //private Integer orden;

    public String getTema() {
        return tema;
    }
    public String getTemaForRead() {
        return tema+".";
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
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(libro.getLiturgyName());
        sb.append("    ");
        //sb.append(Utils.toRed(getCapitulo()));
        //sb.append(", ");
        sb.append(Utils.toRed(getRef()));
        //sb.append(Utils.toRed(getVersoFinal()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        //sb.append(responsorio.getAll());
        return sb;
    }

    /**
     * <p>Obtiene la lectura bíblica completa formateada para la lectura de voz.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    public SpannableStringBuilder getAllForRead(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getLibroForRead());
        sb.append(getTemaForRead());
        sb.append(getTexto());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        //sb.append(getResponsorio().getAllForRead());
        return sb;
    }


    public Integer getOrden() {
        return this.orden;
    }

    public void setOrden(Integer orden) {
        this.orden=orden;
    }
}
