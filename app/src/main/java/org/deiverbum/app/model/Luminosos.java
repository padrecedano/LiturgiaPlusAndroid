package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Luminosos {
    public int id;
    public String titulo;
    public List<String> contenido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getContenido() {
        return contenido;
    }

    public void setContenido(List<String> contenido) {
        this.contenido = contenido;
    }

    public SpannableStringBuilder getTexto() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");


        for (String s : getContenido()) {

            ssb.append(s);
            ssb.append(Utils.LS2);

        }
        return ssb;
    }


}
