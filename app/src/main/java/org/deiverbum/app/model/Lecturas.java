package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;

import org.deiverbum.app.utils.Utils;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class Lecturas {
    private int tipo;
    private List<BiblicaMisa> lecturas;

    public MetaLiturgia getMetaLiturgia() {
        return metaliturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaliturgia) {
        this.metaliturgia = metaliturgia;
    }

    @SuppressWarnings("unused")
    private MetaLiturgia metaliturgia;

    public Lecturas(){}


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

    public List<BiblicaMisa> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<BiblicaMisa> lecturas) {
        this.lecturas = lecturas;
    }

    public SpannableStringBuilder getEvangelio() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BiblicaMisa l : lecturas) {
            if (l.getOrden() == 40) {
                sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
                sb.append(LS2);
                sb.append(Utils.toRed(l.getTema()));
                sb.append(LS2);
                String txtLectura = Utils.getFormato(l.getTexto());
                sb.append(Utils.fromHtml(txtLectura));
                sb.append(LS2);
            }
        }

        return sb;
    }

    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red("LECTURAS DE LA MISA");
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(metaliturgia.getForViewMisa());
        sb.append(LS2);

        sb.append(getTitulo());
        sb.append(LS2);

        for (BiblicaMisa l : lecturas) {
            sb.append(l.getAll());
            /*
            sb.append(LS2);
            sb.append(Utils.toH3Red(findOrden(l.getOrden())));
            sb.append(LS2);
            sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
            sb.append(LS2);
            sb.append(Utils.toRed(l.getTema()));
            sb.append(LS2);
            //String txtLectura = Utils.getFormato(l.getTexto());
            sb.append(Utils.fromHtml(l.getTexto()));
            sb.append(LS2);*/
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

    @NonNull
    public String toString(){
        return "Test";
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BiblicaMisa l : lecturas) {
            sb.append(Utils.normalizeEnd(findOrden(l.getOrden())));
            //sb.append(Utils.normalizeEnd(l.getLibro()));
            sb.append(Utils.normalizeEnd(l.getTema()));
            sb.append(Utils.fromHtml(l.getTexto()));
        }

        return sb;
    }
}

