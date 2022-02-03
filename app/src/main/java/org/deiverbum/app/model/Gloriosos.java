package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Gloriosos {
    public int id;
    public String titulo;
    public List<String> contenido;

    public SpannableStringBuilder getContenido() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");


        for (String s : contenido) {
            ssb.append(s);
            ssb.append(Utils.LS2);
        }
        return ssb;
    }

    public void setContenido(List<String> contenido) {
        this.contenido = contenido;
    }
}

