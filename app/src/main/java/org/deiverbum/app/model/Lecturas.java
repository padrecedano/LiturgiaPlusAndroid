package org.deiverbum.app.model;
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
public class Lecturas extends Liturgia{
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
    private String getTituloForRead() {
        return "Lecturas de la Misa.";
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {

            sb.append(hoy.getForViewMisa());
        sb.append(LS2);

        sb.append(getTitulo());
        sb.append(LS2);

        for (BiblicaMisa l : lecturas) {
            sb.append(l.getAll());
        }
        } catch (Exception e){
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }


    @NonNull
    public String toString(){
        return Objects.requireNonNull(this.getClass().getCanonicalName());
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(hoy.getAllForRead());
        sb.append(getTituloForRead());
        for (BiblicaMisa l : lecturas) {
            sb.append(l.getAllForRead());
        }
        return sb;
    }


}

