package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_PRAYER;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Prayer {
    public String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle(TITLE_PRAYER);
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
        sb.append(Utils.pointAtEnd(TITLE_PRAYER));
        sb.append(Utils.fromHtml(texto));
        return sb.toString();
    }
}
