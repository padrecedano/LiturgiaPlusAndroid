package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Preces {
    public String intro;
    public String texto;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public SpannableStringBuilder getAll() {

        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        String[] introArray = intro.split("\\|");
        if (introArray.length == 3) {
            sb.append(introArray[0]);
            sb.append(LS2);
            sb.append(Utils.fromHtml(String.format("<i>%s</i>",introArray[1])));
            sb.append(LS2);
            sb.append(getTexto());
            sb.append(LS2);
            sb.append(introArray[2]);
        } else {
            sb.append(getIntro());
            sb.append(LS2);
            sb.append(getTexto());
        }
        return sb;

    }

    public Spanned getTexto() {
        return Utils.fromHtml(Utils.getFormato(texto));
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("PRECES");
    }

    public String getHeaderForRead() {
        return "PRECES.";
    }


    /**
     * @deprecated desde la versión 2022.01 - Reemplazar por {@link Preces#getAll()}
     * @return
     */
    @Deprecated
    public String getPreces() {
        return "";
    }

    public SpannableStringBuilder getAllForRead() {

        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(LS2);
        String[] introArray = intro.split("\\|");
        if (introArray.length == 3) {
            sb.append(introArray[0]);
            sb.append(introArray[1]);
            sb.append(getTexto());
            sb.append(introArray[2]);
        } else {
            sb.append(getIntro());
            sb.append(getTexto());
        }
        return sb;

    }
}
