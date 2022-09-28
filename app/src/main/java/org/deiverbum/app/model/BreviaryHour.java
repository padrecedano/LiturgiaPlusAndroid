package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.RED_COLOR;
import static org.deiverbum.app.utils.Constants.TITLE_CONCLUSION;
import static org.deiverbum.app.utils.Constants.TITLE_INITIAL_INVOCATION;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;

import org.deiverbum.app.utils.Utils;

import java.util.List;

/**
 * <p>
 * Reúne aquellos elementos que son comúnes a las diversas horas del Breviary.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class BreviaryHour extends Liturgy {
    //protected MetaLiturgia metaLiturgia;
    //protected Saint santo;
    protected int hourId;

    protected String metaInfo;
    protected LHHymn himno;
    protected LHPsalmody salmodia;
    protected Prayer oracion;
    protected LHOfficeOfReading oficioLecturas;
    protected TeDeum teDeum;
    protected Oficio oficio;
    protected Laudes laudes;
    protected List<MassReading> evangelios;
    private Mixto mixto;
    private Intermedia intermedia;
    private Visperas visperas;

    public Completas getCompletas() {
        return completas;
    }

    public void setCompletas(Completas completas) {
        this.completas = completas;
    }

    private Completas completas;


    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public BreviaryHour() {
    }

    /*public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }
*/
    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    //TODO checkthis
    public String getMetaInfo() {

        if(metaInfo!=null) {
            return !metaInfo.equals("") ? "<br><br>" + metaInfo : "";
        }else{
            return "";
        }
    }
/*
    public Saint getSanto() {
        return santo;
    }

    public void setSanto(Saint santo) {
        this.santo = santo;
    }
*/

    public SpannableStringBuilder getSaludoOficio() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.formatTitle(TITLE_INITIAL_INVOCATION));
        sb.append(LS2);
        sb.append(Utils.toRed("V. "));
        sb.append("Señor, abre mis labios.");
        sb.append(LS);
        sb.append(Utils.toRed("R. "));
        sb.append("Y mi boca proclamará tu alabanza.");
        sb.append(LS2);
        sb.append(salmodia.getFinSalmo());
        return sb;
    }

    public SpannableStringBuilder getSaludoOficioForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append("<p>Señor abre mis labios.</p>");
        sb.append("<p>Y mi boca proclamará tu alabanza.</p>");
        sb.append("<p>Gloria al Padre, y al Hijo, y al Espíritu Santo.</p>");
        sb.append("<p>Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.</p>");
        return (SpannableStringBuilder) Utils.fromHtml(sb.toString());
    }

    public SpannableStringBuilder getSaludoDiosMio() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.formatTitle(TITLE_INITIAL_INVOCATION));
        sb.append(LS2);
        sb.append(Utils.toRed("V. "));
        sb.append("Dios mío, ven en mi auxilio.");
        sb.append(LS);
        sb.append(Utils.toRed("R. "));
        sb.append("Señor, date prisa en socorrerme.");
        sb.append(LS2);
        sb.append(salmodia.getFinSalmo());
        return sb;
    }

    /**
     * @since 2021.01
     * @return El saludo listo para el lector de voz.
     */
    public static String getSaludoDiosMioForRead() {
        StringBuilder ssb = new StringBuilder();
        ssb.append("Dios mío, ven en mi auxilio.");
        ssb.append("Señor, date prisa en socorrerme.");
        ssb.append("Gloria al Padre, y al Hijo, y al Espíritu Santo.");
        ssb.append("Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.");
        return ssb.toString();
    }

    public LHHymn getHimno() {
        return himno;
    }

    public void setHimno(LHHymn himno) {
        this.himno = himno;
    }

    public LHPsalmody getSalmodia() {
        return salmodia;
    }

    public void setSalmodia(LHPsalmody salmodia) {
        this.salmodia = salmodia;
    }


    public void setOracion(Prayer oracion) {
        this.oracion = oracion;
    }

    public Prayer getOracion() {
        return oracion;
    }

    /**
     * //TODO este método existe también en Liturgy, repensar el modelo
     *
     * @return Contenido formateado para la vista con la vida del santo
     */
    public SpannableStringBuilder getVida() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (getMetaLiturgia().getHasSaint()) {
            ssb.append(getSanto().getVidaSmall());
        }
        return ssb;
    }

    public String getTitulo() {
        if (getMetaLiturgia().getHasSaint()) {
            return getSanto().getTheName() + LS2;
        } else {
            return getMetaLiturgia().getTitulo() + LS2;
        }
    }

    public static SpannableStringBuilder getConclusionHorasMayores() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(Utils.formatTitle(TITLE_CONCLUSION));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Amén.");
        ssbPartial.setSpan(CharacterStyle.wrap(RED_COLOR), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(RED_COLOR), 78, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        return ssb;
    }

    /**
     * @since 2021.01
     * @return Texto con la conclusión de la hora, formateado para lectura
     */
    public static SpannableStringBuilder getConclusionHorasMayoresForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.fromHtml("<p>El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.</p>"));
        ssb.append(Utils.fromHtml("<p>Amén.</p>"));
        return ssb;
    }

    /**
     * Método que obtiene la conclusión de la hora
     *
     * @since 2022.2
     * @return Texto con la conclusión de la hora, formateado para vista
     */
    public static SpannableStringBuilder getConclusionHoraMenor() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.formatTitle(TITLE_CONCLUSION));
        sb.append(LS2);
        sb.append(Utils.toRed("V. "));
        sb.append("Bendigamos al Señor.");
        sb.append(LS);
        sb.append(Utils.toRed("R. "));
        sb.append("Demos gracias a Dios.");
        return sb;
    }

    /**
     * Método que obtiene la conclusión de la hora
     *
     * @since 2022.2
     * @return Texto con la conclusión de la hora, formateado para lectura
     */
    public static String getConclusionHoraMenorForRead() {
        return "Bendigamos al Señor. Demos gracias a Dios.";
    }

    public void setHourId(int hourId) {
        this.hourId = hourId;
    }

    public int getHourId() {
        return hourId;
    }

    public void setOfficeOfReading(LHOfficeOfReading oficioLecturas) {
        this.oficioLecturas = oficioLecturas;
    }


    public void setTeDeum(TeDeum teDeum) {

    }

    public void setLaudes(Laudes laudes) {
        this.laudes=laudes;
    }
    public void setEvangelios(List<MassReading> evangelios) {
        this.evangelios=evangelios;
    }
    public void setMixto(Mixto mixto) {
       this.mixto=mixto;
    }
    public Mixto getMixto() {

        return this.mixto;
    }

    public Laudes getLaudes() {
        return this.laudes;
    }

    public Intermedia getIntermedia() {
        return this.intermedia;
    }

    public void setIntermedia(Intermedia intermedia) {
        this.intermedia=intermedia;
    }

    public void setVisperas(Visperas visperas) {
        this.visperas=visperas;
    }
    public Visperas getVisperas() {
        return this.visperas;
    }

}

