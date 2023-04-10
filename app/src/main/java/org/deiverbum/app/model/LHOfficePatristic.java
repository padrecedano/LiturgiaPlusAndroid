package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_RESPONSORY;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

@SuppressWarnings("SameReturnValue")
public class LHOfficePatristic {
    public Integer groupFK;
    public Integer homilyFK;
    public Integer responsoryFK;
    public String source;
    public String theme;
    @Ignore
    public String padre;
    @Ignore
    public String obra;
    @Ignore
    public String text;
    @Ignore
    public String ref;
    @Ignore
    public PaterOpus paterOpus;
    @Ignore
    public LHResponsory responsorioLargo;
    private Integer theOrder;

    public LHOfficePatristic() {
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getPadreForRead() {
        return Utils.pointAtEnd(padre);
    }

    @SuppressWarnings("unused")
    public String getObra() {
        return paterOpus.getOpusForView();
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getObraForView() {
        return paterOpus.getOpusForView();
    }

    public String getObraForRead() {
        return Utils.pointAtEnd(obra);
    }

    @SuppressWarnings("unused")
    public String getSource() {
        return source;
    }

    @SuppressWarnings("unused")
    public void setSource(String source) {
        this.source = source;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTemaForRead() {
        return Utils.pointAtEnd(theme);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Spanned getTextForRead() {
        return Utils.fromHtmlForRead(text);
    }

    public Spanned getTextoSpan() {

        return Utils.fromHtml(text);
    }

    @SuppressWarnings("unused")
    public String getRef() {
        return ref;
    }

    @SuppressWarnings("unused")
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
     *
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getPadreForRead());
        sb.append(getObraForRead());
        sb.append(getTemaForRead());
        sb.append(getTextForRead());
        sb.append(getPadreForRead());
        sb.append(getResponsorioHeaderForRead());
        sb.append(responsorioLargo.getAllForRead());
        return sb;
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getObraForView());
        sb.append(Utils.LS2);
        sb.append(Utils.toSmallSizeRed(source));
        sb.append(LS2);
        sb.append(Utils.toRed(theme));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        sb.append(responsorioLargo.getAll());
        return sb;
    }

    public Integer getTheOrder() {
        return this.theOrder;
    }

    public void setTheOrder(Integer theOrder) {
        this.theOrder = theOrder;
    }
}