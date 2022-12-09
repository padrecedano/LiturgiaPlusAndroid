package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Evangelio extends MassReading {

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
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(Utils.getFormato(getTexto())));
        return sb;
    }

}