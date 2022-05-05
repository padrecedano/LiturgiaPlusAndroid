package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Evangelio extends BiblicaMisa{

    public String antifona;

    public String getAntifona() {
        return antifona;
    }

    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }



    @Override
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.formatSubTitle("evangelio del día"));
        sb.append(Utils.LS2);
        sb.append(libro.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getRef()));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(Utils.getFormato(getTexto())));
        return sb;
    }

    public SpannableStringBuilder getEvangelioForRead() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append("EVANGELIO DEL DÍA.");
        sb.append(libro.getLiturgyName());
        sb.append(getAllForRead());
        sb.append("Palabra del Señor.");
        sb.append("Gloria a ti, Señor Jesús.");
        return sb;
    }

    //@Override
    public SpannableStringBuilder getAllDELETE() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(libro.getName());
        sb.append("    ");
        sb.append(Utils.toRed(getCapitulo()));
        sb.append(", ");
        sb.append(Utils.toRed(getVersoInicial()));
        sb.append(Utils.toRed(getVersoFinal()));
        sb.append(LS2);
        //sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        return sb;
    }

}