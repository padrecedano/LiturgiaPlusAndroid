package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_CONCLUSION;
import static org.deiverbum.app.utils.Constants.TITLE_INITIAL_INVOCATION;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

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
@SuppressWarnings("SameReturnValue")
public class BreviaryHour extends Liturgy {
    //protected int typeID;

    protected String metaInfo;
    protected LHHymn himno;
    protected LHPsalmody salmodia;
    protected Prayer oracion;
    protected LHOfficeOfReading oficioLecturas;
    protected TeDeum teDeum;
    protected Oficio oficio;
    protected OficioEaster oficioEaster;

    protected Laudes laudes;
    private Mixto mixto;
    private Intermedia intermedia;
    private Visperas visperas;
    private Completas completas;

    public BreviaryHour() {
    }

    /**
     * @return El saludo listo para el lector de voz.
     * @since 2021.01
     */
    public static String getSaludoDiosMioForRead() {
        return "Dios mío, ven en mi auxilio." +
                "Señor, date prisa en socorrerme." +
                "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.";
    }

    public static SpannableStringBuilder getConclusionHorasMayores() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(Utils.formatTitle(TITLE_CONCLUSION));
        ssb.append(LS2);
        ssb.append(Utils.toRed("V. "));
        ssb.append("El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.");
        ssb.append(LS);
        ssb.append(Utils.toRed("R. "));
        ssb.append("Amén.");
        return ssb;
    }

    /**
     * @return Texto con la conclusión de la hora, formateado para lectura
     * @since 2021.01
     */
    public static String getConclusionHorasMayoresForRead() {
        return "El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna. Amén.";
    }

    /**
     * Método que obtiene la conclusión de la hora
     *
     * @return Texto con la conclusión de la hora, formateado para vista
     * @since 2022.2
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
     * @return Texto con la conclusión de la hora, formateado para lectura
     * @since 2022.2
     */
    public static String getConclusionHoraMenorForRead() {
        return "Bendigamos al Señor. Demos gracias a Dios.";
    }

    public Completas getCompletas() {
        return completas;
    }

    public void setCompletas(Completas completas) {
        this.completas = completas;
    }

    //TODO checkthis
    public String getMetaInfo() {

        if (metaInfo != null) {
            return !metaInfo.equals("") ? "<br><br>" + metaInfo : "";
        } else {
            return "";
        }
    }

    @SuppressWarnings("unused")
    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

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
        sb.append("Señor abre mis labios.");
        sb.append("Y mi boca proclamará tu alabanza.");
        sb.append("Gloria al Padre, y al Hijo, y al Espíritu Santo.");
        sb.append("Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.");
        return sb;
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

    public Prayer getOracion() {
        return oracion;
    }

    public void setOracion(Prayer oracion) {
        this.oracion = oracion;
    }

    public void setTypeId(int typeID) {
        this.typeID = typeID;
    }

    @SuppressWarnings("unused")
    public void setOfficeOfReading(LHOfficeOfReading oficioLecturas) {
        this.oficioLecturas = oficioLecturas;
    }

    public void setTeDeum(TeDeum teDeum) {
    }

    @SuppressWarnings("unused")
    public Mixto getMixto() {
        return this.mixto;
    }

    public void setMixto(Mixto mixto) {
        this.mixto = mixto;
    }

    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public Oficio getOficio(boolean hasInvitatory) {
        oficio.getInvitatorio().isMultiple = hasInvitatory;
        return oficio;
    }

    public Laudes getLaudes() {
        return this.laudes;
    }

    public void setLaudes(Laudes laudes) {
        this.laudes = laudes;
    }

    public Laudes getLaudes(boolean hasInvitatory) {
        laudes.getInvitatorio().isMultiple = hasInvitatory;
        return laudes;
    }

    public Intermedia getIntermedia() {
        return this.intermedia;
    }

    public void setIntermedia(Intermedia intermedia) {
        this.intermedia = intermedia;
    }

    public Visperas getVisperas() {
        return this.visperas;
    }

    public void setVisperas(Visperas visperas) {
        this.visperas = visperas;
    }

    public OficioEaster getOficioEaster() {
        return this.oficioEaster;
    }

    public void setOficioEaster(OficioEaster oficioEaster) {
        this.oficioEaster = oficioEaster;
    }

    public SpannableStringBuilder getMixtoForView(LiturgyTime liturgyTime, boolean hasSaint) {
        SpannableStringBuilder sb = new SpannableStringBuilder();


        try {
            this.hasSaint = hasSaint;
            LHInvitatory invitatory = oficio.getInvitatorio();

            invitatory.normalizeByTime(liturgyTime.getTimeID());
            laudes.salmodia.normalizeByTime(liturgyTime.getTimeID());

            sb.append(LS2);
            if (santo != null && this.hasSaint) {
                invitatory.normalizeIsSaint(santo.theName);
                sb.append(santo.getVidaSmall());
                sb.append(LS);
            }

            sb.append(mixto.getTituloHora());
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);
            sb.append(laudes.getSaludoOficio());
            sb.append(Utils.LS2);
            sb.append(oficio.getInvitatorio().getAll());
            sb.append(Utils.LS2);
            sb.append(laudes.himno.getAll());
            sb.append(Utils.LS2);
            sb.append(laudes.getSalmodia().getAll());
            sb.append(laudes.getLecturaBreve().getAllWithHourCheck(2));
            //sb.append(lecturaBreve.getAllWithHourCheck(2));

            sb.append(Utils.LS2);
            sb.append(oficio.oficioLecturas.getAll(liturgyTime.getTimeID()));
            sb.append(Utils.LS2);
            //sb.append(mixto.getMisaLecturas().getAllEvangelioForView());

            sb.append(mixto.getEvangeliosForView());
            sb.append(Utils.LS2);
            sb.append(laudes.getGospelCanticle().getAll());
            sb.append(Utils.LS2);
            sb.append(laudes.getPreces().getAll());
            sb.append(LS2);
            sb.append(PadreNuestro.getAll());
            sb.append(LS2);
            sb.append(laudes.oracion.getAll());
            sb.append(LS2);
            sb.append(getConclusionHorasMayores());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getMixtoForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            if (santo != null && this.hasSaint) {
                sb.append(santo.getVida());
            }
            sb.append(mixto.getTituloHoraForRead());
            sb.append(laudes.getSaludoOficioForRead());
            sb.append(oficio.getInvitatorio().getAllForRead());
            sb.append(laudes.himno.getAllForRead());
            sb.append(laudes.salmodia.getAll());
            sb.append(laudes.getLecturaBreve().getAllForRead());
            sb.append(oficio.oficioLecturas.getAllForRead());
            sb.append(mixto.getEvangeliosForRead());
            sb.append(laudes.getGospelCanticle().getAllForRead());
            sb.append(laudes.getPreces().getAllForRead());
            sb.append(PadreNuestro.getAll());
            sb.append(laudes.getOracion().getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

}

