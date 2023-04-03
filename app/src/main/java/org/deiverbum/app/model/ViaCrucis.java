package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;

import java.util.List;

@SuppressWarnings("SameReturnValue")
public class ViaCrucis {

    private String subTitulo;
    private String fecha;
    private String autor;
    private IntroViaCrucis introViaCrucis;
    private String adoramus;
    private List<String> respuestas;
    private List<Estacion> estaciones;
    private String oracion;

    public String getTitulo() {
        return "Vía Crucis";
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    @SuppressWarnings("unused")
    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @SuppressWarnings("unused")
    public IntroViaCrucis getIntroViaCrucis() {
        return this.introViaCrucis;
    }

    public void setIntro(IntroViaCrucis introViaCrucis) {
        this.introViaCrucis = introViaCrucis;
    }

    public SpannableStringBuilder getAdoramus() {
        String[] textParts = adoramus.split("\\|");
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.toRed("V/. "));
        sb.append(textParts[0]);
        sb.append(LS2);
        sb.append(Utils.toRed("R/. "));
        sb.append(textParts[1]);
        return sb;//this.adoramus;
    }

    @SuppressWarnings("unused")
    public void setAdoramus(String adoramus) {
        this.adoramus = adoramus;
    }

    @SuppressWarnings("unused")
    public List<String> getRespuestas() {
        return this.respuestas;
    }

    @SuppressWarnings("unused")
    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public SpannableStringBuilder getAllEstaciones() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
         for (Estacion e : estaciones) {
                sb.append(LS);
                sb.append(Utils.toH3Red(e.getTitulo()));
                sb.append(LS2);
                sb.append(Utils.toH3(e.getSubtitulo()));
                sb.append(LS2);
                sb.append(getAdoramus());
                sb.append(LS2);
                sb.append(e.getTextoBiblicoSpan());
                sb.append(LS2);
                sb.append(e.getMeditacionSpan(respuestas));
                sb.append(LS);
                sb.append(PadreNuestro.getTexto());
                sb.append(LS2);
                sb.append(Utils.toH4Red("Estrofa del Stabat Mater"));
                sb.append(LS2);
                sb.append(Utils.fromHtml(Utils.getFormato(e.getCanto())));
                sb.append(LS2);
            }
        return sb;
    }

    @SuppressWarnings("unused")
    public StringBuilder getAllEstacionesForRead() {
        StringBuilder sb = new StringBuilder();
            String[] textParts = adoramus.split("\\|");
            String txtAdoramus = textParts[0] + "." + textParts[1] + ".";
            for (Estacion e : estaciones) {
                sb.append(e.getTitulo());
                sb.append(".");
                sb.append(e.getSubtitulo());
                sb.append(".");
                sb.append(txtAdoramus);
                sb.append(e.getTextoBiblico());
                sb.append(".");
                sb.append(e.getMeditacionSpan(respuestas));
                sb.append(PadreNuestro.getTexto());
                sb.append("Estrofa del Stabat Mater.");
                sb.append(Utils.fromHtml(Utils.getFormato(e.getCanto())));
        }
        return sb;
    }

    @SuppressWarnings("unused")
    public List<Estacion> getEstaciones() {
        return this.estaciones;
    }

    @SuppressWarnings("unused")
    public void setEstaciones(List<Estacion> estaciones) {
        this.estaciones = estaciones;
    }

    public String getOracion() {
        return this.oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public SpannableStringBuilder getForView(boolean isNightMode) {
        ColorUtils.isNightMode=isNightMode;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.toH2Red(getTitulo()));
        sb.append(LS2);
        sb.append(Utils.toH3(getSubTitulo()));
        sb.append(LS);
        sb.append(Utils.toH4(getFecha()));
        sb.append(LS);
        sb.append(Utils.toH4Red(getAutor()));
        sb.append(LS2);
        sb.append(Utils.getSaludoEnElNombre());
        sb.append(LS);
        sb.append(Utils.toH3Red("Preámbulo"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(introViaCrucis.getIntro()));
        sb.append(LS2);
        sb.append(Utils.toH3Red("Oración inicial"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(introViaCrucis.getOracion()));
        sb.append(LS2);
        sb.append(getAllEstaciones());
        sb.append(Utils.toH3Red("Oración final"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(Utils.getFormato(getOracion())));
        sb.append(LS2);
        sb.append(Utils.getConclusionHorasMayores());
        sb.append(LS2);
        sb.append(Utils.toRed("Si la celebración la preside un ministro ordenado se concluye con la bendición, como habitualmente."));
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(getTitulo());
        sb.append(".");
        sb.append(getSubTitulo());
        sb.append(".");
        sb.append(getAutor());
        sb.append(".");
        sb.append(Utils.getSaludoEnElNombre());
        sb.append(Utils.toH3Red("Preámbulo."));
        sb.append(Utils.fromHtml(introViaCrucis.getIntro()));
        sb.append("Oración inicial.");
        sb.append(Utils.fromHtml(introViaCrucis.getOracion()));
        sb.append(getAllEstaciones());
        sb.append("Oración final.");
        sb.append(Utils.fromHtml(Utils.getFormato(getOracion())));
        sb.append(Utils.getConclusionHorasMayores());
        return sb;
    }
}
