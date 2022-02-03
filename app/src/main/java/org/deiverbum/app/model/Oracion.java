package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Oracion {
    public String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("ORACIÓN");
    }

    public final Spanned getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
            sb.append(getHeader());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(texto));

        return sb;
    }

    public String getAllForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(getHeader());
        sb.append(".");
        sb.append(Utils.fromHtml(texto));
        return sb.toString();
    }
}
