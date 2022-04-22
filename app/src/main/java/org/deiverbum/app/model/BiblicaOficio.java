package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class BiblicaOficio extends Biblica {
    protected BibliaLibro libro;
    private String tema;
    private Responsorio responsorio;
    private Integer orden;

    public BibliaLibro getLibro() {
        return libro;
    }

    public String getLibroForRead() {
        return libro+".";
    }


    public void setLibro(BibliaLibro libro) {
        this.libro = libro;
    }

    public String getTema() {
        return tema;
    }
    public String getTemaForRead() {
        return tema+".";
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Responsorio getResponsorio() {
        return responsorio;
    }

    public void setResponsorio(Responsorio responsorio) {
        this.responsorio=responsorio;
    }

    @Override
    public SpannableStringBuilder getHeader() {
        String s=String.format("%s lectura",Utils.getOrdinal(orden)).toUpperCase();
        return Utils.formatTitle(s);
    }

    public SpannableStringBuilder getResponsorioHeader() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.toRed(String.format("%-15s", "Responsorio")));
        sb.append(Utils.toRed(getRef()));
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
        sb.append(libro.getName());
        sb.append("    ");
        sb.append(Utils.toRed(getCapitulo()));
        sb.append(", ");
        sb.append(Utils.toRed(getVersoInicial()));
        sb.append(Utils.toRed(getVersoFinal()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        sb.append(responsorio.getAll());
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
        sb.append(getLibroForRead());
        sb.append(getTemaForRead());
        sb.append(getTexto());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        sb.append(getResponsorio().getAllForRead());
        return sb;
    }

    public Integer getOrden() {
        return this.orden;
    }

    public void setOrden(Integer orden) {
        this.orden=orden;
    }
}
