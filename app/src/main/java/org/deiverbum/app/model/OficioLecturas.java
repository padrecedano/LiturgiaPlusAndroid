package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class OficioLecturas {
    private String responsorio;
    private Biblica biblica;
    private Patristica patristica;
    private TeDeum teDeum;


    public OficioLecturas() {
    }

    public String getResponsorio() {
        return responsorio;
    }

    public SpannableStringBuilder getResponsorioForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        String r = "";
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


    public Patristica getPatristica() {
        return patristica;
    }

    public void setPatristica(Patristica patristica) {
        this.patristica = patristica;
    }

    public Biblica getBiblica() {
        return biblica;
    }

    public void setBiblica(Biblica biblica) {
        this.biblica = biblica;
    }

    public TeDeum getTeDeum() {
        return teDeum;
    }

    public void setTeDeum(TeDeum teDeum) {
        this.teDeum = teDeum;
    }

    /**
     * @deprecated
     * A partir de la versión 2022.01 cambiar por {@link OficioLecturas#getHeader()}
     * @return
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
        sb.append(biblica.getAll());
        sb.append(patristica.getAll());
        return sb;
    }

    public String getAllForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getResponsorioForRead());
        sb.append(biblica.getAllForRead());
        sb.append(patristica.getAllForRead());
        return sb.toString();
    }
}

