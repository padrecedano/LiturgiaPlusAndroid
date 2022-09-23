package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_MASS_READING;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;

import org.deiverbum.app.utils.Utils;

import java.util.List;
import java.util.Objects;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class BibleReading_ extends Liturgy {
    private int tipo;
    private List<MassReading> lecturas;

    public MetaLiturgia getMetaLiturgia() {
        return metaliturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaliturgia) {
        this.metaliturgia = metaliturgia;
    }

    @SuppressWarnings("unused")
    private MetaLiturgia metaliturgia;

    public BibleReading_() {
    }

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

    public List<MassReading> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<MassReading> lecturas) {
        this.lecturas = lecturas;
    }

    public SpannableStringBuilder getEvangelio() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (MassReading l : lecturas) {
            if (l.getOrden() == 40) {
                sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getCita()));
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
        return Utils.toH3Red(TITLE_MASS_READING);
    }

    public String getTituloForRead() {
        return Utils.pointAtEnd(TITLE_MASS_READING);
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {
            sb.append(hoy.getAllForView());
            sb.append(LS2);
            sb.append(getTitulo());
            sb.append(LS2);
            for (MassReading l : lecturas) {
                sb.append(l.getAll());
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }


    @NonNull
    public String toString() {
        return Objects.requireNonNull(this.getClass().getCanonicalName());
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {
            sb.append(hoy.getAllForRead());
            sb.append(getTituloForRead());
            for (MassReading l : lecturas) {
                sb.append(l.getAllForRead());
            }
        } catch (Exception e){
            sb.append(e.getMessage());
        }
        return sb;
    }

}

