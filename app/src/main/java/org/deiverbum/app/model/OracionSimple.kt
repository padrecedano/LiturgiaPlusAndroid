package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;

public class OracionSimple {
    private String titulo;
    private String texto;
    private String info;

    public OracionSimple() {
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getInfo() {
        return this.info;
    }

    @SuppressWarnings("unused")
    public void setInfo(String info) {
        this.info = info;
    }

    public SpannableStringBuilder getForView(boolean nightMode) {
        ColorUtils.isNightMode = nightMode;

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.toH3Red(titulo));
        if (this.info != null) {
            sb.append(LS2);
            sb.append(Utils.toRed(getInfo()));
        }
        sb.append(LS2);
        sb.append(Utils.fromHtml(this.texto));
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitulo());
        sb.append(".");
        sb.append(Utils.fromHtml(this.texto));
        return sb;
    }
}