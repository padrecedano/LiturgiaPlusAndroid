package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Salmo {
    private String orden;
    protected String antifona;
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
            return "";
        }
    }

    public String getAntifonaForRead() {
        if (antifona != null || !antifona.equals("")) {
            return antifona;
        } else {
            return "";
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

    /**
     *
     * @return Texto al final de cada salmo
     * @since 2022.01
     */
    public Spanned getFinSalmo() {
        String fin = "Gloria al Padre, y al Hijo, y al Espíritu Santo." + BR
                + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
        return Utils.fromHtml(fin);
    }

    public String getFinSalmoForRead() {
        String fin = "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.";
        return fin;
    }

    /**
     *
     * @return La rúbrica cuando no se dice Gloria en los salmos.
     * @sice 2022.01
     */
    public SpannableStringBuilder getNoGloria() {
        SpannableStringBuilder sb = new SpannableStringBuilder("No se dice Gloria");
        return Utils.toRedNew(sb);
    }

}