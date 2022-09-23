package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

/*
Sustituida por {@link BibleReading_}
 */
@Deprecated
public class LiturgiaPalabra {

    private int tipo;
    private List<BibleReading> lecturas;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @SuppressWarnings("unused")
    public String getTipos() {
        return "tipo";
    }

    public List<BibleReading> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<BibleReading> lecturas) {
        this.lecturas = lecturas;
    }

    public SpannableStringBuilder getEvangelio() {
        String txtLectura;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BibleReading l : lecturas) {
            if (l.getOrden() == 40) {
                sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
                sb.append(Utils.LS2);
                sb.append(Utils.toRed(l.getTema()));
                sb.append(Utils.LS2);
                txtLectura = Utils.getFormato(l.getTexto());
                sb.append(Utils.fromHtml(txtLectura));
                sb.append(Utils.LS2);
            }
        }

        return sb;
    }


    @SuppressWarnings("unused")
    public SpannableStringBuilder getLiturgiaPalabra() {
        String txtLectura;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BibleReading l : lecturas) {
            sb.append(Utils.LS2);
            sb.append(Utils.toH3Red(findOrden(l.getOrden())));
            sb.append(Utils.LS2);
            sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
            sb.append(Utils.LS2);
            sb.append(Utils.toRed(l.getTema()));
            sb.append(Utils.LS2);
            txtLectura = Utils.getFormato(l.getTexto());
            sb.append(Utils.fromHtml(txtLectura));
            sb.append(Utils.LS2);
        }

        return sb;
    }


    @SuppressWarnings("unused")
    public SpannableStringBuilder getLiturgiaPalabraforRead() {
        String txtLectura;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BibleReading l : lecturas) {
            sb.append(findOrden(l.getOrden()));
            sb.append(Utils.LS2);
            sb.append(l.getLibro());
            sb.append(Utils.LS2);
            sb.append(Utils.LS2);
            sb.append(l.getTema());
            sb.append(Utils.LS2);
            txtLectura = l.getTexto();
            sb.append(Utils.fromHtml(txtLectura));
            sb.append(Utils.LS2);
        }

        return sb;
    }

    /*Bug fixed on v. 2020.1.3*/
    public String findOrden(int orden) {
        String orderText;

        if (orden <= 19) {
            orderText = "Primera Lectura";
        } else if (orden <= 29) {
            orderText = "Salmo Responsorial";
        } else if (orden <= 39) {
            orderText = "Segunda Lectura";
        } else {
            orderText = "Evangelio";
        }

        return orderText;


    }

}
