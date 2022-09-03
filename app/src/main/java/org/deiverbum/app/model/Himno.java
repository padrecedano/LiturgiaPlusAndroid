package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;


public class Himno {
    private String hymn;

    public Himno() {
    }

    public Himno(String himno) {
        this.hymn=himno;
    }

    public Spanned getTextoSpan() {
        return Utils.fromHtml(Utils.getFormato(hymn));
    }

    //@PropertyName("himno.texto")
    public String getTexto() {
        return hymn;
    }

    public String getHimno() {
        return hymn;
    }

    public void setHimno(String himno) {
        this.hymn = himno;
    }

    public void setTexto(String texto) {
        this.hymn = texto;
    }

    public String getHymn() {
        return hymn;
    }

    public void setHymn(String hymn) {
        this.hymn = hymn;
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
