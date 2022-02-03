package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;


public class Himno {
    private String texto;

    public Himno() {
    }

    public Spanned getTextoSpan() {
        return Utils.fromHtml(Utils.getFormato(texto));
    }

    //@PropertyName("himno.texto")
    public String getTexto() {
        return texto;
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("HIMNO");
    }


    public String getHeaderForRead() {
        return "Himno.";
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getTextoSpan());
        return sb;
    }


    public String getAllForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getTextoSpan());
        return sb.toString();
    }

}
