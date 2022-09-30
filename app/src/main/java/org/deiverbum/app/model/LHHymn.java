package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_HYMN;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class LHHymn {
    public Integer hymnID;
    private String hymn;

    public LHHymn() {
    }

    public LHHymn(String himno) {
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
        return Utils.formatTitle(TITLE_HYMN);
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_HYMN);
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getTextoSpan());
        return sb;
    }

    public String getAllForRead() {
        return getHeaderForRead() +
                getTextoSpan();
    }
}