package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Patristica {
    public String padre;
    public String obra;
    public String fuente;
    public String tema;
    public String texto;
    public String ref;
    private Integer orden;

    public Responsorio responsorio;

    public Patristica() {
    }
    public String getPadre() {
        return padre;
    }

    public String getPadreForRead() {
        return String.format("%s.",padre);
    }


    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getObra() {
        return obra;
    }

    public String getObraForRead() {
        return String.format("%s.",obra);
    }


    public void setObra(String obra) {
        this.obra = obra;
    }

    @SuppressWarnings("unused")
    public String getFuente() {
        return fuente;
    }

    @SuppressWarnings("unused")
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getTema() {
        return tema;
    }

    public String getTemaForRead() {
        return String.format("%s.",tema);
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTexto() {
        return texto;
    }

    public Spanned getTextoSpan() {
       return Utils.fromHtml(Utils.getFormato(texto));
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Responsorio getResponsorio() {
        return responsorio;
    }


    public void setResponsorio(Responsorio responsorio) {
        this.responsorio = responsorio;
    }

    public SpannableStringBuilder getHeaderDelete() {

        return Utils.formatTitle("SEGUNDA LECTURA");
    }


    public SpannableStringBuilder getHeader() {
        String s=String.format("%s lectura",Utils.getOrdinal(orden)).toUpperCase();
        return Utils.formatTitle(s);
    }

    public String getHeaderForRead() {
        return "Segunda lectura.";
    }


        /**
         * <p>Obtiene la lectura patrística completa, incluyendo el responsorio, formateada para la vista.</p>
         * @since 2022.01
         * @return Un objeto {@link SpannableStringBuilder con el contenido.}
         */
    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getPadreForRead());
        sb.append(getObraForRead());
        sb.append(getTemaForRead());
        sb.append(getTexto());
        sb.append(getPadreForRead());
        sb.append(responsorio.getAllForRead());
        return sb;
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(padre);
        sb.append(", ");
        sb.append(obra);
        sb.append(Utils.LS);
        sb.append(Utils.toSmallSizeRed(fuente));
        sb.append(LS2);
        sb.append(Utils.toRed(tema));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        sb.append(responsorio.getAll());
        return sb;
    }

    public void setOrden(Integer orden) {
        this.orden=orden;
    }

    public Integer getOrden() {
        return this.orden;
    }
}
