package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

/**
 * @deprecated  A partir de la v. 2022.01 - Reemplazar por {@link LHPsalm}
 */
@Deprecated
public class SalmoCompleto {
    private String orden;
    private String antifona;
    private String ref;
    private String tema;
    private String intro;
    private String parte;
    private String salmo;

    public String getOrden() {
       return this.orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    @SuppressWarnings("unused")
    public String getAntifona() {
        return this.antifona;
    }


    @SuppressWarnings("unused")
    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }


    public SpannableStringBuilder getRef() {
        if (ref != null ) {
            return new SpannableStringBuilder(Utils.toRedHtml(Utils.getFormato(ref)));//Utils.ssbRed((SpannableStringBuilder) Utils.fromHtml(ref));
        } else {
            return null;
        }
    }


    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTema() {
       return this.tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getIntro() {
            return this.intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getParte() {
            return this.parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getSalmo() {
        return salmo;
    }

    public void setSalmo(String salmo) {
        this.salmo = salmo;
    }

    public Spanned getTextos() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        Spanned str = Utils.fromHtml(Utils.getFormato(intro));
        ssb.append(str);
        return Utils.ssbSmallSize(ssb);
    }


}