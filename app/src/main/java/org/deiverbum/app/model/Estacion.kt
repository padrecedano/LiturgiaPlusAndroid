package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Estacion {

    private String titulo;
    private String subtitulo;
    private TextoBiblico textoBiblico;
    private Meditacion meditacion;
    private String aclamaciones;
    private int r;
    private String canto;

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return this.subtitulo;
    }

    @SuppressWarnings("unused")
    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public TextoBiblico getTextoBiblico() {
        return this.textoBiblico;
    }

    @SuppressWarnings("unused")
    public void setTextoBiblico(TextoBiblico textoBiblico) {
        this.textoBiblico = textoBiblico;
    }

    @SuppressWarnings("unused")
    public Meditacion getMeditacion() {
        return this.meditacion;
    }

    @SuppressWarnings("unused")
    public void setMeditacion(Meditacion meditacion) {
        this.meditacion = meditacion;
    }

    @SuppressWarnings("unused")
    public String getAclamaciones() {
        return this.aclamaciones;
    }

    @SuppressWarnings("unused")
    public void setAclamaciones(String aclamaciones) {
        this.aclamaciones = aclamaciones;
    }

    public int getR() {
        return this.r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public String getCanto() {
        return this.canto;
    }

    @SuppressWarnings("unused")
    public void setCanto(String canto) {
        this.canto = canto;
    }

    public SpannableStringBuilder getMeditacionSpan(List<String> respuestas) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");

        ssb.append(Utils.toH4Red("Meditación"));
        ssb.append(LS2);
        ssb.append(Utils.fromHtml(meditacion.getTexto()));

        if (meditacion.getTipo() == 2) {
            ssb.append(LS);
            ssb.append(Utils.toH4Red("Oración"));
            ssb.append(LS2);
            ssb.append(Utils.fromHtml(meditacion.getTextoPost()));
        } else {
            ssb.append(LS2);
            ssb.append(Utils.toH4Red("Aclamaciones"));
            ssb.append(LS2);
            String[] textParts = meditacion.getTextoPost().split("\\|");
            for (String part : textParts) {
                ssb.append(Utils.fromHtml(Utils.getFormato(part)));
                ssb.append(LS2);
                ssb.append(Utils.toRed("R/. "));
                ssb.append(respuestas.get(r));
                ssb.append(LS2);
            }

        }
        return ssb;
    }


    public SpannableStringBuilder getTextoBiblicoSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append(textoBiblico.getLibro());
        ssb.append("     ");
        ssb.append(Utils.toRed(textoBiblico.getRef()));
        ssb.append(LS2);
        ssb.append(Utils.fromHtml(Utils.getFormato(textoBiblico.getTexto())));
        return ssb;
    }
}
