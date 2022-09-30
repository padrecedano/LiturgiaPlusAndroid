package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_RESPONSORY;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

@SuppressWarnings("SameReturnValue")
public class LHOfficePatristic {
    public String padre;
    public String obra;
    public String fuente;
    public String tema;
    public String texto;
    public String ref;
    private Integer orden;

    public LHResponsory responsorioLargo;

    public LHOfficePatristic() {
    }
    public String getPadre() {
        return padre;
    }

    public String getPadreForRead() {
        return Utils.pointAtEnd(padre);
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getObra() {
        return obra;
    }

    public String getObraForRead() {
        return Utils.pointAtEnd(obra);
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
        return Utils.pointAtEnd(tema);
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

    public LHResponsory getResponsorioLargo() {
        return responsorioLargo;
    }

    public void setResponsorioLargo(LHResponsory responsorioLargo) {
        this.responsorioLargo = responsorioLargo;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("SEGUNDA LECTURA");
    }

    public String getResponsorioHeaderForRead() {
        return Utils.pointAtEnd(TITLE_RESPONSORY);
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
        sb.append(getResponsorioHeaderForRead());
        sb.append(responsorioLargo.getAllForRead());
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
        sb.append(responsorioLargo.getAll());
        return sb;
    }

    public void setOrden(Integer orden) {
        this.orden=orden;
    }

    public Integer getOrden() {
        return this.orden;
    }
}