package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Intermedia extends BreviarioHora {
    private int hourId;
    private LecturaBreve lecturaBreve;

    public Intermedia() {
    }

    /**
     * Método que obtiene la conclusión de la hora
     *
     * @return Texto formateado con la conclusión de la hora
     * @since 2022.1
     */
    public static SpannableStringBuilder getConclusionHora() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.formatTitle("CONCLUSIÓN"));
        sb.append(LS2);
        sb.append(Utils.toRed("V. "));
        sb.append("Bendigamos al Señor.");
        sb.append(LS);
        sb.append(Utils.toRed("R. "));
        sb.append("Demos gracias a Dios.");
        return sb;
    }

    @SuppressWarnings("unused")
    public LecturaBreve getLecturaBreve() {
        return lecturaBreve;
    }


    public void setHourId(int hourId) {
        this.hourId = hourId;
    }

    public int getHourId() {
        return hourId;
    }

    public String getTitulo() {

        if (metaLiturgia.getHasSaint()) {
            return santo.getNombre() + LS2;
        } else {
            return metaLiturgia.getTitulo() + LS2;
        }
    }

    //TODO Obtener el título de la hora: Agregar el id en la respuesta de la API
    public String getTituloHora() {
        String titulo = "";
        switch (hourId) {
            case 3:
                titulo = "Hora Intermedia: Tercia";
                break;
            case 4:
                titulo = "Hora Intermedia: Sexta";
                break;
            case 5:
                titulo = "Hora Intermedia: Nona";
                break;

            default:
                break;

        }
        return titulo;
    }

    public SpannableStringBuilder getForView() {

        lecturaBreve.normalizeByTime(metaLiturgia.calendarTime);
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            sb.append(metaLiturgia.getAll());
            sb.append(Utils.LS2);

            sb.append(Utils.toH3Red(getTituloHora().toUpperCase()));
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);

            sb.append(getSaludoDiosMio());
            sb.append(LS2);

            sb.append(himno.getAll());
            sb.append(LS2);

            sb.append(salmodia.getAll(getHourIndex()));
            sb.append(LS);

            sb.append(lecturaBreve.getAll());
            sb.append(LS2);

            sb.append(oracion.getAll());
            sb.append(LS2);

            sb.append(getConclusionHora());

        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb;

    }

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(metaLiturgia.getAllForRead());
            sb.append(getTituloHora());
            sb.append(".");
            sb.append(getSaludoDiosMioForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead(getHourIndex()));
            sb.append(lecturaBreve.getAllForRead());
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHoraForRead());
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb;
    }

    @SuppressWarnings("unused")
    public void setLecturaBreve(LecturaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    /**
     * Devuelve el índice de la hora para fines de Salmodia
     * por ejemplo, para determinar la antífona única en los
     * tiempos litúrgicos en que ésta aplica
     *
     * @return Un entero con el índice 0
     * @since 2022.1
     */
    private int getHourIndex() {
        switch (hourId) {
            case 4:
                return 1;
            case 5:
                return 2;
            case 3:
            default:
                return 0;
        }
    }

    public static SpannableStringBuilder getConclusionHoraForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("<p>Bendigamos al Señor.</p>");
        ssb.append("<p>Demos gracias a Dios.</p>");
        return (SpannableStringBuilder) Utils.fromHtml(ssb.toString());
    }


}