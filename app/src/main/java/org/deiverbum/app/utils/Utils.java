package org.deiverbum.app.utils;

import static android.graphics.Typeface.BOLD;
import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.CSS_RED_A;
import static org.deiverbum.app.utils.Constants.CSS_RED_Z;
import static org.deiverbum.app.utils.Constants.ERR_REPORT;
import static org.deiverbum.app.utils.Constants.ERR_RESPONSORIO;
import static org.deiverbum.app.utils.Constants.NBSP_4;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;
import static org.deiverbum.app.utils.Constants.OBIEN;
import static org.deiverbum.app.utils.Constants.PADRENUESTRO;
import static org.deiverbum.app.utils.Constants.PRECES_IL;
import static org.deiverbum.app.utils.Constants.PRECES_R;
import static org.deiverbum.app.utils.Constants.RESP_A;
import static org.deiverbum.app.utils.Constants.RESP_R;
import static org.deiverbum.app.utils.Constants.RESP_V;

import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.LHResponsoryShort;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Clase utilitaria que se usa en varias partes de la aplicación
 *
 * @author Alfertson Cedano Guerrero
 */
public final class Utils {

    public static final String LS = System.getProperty("line.separator");
    public static final String LS2 = LS + LS;
    public static final float H3 = 1.4f;
    public static final float H2 = 1.7f;
    public static final float H4 = 1.1f;

    private static final ForegroundColorSpan liturgicalRed = new ForegroundColorSpan(Color.parseColor("#A52A2A"));

    public static SpannableStringBuilder formatTitle(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(toUpper(sOrigen));
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static SpannableStringBuilder formatSubTitle(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sOrigen);
        RelativeSizeSpan textSize = new RelativeSizeSpan(H3);
        ssb.setSpan(CharacterStyle.wrap(textSize), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }


    public static SpannableStringBuilder ssbSmallSize(SpannableStringBuilder ssb) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        ssb.setSpan(CharacterStyle.wrap(smallSizeText), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static SpannableStringBuilder toSmallSize(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }

    public static SpannableStringBuilder toMediumSize(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.9f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }

    public static SpannableStringBuilder toSmallSizes(Spanned sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }


    public static SpannableStringBuilder toSmallSizeRed(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }


    public static SpannableStringBuilder fromHtmlToSmallRed(String sOrigen) {
        Spanned s = fromHtml(sOrigen);
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }



    public static SpannableStringBuilder toH3(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H3);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }

    public static SpannableStringBuilder toH4(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H4);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }


    public static SpannableStringBuilder toH2(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H2);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }

    public static SpannableStringBuilder toH2Red(String sOrigen) {
        RelativeSizeSpan h2 = new RelativeSizeSpan(H2);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        //spannableString=fromHtml(spannableString);
        spannableString.setSpan(CharacterStyle.wrap(h2), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toH2RedNew(String sOrigen) {
        RelativeSizeSpan h2 = new RelativeSizeSpan(H2);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString =
                new SpannableString(toRedHtml(sOrigen));
        //spannableString=fromHtml(spannableString);
        spannableString.setSpan(CharacterStyle.wrap(h2), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toH3Red(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H3);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toH4Red(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H4);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toRed(String sOrigen) {
        SpannableString s = new SpannableString(sOrigen);
        s.setSpan(CharacterStyle.wrap(liturgicalRed), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new SpannableStringBuilder(s);
    }

    public static SpannableStringBuilder toRedNew(SpannableStringBuilder sOrigen) {
        sOrigen.setSpan(CharacterStyle.wrap(liturgicalRed), 0, sOrigen.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sOrigen;
    }

    public static SpannableStringBuilder toRedHtml(String sOrigen) {
        SpannableString s = new SpannableString(fromHtml(sOrigen));

        s.setSpan(CharacterStyle.wrap(liturgicalRed), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new SpannableStringBuilder(s);
    }

    /**
     * Este método es @deprecated a partir de la versión 2022.01,
     * porque conviene migrar algunos métodos a clases más propias.
     * Usar en su lugar {@link BreviaryHour#getSaludoOficio()}
     */
    @Deprecated

    public static SpannableStringBuilder getSaludoOficio() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("INVOCACIÓN INICIAL"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. Señor, abre mis labios.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Y mi boca proclamará tu alabanza.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 27, 29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        ssb.append(LS2);
        ssb.append(Utils.getFinSalmo());
        return ssb;
    }


        public static SpannableStringBuilder getSaludoEnElNombre() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V/. En el nombre del Pater, y del Hijo, y del Espíritu Saint.");
        ssbPartial.append(LS);
        ssbPartial.append("R/. Amén.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 62, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        ssb.append(LS2);
        return ssb;
    }

    /**
     * Este método es @deprecated a partir de la versión 2022.01,
     * porque conviene migrar algunos métodos a clases más propias.
     * Usar en su lugar {@link BreviaryHour#getSaludoOficioForRead()}
     */
    @Deprecated
    public static SpannableStringBuilder getSaludoOficioForReader() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("<p>Señor abre mis labios.</p>");
        ssb.append("<p>Y mi boca proclamará tu alabanza.</p>");
        ssb.append("<p>Gloria al Pater, y al Hijo, y al Espíritu Saint.</p>");
        ssb.append("<p>Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.</p>");
        return (SpannableStringBuilder) fromHtml(ssb.toString());
    }

    /**
     * Este método es @deprecated a partir de la versión 2022.01,
     * porque conviene migrar algunos métodos a clases más propias.
     * Usar en su lugar {@link BreviaryHour#getSaludoDiosMio()}
     */
    @Deprecated

    public static SpannableStringBuilder getSaludoDiosMio() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("INVOCACIÓN INICIAL"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. Dios mío, ven en mi auxilio.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Señor, date prisa en socorrerme.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 32, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        ssb.append(LS2);
        ssb.append(Utils.getFinSalmo());
        return ssb;
    }

    /**
     * Este método es @deprecated a partir de la versión 2022.01,
     * porque conviene migrar algunos métodos a clases más propias.
     * Usar en su lugar {@link BreviaryHour#getSaludoDiosMioForRead()}
     */
    @Deprecated

    public static SpannableStringBuilder getSaludoDiosMioForReader() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("<p>Dios mío, ven en mi auxilio.</p>");
        ssb.append("<p>Señor, date prisa en socorrerme.</p>");
        ssb.append("<p>Gloria al Pater, y al Hijo, y al Espíritu Saint.</p>");
        ssb.append("<p>Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.</p>");
        return (SpannableStringBuilder) fromHtml(ssb.toString());
    }

    /**
     * @deprecated desde v. 2022.01 - Usar en su lugar {@link BreviaryHour#getConclusionHorasMayores()}
     * @return Conclusión formateada
     */
    @Deprecated

    public static SpannableStringBuilder getConclusionHorasMayores() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("CONCLUSIÓN"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Amén.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 78, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        return ssb;
    }

    /**
     * @deprecated
     * Reemplazar por {@link BreviaryHour#getConclusionHorasMayoresForRead()}
     * @return Texto de la conclusión
     */
    @Deprecated
    public static SpannableStringBuilder getConclusionHorasMayoresForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(fromHtml("<p>El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.</p>"));
        ssb.append(fromHtml("<p>Amén.</p>"));
        return ssb;
    }

    /**
     * @deprecated Usar {@link Intermedia#getConclusionHora()}
     * @return Una cadena con la conclusión
     */
    public static SpannableStringBuilder getConclusionIntermedia() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("CONCLUSIÓN"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. Bendigamos al Señor.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Demos gracias a Dios.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 24, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        return ssb;
    }

    /**
     * @deprecated Usar {@link Intermedia#getConclusionHoraForRead()}
     * @return Una cadena con la conclusión
     */
    public static SpannableStringBuilder getConclusionIntermediaForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("<p>Bendigamos al Señor.</p>");
        ssb.append("<p>Demos gracias a Dios.</p>");
        return (SpannableStringBuilder) fromHtml(ssb.toString());
    }

    /**
     * @deprecated  migrar a {@link org.deiverbum.app.model.Kyrie}
     */

    public static SpannableStringBuilder getKyrie(int type) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        switch (type) {
            case 0:
                String text = "Yo confieso ante Dios todopoderoso " + LS +
                        "y ante vosotros, hermanos " + LS +
                        "que he pecado mucho" + LS +
                        "de pensamiento, palabra, obra y omisión:" + LS +
                        "por mi culpa, por mi culpa, por mi gran culpa." + LS2 +
                        "Por eso ruego a santa María, siempre Virgen," + LS +
                        "a los ángeles, a los santos y a vosotros, hermanos," + LS +
                        "que intercedáis por mí ante Dios, nuestro Señor.";

                ssb.append(text);

                break;
            case 1:
                ssb.append("V. Señor, ten misericordia de nosotros.");
                ssb.append(LS);
                ssb.append("R. Porque hemos pecado contra ti.");
                ssb.append(LS2);
                ssb.append("V. Muéstranos, Señor, tu misericordia.");
                ssb.append(LS);
                ssb.append("R. Y danos tu salvación.");
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 39, 41, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 74, 76, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 113, 115, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                break;
            case 2:
                ssb.append("V. Tú que has sido enviado a sanar los corazones afligidos: Señor, ten piedad.");
                ssb.append(LS);
                ssb.append("R. Señor, ten piedad.");
                ssb.append(LS2);

                ssb.append("V. Tú que has venido a llamar a los pecadores: Cristo, ten piedad.");
                ssb.append(LS);

                ssb.append("R. Cristo, ten piedad.");
                ssb.append(LS2);

                ssb.append("V. Tú que estás sentado a la derecha del Pater para interceder por nosotros: Señor, ten piedad.");
                ssb.append(LS);

                ssb.append("R. Señor, ten piedad.");
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 78, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 100, 103, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 169, 172, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 190, 194, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 289, 292, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            default:
                ssb.append("");
                break;
        }


        return ssb;

    }

    /**
     * Método que crea las preces *** terminar descripción luego
     * @deprecated  migrar a {@link LHIntercession}
     *
     * @param precesIntro Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param precesTexto    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */
    @Deprecated
    public static String getPreces(String precesIntro, String precesTexto) {
        String sFinal;
        String[] introArray = precesIntro.split("\\|");
        if (introArray.length == 3) {
            sFinal = introArray[0] + BRS + "<i>" + introArray[1] + "</i>" + BRS + precesTexto + BRS + introArray[2];
        } else {
            sFinal = precesIntro + BRS + precesTexto;

        }
        sFinal = getFormato(sFinal);
        return sFinal;
    }

    //TODO debe dejarse de usar caracteres en griego para los reemplazos

    /**
     * <p>Sustituye y/o formatea determinados caracteres según una convención de marcado ideada por mí mismo.</p>
     * <p>La convención es la siguiente:</p>
     * <ul>
     *     <li></li>
     * </ul>
     * @param sOrigen Cadena original para hacer el reemplazo
     * @return La cadena formateada
     */
    public static String getFormato(String sOrigen) {
        String sFormateado;
        //α β γ δ ε ϝ ϛ ζ η θ ι κ λ μ ν ξ ο π ϟ ϙ ρ σ τ υ φ χ ψ ω ϡ
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫  ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿

        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟

        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿

        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟

        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿
        BibleReading de la Pasión: τ : Jesús  ν : Cronista ς : S

        Nuevos valores desde v. 2022.01.01:
            ⊞ en lugar de τ ... ✚.
            ⊝ en lugar de ν ... C.
            ⊟ en lugar de ς ... S.
            ⊓  en lugar de ƞ ... N.
            ⊚  en lugar de ℇ ... O bien


*/
        sFormateado = sOrigen
                .replace("_", NBSP_SALMOS)
                .replace("§", BRS)
                .replace("~", BR)
                .replace("¦", NBSP_4)
                .replace("⊣", BR + NBSP_4)
                .replace("≠", PRECES_R)
                .replace("∞", PRECES_IL)
                .replace("⊚", OBIEN)
                .replace("†", CSS_RED_A + " † " + CSS_RED_Z)
                .replace("⊓", CSS_RED_A + " N. " + CSS_RED_Z)
                .replace("Ɽ", CSS_RED_A + " R. " + CSS_RED_Z)
                .replace("⟨", CSS_RED_A + "(" + CSS_RED_Z)
                .replace("⟩", CSS_RED_A + ")" + CSS_RED_Z)
                .replace("ⱱ", CSS_RED_A + "V/." + CSS_RED_Z)
                .replace("ⱴ", CSS_RED_A + "R/." + CSS_RED_Z)
                .replace("Ʀ", CSS_RED_A + " R/. " + CSS_RED_Z + BRS)
//NEW
                .replace("℟", CSS_RED_A + "℟." + CSS_RED_Z)
                //.replace("℟",  toRed("℟") )

                .replace("℣", CSS_RED_A + "℣." + CSS_RED_Z)
                .replace("≀", BR + NBSP_4 + NBSP_4)
                .replace("~", BR)
                .replace("§", BRS)
                .replace("∸", BRS)
                .replace("⊞", CSS_RED_A + "✚. " + CSS_RED_Z)
                .replace("⊝", CSS_RED_A + "C. " + CSS_RED_Z)
                .replace("⊟", CSS_RED_A + "S. " + CSS_RED_Z)
                .replace("[rubrica]", CSS_RED_A)
                .replace("[/rubrica]", CSS_RED_Z)
        ;


        return sFormateado;
    }

    public static String replaceByTime(String mText, int timeID) {
        String sFormateado;
        if (timeID == 6) {
            sFormateado = mText
                    .replace(" Ƥ.", " Aleluya.")
                    .replace("Ƥ.", " Aleluya.")

                    .replace("Ƥ", " Aleluya.")

                    .replace("α", " Aleluya.")
                    .replace("αα", " Aleluya, aleluya.");
        } else {
            sFormateado = mText
                    .replace("Ƥ.", "")
                    .replace("Ƥ", "")

                    .replace("α.", "")
                    .replace("αα", "")
                    .replace("α", "");

        }

        return sFormateado;
    }

    //Ojo solución a fromHTML deprecated... ver: http://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
    public static Spanned fromHtml(String html) {
        String s = getFormato(html);
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(s);
        }
        return result;
    }

    public static SpannableStringBuilder fromHtmlSmall(String html) {
        String s = getFormato(html);
        //SpannableString ss=toSmallSizes(html);
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(s);
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(result);

        return toSmallSizes(ssb);
    }

    /**
     * Este método es un ayudador para la lectura de voz,
     * que intenta quitar ciertas combinaciones de caracteres que distraerían la atención
     * durante la lectura de voz, porque son pronunciables.
     * Remueve también ciertos delimitadores que se usan
     * para dar formato al texto en la vista
     *
     * @param sOrigen Cadena original
     * @return La cadena sin las comillas
     */
    public static String stripQuotation(String sOrigen) {
        String sFormateado;
        sFormateado = sOrigen
                .replace("«", "")
                .replace(".»", "».")
                .replace("\"", "")
                .replace("'", "")
                .replace("“", "")
                .replace("”", "")
                .replace("(...)", ".")
                .replace("(", "")
                .replace(")", "")
                .replace("...", ".")
                .replace("[...]", "")
                .replace("ς", "")
                .replace("ν", "")
                .replace("τ", "")
                .replace("*", "")
                .replace("—", "")
                //.replace("ü", "u")

                .replaceAll("(?s)[\\[rubrica].*?/rubrica]", "")
        ;


        return sFormateado.trim();
    }

    /**
     * Este método es un ayudador para la lectura de voz,
     * que agrega el punto al final de aquellos contenidos que no lo tengan.
     * El punto es el carácter que se usa para separar los bloques de lectura de voz.
     *
     * @param sOrigen La cadena original
     * @return Cadena con final normalizado
     */
    public static String normalizeEnd(String sOrigen) {
        return sOrigen.endsWith(".") ? sOrigen : String.format("%s.",sOrigen);
    }

    /**
     * Método que limpia la segunda parte de la antífona, en el caso del símblo †
     * @deprecated Desde 2022.01 - Usar {@link LHPsalmody#getAntifonaLimpia(String)}
     * @param sAntifona Una cadena con el texto de la antífona
     * @return La misma cadena, pero sin el referido símbolo
     */

    @Deprecated
    public static String getAntifonaLimpia(String sAntifona) {
        return sAntifona.replace("†", "");
    }

    /**
     * @deprecated Desde la versión 2022.01. Usar en su lugar {@link LHResponsoryShort#getAll()}
     * <p>Método que crea la cadena completa de un responsorio dado.</p>
     *
     * @param respArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param nForma    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    @Deprecated
    public static String getResponsorio(String[] respArray, int nForma) {
        String sResponsorio = ERR_RESPONSORIO + BR + "Tamaño del responsorio: " + respArray.length + " Código forma: " + nForma + BR;
        switch (nForma) {
            case 1:
                if (respArray.length == 3) {
                    sResponsorio =
                            RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                    RESP_V + respArray[2] + BRS +
                                    RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);
                }
                break;

            case 2:
                sResponsorio =
                        RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                RESP_V + respArray[2] + BRS +
                                RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);

                break;


            case 6001230:
                /*
                 *6 partes distribuidas así: 0-0-1-2-3-0, de ahí el código 6001230... si no, imposible entenderse
                 *Suele ser el modelo de responsorio para Laudes
                 */
                if (respArray.length == 4) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[2] + BRS +
                                    RESP_V + respArray[3] + BR +
                                    RESP_R + respArray[0] + BR;
                }
                break;


            case 6001020:
                if (respArray.length == 3) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[2] + BR +
                                    RESP_R + respArray[0] + BR;
                }
                break;


            case 4:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[1] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[2] + BR +
                                RESP_R + respArray[0] + BR;
                break;

            case 201:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[1] + BR;
                break;


            default:
                break;
        }
        return sResponsorio;


    }


    /**
     * @deprecated desde la versión 2022.01. Usar en su lugar {@link LHResponsoryShort#getAllForRead()}
     * <p>Método que crea la cadena completa de un responsorio dado destinado a la lectura de voz.</p>
     *
     * @param respArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param nForma    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */
    @Deprecated
    public static String getResponsorioForReader(String[] respArray, int nForma) {
        String sResponsorio = ERR_RESPONSORIO + BR + "Tamaño del responsorio: " + respArray.length + " Código forma: " + nForma + BR;
        switch (nForma) {
            case 1:
                if (respArray.length == 3) {
                    sResponsorio =
                            respArray[0] + respArray[1] + BRS +
                                    respArray[2] + BRS +
                                    Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);
                }
                break;

            case 2:
                sResponsorio =
                        respArray[0] + respArray[1] + BRS +
                                respArray[2] + BRS +
                                Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);

                break;


            case 6001230:

                if (respArray.length == 4) {

                    sResponsorio =
                            respArray[0] + BR +
                                    respArray[0] + BRS +
                                    respArray[1] + BR +
                                    respArray[2] + BRS +
                                    respArray[3] + BR +
                                    respArray[0] + BRS;
                }
                break;


            case 6001020:

                if (respArray.length == 3) {

                    sResponsorio =
                            respArray[0] + BR +
                                    respArray[0] + BRS +
                                    respArray[1] + BR +
                                    respArray[0] + BRS +
                                    respArray[2] + BR +
                                    respArray[0] + BRS;
                }
                break;


            case 4:
                sResponsorio =
                        respArray[0] + BR +
                                respArray[0] + BRS +
                                respArray[1] + BR +
                                respArray[0] + BRS +
                                respArray[2] + BR +
                                respArray[0] + BRS;
                break;

            case 201:
                sResponsorio =
                        respArray[0] + BR +
                                respArray[1] + BRS;
                break;

            default:
                break;
        }
        return sResponsorio;


    }

    /**
     * @deprecated Usar en su lugar {@link org.deiverbum.app.model.TeDeum#getTextoSpan()}
     * @return Texto del Te Deum
     */
    @Deprecated
    public static Spanned getTeDeum() {
        String teDeum = "<p>Señor, Dios eterno, alegres te cantamos, <br />a ti nuestra alabanza, <br />a ti, Pater del cielo, te aclama la creación. <br /><br />Postrados ante ti, los ángeles te adoran <br />y cantan sin cesar: <br /><br />Saint, santo, santo es el Señor, <br />Dios del universo; <br />llenos están el cielo y la tierra de tu gloria. <br /><br />A ti, Señor, te alaba el coro celestial de los apóstoles, <br />la multitud de los profetas te enaltece, <br />y el ejército glorioso de los mártires te aclama. <br /><br />A ti la Iglesia santa, <br />por todos los confines extendida, <br />con júbilo te adora y canta tu grandeza: <br /><br />Pater, infinitamente santo, <br />Hijo eterno, unigénito de Dios, <br />Saint Espíritu de amor y de consuelo. <br /><br />Oh Cristo, tú eres el Rey de la gloria, <br />tú el Hijo y Palabra del Pater, <br />tú el Rey de toda la creación. <br /><br />Tú, para salvar al hombre, <br />tomaste la condición de esclavo <br />en el seno de una virgen. <br /><br />Tú destruiste la muerte <br />y abriste a los creyentes las puertas de la gloria. <br /><br />Tú vives ahora, <br />inmortal y glorioso, en el reino del Pater. <br /><br />Tú vendrás algún día, <br />como juez universal. <br /><br />Muéstrate, pues, amigo y defensor <br />de los hombres que salvaste. <br /><br />Y recíbelos por siempre allá en tu reino, <br />con tus santos y elegidos. <br /><br />Salva a tu pueblo, Señor, <br />y bendice a tu heredad. <br /><br />Sé su pastor, <br />y guíalos por siempre. <br /><br />Día tras día te bendeciremos <br />y alabaremos tu nombre por siempre jamás. <br /><br />Dígnate, Señor, <br />guardarnos de pecado en este día. <br /><br />Ten piedad de nosotros, Señor, <br />ten piedad de nosotros. <br /><br />Que tu misericordia, Señor, venga sobre nosotros, <br />como lo esperamos de ti. <br /><br />A ti, Señor, me acojo, <br />no quede yo nunca defraudado.</p>";
        return fromHtml(teDeum);
    }


    /**
     * @deprecated Usar en su lugar {@link LHPsalmody#getNoGloria()}
     * @return Texto del "No se dice Gloria..."
     */
    @Deprecated
    public static SpannableStringBuilder getNoGloria() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("No se dice Gloria");
        return toRedNew(ssb);
    }

    /**
     * @deprecated Usar en su lugar {@link LHPsalmody#getFinSalmo()}
     * @return Fin del salmo para la vista
     */
    @Deprecated
    public static Spanned getFinSalmo() {
        String fin = "Gloria al Pater, y al Hijo, y al Espíritu Saint." + BR
                + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
        return fromHtml(fin);
    }

    /**
     * @deprecated Usar en su lugar {@link LHPsalmody#getFinSalmoForRead()}
     * @return Fin del salmo para lectura
     */
    @Deprecated
    public static  Spanned getFinSalmoForRead() {
        String fin = "<p>Gloria al Pater, y al Hijo, y al Espíritu Saint.<br />" +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.</p>";
        return fromHtml(fin);
    }

    @Deprecated
    public static Spanned getPadreNuestro() {

        return fromHtml(PADRENUESTRO);
    }

    /**
     * Método que devuelve la fecha del sistema en formato yyyyMMdd
     *
     * @return Una cadena con la fecha
     */

    public static String getHoy() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * Método que devuelve la fecha del sistema en forma legible: 22 de Agosto de 1972
     *
     * @return Una cadena con la fecha
     */

    public static String getFecha() {
        SimpleDateFormat mFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", Locale.getDefault());
        return mFormat.format(new Date());
    }

    /**
     * Método que formatea una fecha dada en forma legible larga: 22 de Agosto de 1972
     * @param dateString La fecha a formatear
     * @return Una cadena con la fecha
     */

    public static String getLongDate(String dateString) {
        SimpleDateFormat longFormat =
                new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return longFormat.format(Objects.requireNonNull(df.parse(dateString)));
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * Formatea una fecha según el patrón: 22 de Agosto 1972 para mostrarla en la barra superior.
     * @param dateString La fecha a formatear
     * @return Una cadena con la fecha
     */

    public static String getTitleDate(String dateString) {
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es", "ES"));
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.format(Objects.requireNonNull(df.parse(dateString)));
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * Formatea una cadena de fecha haciéndola apta para consultas en Firebase
     * @param date La cadena con la fecha
     * @return La cadena formateada para Firebase
     * @since 2022.01
     */
    public static String toDocument(String date) {
        return String.format("%s/%s/%s",
                date.substring(0, 4),
                date.substring(4, 6),
                date.substring(6, 8)
        );
    }


    /**
     * Quita el carácter / a las fechas que son pasadas inicialmente
     * para buscar datos en Firebase, a fin de hacer peticiones de Api
     * @param dateString Cadena con la fecha
     * @return La cadena limpia
     * @since 2022.01
     */
    public static String cleanDate(String dateString) {
        return dateString.replaceAll("/","");
    }

    /**
     * Crea mensajes de error personalizados
     * @param msg El mensaje de error
     * @param params Los parámetros que se usaron para obtener la información en el contexto
     * @return Una cadena con el mensaje personalizado
     * @since 2022.01
     */
    public static String createErrorMessage(String msg, String params) {
        return String.format("%s\nParámetros: %s",msg,params);
    }

    public static String createErrorMessage(String msg) {
        return String.format("Ha ocurrido el siguiente error:%s\n%s",msg,ERR_REPORT);
    }

    /**
     * Obtiene las dos cifras del mes desde una cadena de fecha
     * @param date La fecha en formato yyyymmdd
     * @return Una cadena con la parte del mes
     * @since 2022.1
     */
    public static String getMonth(String date) {
        return date.substring(4, 6);
    }

    /**
     * Obtiene las dos cifras del día desde una cadena de fecha
     * @param date La fecha en formato yyyymmdd
     * @return Una cadena con la parte del día
     * @since 2022.1
     */
    public static String getDay(String date) {
        return date.substring(6, 8);
    }

    public static String getOrdinal(int i) {
            switch (i) {
                case 1:
                    return "Primera";

                case 2:
                    return "Segunda";

                case 3:
                    return "Tercera";
                case 4:
                    return "Cuarta";
                case 5:
                    return "Quinta";
                case 6:
                    return "Sexta";
                case 7:
                    return "Séptima";
                case 8:
                    return "Octava";
                case 9:
                    return "Novena";
                default:
                    return "";


            }
        }

    public static String getDayName(int day) {
        if (day > 0 && day <= 7) {

            Locale locale = new Locale("es", "ES");
            DateFormatSymbols symbols = new DateFormatSymbols(locale);
            String[] dayNames = symbols.getWeekdays();
            return dayNames[day];
        }
        return "";
    }

    public static String pointAtEnd(String s) {
        return String.format("%s.",s);
    }
    public static String toUpper(String s) {
        return s.toUpperCase();
    }
    public static String toLower(String s) {
        return s.toLowerCase();
    }


}
