package org.deiverbum.app.model;

import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class HomilyAll {
    public String padre;
    public String texto;
    public String tema;
    public String obra;
    public String fecha;


    public String getPadre() {
        return padre;
    }
    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    @SuppressWarnings("unused")
    public Spanned getTemaSpan() {
        return Utils.toRed(tema);
    }

    @SuppressWarnings("unused")
    public Spanned getObraSpan() {
        return Utils.toH4Red(obra);
    }

    @SuppressWarnings("unused")
    public Spanned getFechaSpan() {
        if (fecha.equals("0000-00-00") || fecha.equals("")) {
            return Utils.fromHtml("");
        }
        return Utils.toH4Red("\n" + fecha + "\n");
    }



    public Spanned getTexto() {
         return Utils.fromHtml(texto);
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }
}
