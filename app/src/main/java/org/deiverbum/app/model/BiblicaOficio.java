package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class BiblicaOficio extends Biblica {
    private String tema;
    private ResponsorioLargo responsorioLargo;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTemaForRead() {
        return tema+".";
    }

    public ResponsorioLargo getResponsorioLargo() {
        return responsorioLargo;
    }

    public void setResponsorioLargo(ResponsorioLargo responsorioLargo) {
        this.responsorioLargo=responsorioLargo;
    }

    //@Override
    public SpannableStringBuilder getHeaderForReview() {
        String s=String.format("%s lectura",Utils.getOrdinal(orden)).toUpperCase();
        return Utils.formatTitle(s);
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("PRIMERA LECTURA");
    }

    public SpannableStringBuilder getResponsorioHeader() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.toRed(String.format("%-15s", "Responsorio")));
        sb.append(Utils.toRed(getCita()));
        return sb;
    }

    public String getResponsorioHeaderForRead() {
        return "Responsorio.";
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    @Override
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(libro.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        //sb.append(", ");
        ////sb.append(Utils.toRed(getVersoFinal()));
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
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    @Override
    public SpannableStringBuilder getAllForRead(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(libro.getForRead());
        sb.append(getTemaForRead());
        sb.append(getTexto());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        sb.append(getResponsorioLargo().getAllForRead());
        return sb;
    }

    public Integer getOrden() {
        return this.orden;
    }

    public void setOrden(Integer orden) {
        this.orden=orden;
    }
}
