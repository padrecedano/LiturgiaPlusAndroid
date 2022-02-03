package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Evangelio {
    public String libro;
    public String ref;
    public String texto;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getEvangelioForRead() {
        return Utils.stripQuotation(texto);
    }


    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.formatSubTitle("evangelio del día"));
        sb.append(Utils.LS2);
        sb.append(libro);
        sb.append("    ");
        sb.append(Utils.toRed(ref));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(Utils.getFormato(texto)));
        return sb;
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append("EVANGELIO DEL DÍA.");
        sb.append(libro);
        sb.append(getEvangelioForRead());
        sb.append("Palabra del Señor.");
        sb.append("Gloria a ti, Señor Jesús.");
        return sb;
    }
}