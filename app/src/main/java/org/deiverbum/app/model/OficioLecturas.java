package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.data.entity.BiblicaOficioWithResponsorio;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class OficioLecturas {
    @SuppressWarnings("unused")
    private String responsorio;
    private List<BiblicaOficio> biblica;
    private List<Patristica> patristica;
    private TeDeum teDeum;


    public OficioLecturas() {
    }

    /*public OficioLecturas(BiblicaOficio biblica, Patristica patristica) {
        this.biblica.add(biblica);
        this.patristica=patristica;
    }
*/
    public String getResponsorio() {
        return responsorio;
    }

    public SpannableStringBuilder getResponsorioForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        String r;
        if (responsorio.contains("|")) {
            r = responsorio.replaceAll("\\|", "<br>");
        } else {
            r = responsorio;
        }
        ssb.append(Utils.fromHtml("<p>" + r + "</p>"));

        return ssb;
    }

    public SpannableStringBuilder getResponsorioSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        if (responsorio.contains("|")) {
            String[] textParts = responsorio.split("\\|");
            if (textParts.length == 2) {
                ssb.append(Utils.toRed("V. "));
                ssb.append(textParts[0]);
                ssb.append(Utils.LS);
                ssb.append(Utils.toRed("R. "));
                ssb.append(textParts[1]);
            } else {
                ssb.append(responsorio);

            }
        } else {
            ssb.append(responsorio);
        }

        return ssb;//responsorio;
    }


    @SuppressWarnings("unused")
    public List<Patristica> getPatristica() {
        return patristica;
    }

    @SuppressWarnings("unused")
    public void setPatristica(List<Patristica> patristica) {
        this.patristica = patristica;
    }

    @SuppressWarnings("unused")
    public List<BiblicaOficio> getBiblica() {
        return biblica;
    }

    @SuppressWarnings("unused")
    public void setBiblica(List<BiblicaOficio> biblica) {
        this.biblica = biblica;
    }

    @SuppressWarnings("unused")
    public TeDeum getTeDeum() {
        return teDeum;
    }

    @SuppressWarnings("unused")
    public void setTeDeum(TeDeum teDeum) {
        this.teDeum = teDeum;
    }

    /**
     * @deprecated
     * A partir de la versión 2022.1 cambiar por {@link OficioLecturas#getHeader()}
     * @return Cadena con el título
     */
    public String getTitulo() {
        return "lecturas del oficio";
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatSubTitle("lecturas del oficio");
    }

    public String getHeaderForRead() {
        return "Lecturas del oficio.";
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getResponsorioSpan());
        sb.append(LS2);
        sb.append(getAllBiblica());
        sb.append(getAllPatristica());
        return sb;
    }

    public SpannableStringBuilder getAllBiblica() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (BiblicaOficio oneBiblica : this.biblica) {
            sb.append(oneBiblica.getAll());
        }
        return sb;
    }

    public SpannableStringBuilder getAllPatristica() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (Patristica theModel : this.patristica) {
            sb.append(theModel.getAll());
        }
        return sb;
    }

    public String getAllForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getResponsorioForRead());
        //sb.append(biblica.getAllForRead());
        //sb.append(patristica.getAllForRead());
        return sb.toString();
    }

    public void setResponsorio(String responsorio) {
        this.responsorio=responsorio;
    }
}

