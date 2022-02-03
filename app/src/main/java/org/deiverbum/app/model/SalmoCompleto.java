package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

/**
 * @deprecated  A partir de la v. 2022.01 - Reemplazar por {@link Salmo}
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
        if (orden != null || !orden.equals("")) {
            return orden;
        } else {
            return null;
        }
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getAntifona() {
        if (antifona != null || !antifona.equals("")) {

            return antifona;
        } else {
            return null;
        }
    }


    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }


    public SpannableStringBuilder getRef() {
        if (ref != null || !ref.equals("")) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(Utils.fromHtml(ref));

            return new SpannableStringBuilder(Utils.toRedHtml(Utils.getFormato(ref)));//Utils.ssbRed((SpannableStringBuilder) Utils.fromHtml(ref));
        } else {
            return null;
        }
    }


    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTema() {
        if (tema != null || !tema.equals("")) {

            return tema;
        } else {
            return null;
        }

    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getIntro() {
        if (intro != null || !intro.equals("")) {
            return intro;
        } else {
            return null;
        }
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getParte() {
        if (parte != null || !parte.equals("")) {
            return parte;
        } else {
            return null;
        }

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