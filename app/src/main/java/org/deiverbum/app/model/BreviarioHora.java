package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.RED_COLOR;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import org.deiverbum.app.utils.Utils;

/**
 * <p>
 * Reúne aquellos elementos que son comúnes a las diversas horas del Breviario.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class BreviarioHora {
    protected MetaLiturgia metaLiturgia;
    protected MetaBreviario metaBreviario;
    protected Santo santo;
    protected String metaInfo;
    protected Himno himno;
    protected Salmodia salmodia;
    protected Oracion oracion;

    public BreviarioHora() {
    }

    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    //TODO checkthis
    public String getMetaInfo() {

        return !metaInfo.equals("") ? "<br><br>" + metaInfo : "";
    }

    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {
        this.santo = santo;
    }


    public SpannableStringBuilder getSaludoOficio() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.formatTitle("INVOCACIÓN INICIAL"));
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
        sb.append(Utils.formatTitle("INVOCACIÓN INICIAL"));
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
    public static Spanned getSaludoDiosMioForRead() {
        StringBuilder ssb = new StringBuilder();
        ssb.append("<p>Dios mío, ven en mi auxilio.</p>");
        ssb.append("<p>Señor, date prisa en socorrerme.</p>");
        ssb.append("<p>Gloria al Padre, y al Hijo, y al Espíritu Santo.</p>");
        ssb.append("<p>Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.</p>");
        return Utils.fromHtml (ssb.toString());
    }

    public Himno getHimno() {
        return himno;
    }

    public void setHimno(Himno himno) {
        this.himno = himno;
    }

    public Salmodia getSalmodia() {
        return salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }


    public void setOracion(Oracion oracion) {
        this.oracion = oracion;
    }

    public Oracion getOracion() {
        return oracion;
    }

    /**
     * //TODO este método existe también en Liturgia, repensar el modelo
     *
     * @return Contenido formateado para la vista con la vida del santo
     */
    public SpannableStringBuilder getVida() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (metaLiturgia.getHasSaint()) {
            ssb.append(santo.getVidaSmall());
        }
        return ssb;
    }

    public String getTitulo() {
        if (metaLiturgia.getHasSaint()) {
            return santo.getNombre() + LS2;
        } else {
            return metaLiturgia.getTitulo() + LS2;
        }
    }

    public static SpannableStringBuilder getConclusionHorasMayores() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(Utils.formatTitle("CONCLUSIÓN"));
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

}

