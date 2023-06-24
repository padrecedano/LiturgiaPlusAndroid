package org.deiverbum.app.util;

import static android.graphics.Typeface.BOLD;
import static org.deiverbum.app.util.Constants.BR;
import static org.deiverbum.app.util.Constants.BRS;
import static org.deiverbum.app.util.Constants.CSS_RED_A;
import static org.deiverbum.app.util.Constants.CSS_RED_Z;
import static org.deiverbum.app.util.Constants.ERR_REPORT;
import static org.deiverbum.app.util.Constants.NBSP_4;
import static org.deiverbum.app.util.Constants.NBSP_SALMOS;
import static org.deiverbum.app.util.Constants.OBIEN;
import static org.deiverbum.app.util.Constants.PRECES_IL;
import static org.deiverbum.app.util.Constants.PRECES_R;
import static org.deiverbum.app.util.Constants.VERSION_CODE;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import org.deiverbum.app.model.BreviaryHour;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Clase utilitaria que se usa en varias partes de la aplicación
 */
@SuppressWarnings("all")
public final class Utils {

    public static final String LS = System.getProperty("line.separator");
    public static final String LS2 = LS + LS;
    public static final float H1 = 2.2f;
    public static final float H2 = 1.7f;
    public static final float H3 = 1.4f;
    public static final float H4 = 1.1f;

    public static SpannableStringBuilder formatTitle(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(toUpper(sOrigen));
        ssb.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    public static SpannableStringBuilder formatSubTitleToLower(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(toLower(sOrigen));
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
        spannableString.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }


    public static SpannableStringBuilder fromHtmlToSmallRed(String sOrigen) {
        Spanned s = fromHtml(sOrigen);
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        spannableString.setSpan(CharacterStyle.wrap(h2), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        spannableString.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toH3Red(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H3);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toH4Red(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H4);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toRed(String sOrigen) {
        SpannableString s = new SpannableString(sOrigen);
        s.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new SpannableStringBuilder(s);
    }

    public static String toRedFont(String s) {
        return String.format("<font color=\"%s\">%s</font>", ColorUtils.getRedCode(), s);
    }

    public static SpannableStringBuilder toRedNew(SpannableStringBuilder sOrigen) {
        sOrigen.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, sOrigen.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sOrigen;
    }

    public static SpannableStringBuilder toRedHtml(String sOrigen) {
        SpannableString s = new SpannableString(fromHtml(sOrigen));

        s.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new SpannableStringBuilder(s);
    }


    /**
     * Este método es @deprecated a partir de la versión 2023.1,
     * porque conviene migrar algunos métodos a clases más propias.
     * Usar en su lugar {@link org.deiverbum.app.model.Liturgy#getSaludoInicial()}
     *
     * @return El saludo formateado
     */

    @Deprecated
    //noinspection
    public static SpannableStringBuilder getSaludoEnElNombre() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V/. En el nombre del Padre, y del Hijo, y del Espíritu Santo.");
        ssbPartial.append(LS);
        ssbPartial.append("R/. Amén.");
        ssbPartial.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 62, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        ssb.append(LS2);
        return ssb;
    }


    /**
     * @return Conclusión formateada
     * @deprecated desde v. 2022.01 - Usar en su lugar {@link BreviaryHour#getConclusionHorasMayores()}
     */
    @Deprecated
    //noinspection
    public static SpannableStringBuilder getConclusionHorasMayores() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("CONCLUSIÓN"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Amén.");
        ssbPartial.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 78, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        return ssb;
    }


    /**
     * <p>Sustituye y/o formatea determinados caracteres según una convención de marcado ideada por mí mismo.</p>
     * <p>La convención es la siguiente:</p>
     * <ul>
     *     <li></li>
     * </ul>
     *
     * @param sOrigen Cadena original para hacer el reemplazo
     * @return La cadena formateada
     */
    public static String getFormato(String sOrigen) {
        String sFormateado;
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫  ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿
        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟
        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿
        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟
        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿

        Nuevos valores desde v. 2022.01.01:
            ⊞ en lugar de τ ...  ✚.
            ⊝ en lugar de ν ...  C.
            ⊟ en lugar de ς ...  S.
            ⊓  en lugar de ƞ ... N.
            ⊚  en lugar de ℇ ... O bien
        */
        sFormateado = sOrigen
                .replace("_", NBSP_SALMOS)
                .replace("§", BRS)
                .replace("~", BR)
                .replace("¦", NBSP_4)
                .replace("⊣", BR + NBSP_4)
                .replace("≠", String.format("%s %s ", NBSP_SALMOS, toRedFont(PRECES_R)))
                .replace("∞", String.format("%s%s%s", BRS, toRedFont(PRECES_IL),BRS))
                .replace("⊚", OBIEN)
                .replace("†", toRedFont(" † "))
                .replace("⊓", toRedFont(" N. "))
                .replace("Ɽ", toRedFont(" R. "))
                .replace("⟨", toRedFont("("))
                .replace("⟩", toRedFont(")"))
                .replace("ⱱ", toRedFont("V/."))
                .replace("ⱴ", toRedFont("R/."))
                .replace("Ʀ", toRedFont(" R/. ") + BRS)
                //NEW
                .replace("℟", toRedFont("℟."))
                //.replace("℟",  toRed("℟") )

                .replace("℣", toRedFont("℣."))
                .replace("≀", BR + NBSP_4 + NBSP_4)
                .replace("~", BR)
                .replace("§", BRS)
                .replace("∸", BRS)
                .replace("⊞", toRedFont("✚. "))
                .replace("⊝", toRedFont("C. "))
                .replace("⊟", toRedFont("S. "))
                .replace("[rubrica]", CSS_RED_A)
                .replace("[/rubrica]", CSS_RED_Z)
        ;
        return sFormateado;
    }

    /**
     * <p>Extrae, para la lectura por voz, algunos caracteres que pueden agregarse para la presentación visual.</p>
     *
     * @param sOrigen Cadena original para hacer el reemplazo
     * @return La cadena formateada
     * @since 2023.1
     */
    public static String getFormatoForRead(String sOrigen) {
        String sFormateado;
        sFormateado = sOrigen
                .replace("_", " ")
                .replace("§", " ")
                .replace("~", " ")
                .replace("¦", " ")
                .replace("⊣", " ")
                .replace("≠", " ")
                .replace("∞", " ")
                .replace("⊚", OBIEN)
                .replace("†", "")
                .replace("⊓", " ")
                .replace("Ɽ", " ")
                .replace("⟨", " ")
                .replace("⟩", " ")
                .replace("ⱱ", " ")
                .replace("ⱴ", " ")
                .replace("Ʀ", " ")
                .replace("℟", " ")
                .replace("℣", " ")
                .replace("≀", " ")
                .replace("~", " ")
                .replace("§", " ")
                .replace("∸", " ")
                .replace("⊞", " ")
                .replace("⊝", " ")
                .replace("⊟", " ")
                .replace("[rubrica]", " ")
                .replace("[/rubrica]", " ")

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
                .replace("⊞", " ")
                .replace("⊝", " ")
                .replace("⊟", " ")
                .replace("⊓", " ")

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

    /*
        Solución a fromHTML deprecated...
        ver: http://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
     */
    //noinspection
    public static Spanned fromHtml(String s) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(getFormato(s), Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(getFormato(s));
        }
    }

    //noinspection
    public static Spanned fromHtmlForRead(String s) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(getFormatoForRead(s), Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(getFormatoForRead(s));
        }
    }

    public static SpannableStringBuilder fromHtmlSmall(String s) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(fromHtml(s));
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
                .replace("⊞", " ")
                .replace("⊝", " ")
                .replace("⊟", " ")
                .replace("⊓", " ")
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
        return sOrigen.endsWith(".") ? sOrigen : String.format("%s.", sOrigen);
    }


    /**
     * Método que devuelve la fecha del sistema menos N días en formato yyyyMMdd
     *
     * @param minusDays Cantidad de días a sustraer
     * @return Un número con la fecha
     */

    public static int getTodayMinus(int minusDays) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String today = LocalDate.now().minusDays(minusDays).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return Integer.parseInt(today);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -minusDays);
        String s = df.format(cal.getTime());
        return Integer.parseInt(s);
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
     * Método que devuelve la fecha y hora actual en formato yyyy-MM-dd HH:mm:ss
     *
     * @return Una cadena con la fecha
     */

    public static String getCurrentTimeStamp() {

        LocalDateTime myObj = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myObj.format(myFormat);

    }

    /**
     * Método que devuelve la fecha de formato DateTime en formato yyyyMMdd
     *
     * @param s La Fecha en formato DateTime
     * @return Una cadena con la fecha
     */

    public static String formatDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", new Locale("es", "ES"));
        Date dateObj;
        try {
            dateObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("es", "ES")).parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return sdf.format(Objects.requireNonNull(dateObj));
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
     *
     * @param dateString La fecha a formatear
     * @return Una cadena con la fecha
     */

    public static String getLongDate(String dateString) {
        Locale loc = new Locale("es", "ES");
        SimpleDateFormat longFormat =
                new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", loc);
        DateFormat df = new SimpleDateFormat("yyyyMMdd", loc);
        try {
            return longFormat.format(Objects.requireNonNull(df.parse(dateString)));
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * Formatea una fecha según el patrón: 22 de Agosto 1972 para mostrarla en la barra superior.
     *
     * @param dateString La fecha a formatear
     * @return Una cadena con la fecha
     */

    public static String getTitleDate(String dateString) {
        SimpleDateFormat sdf =
                new SimpleDateFormat("d '-' MMMM yyyy", new Locale("es", "ES"));
        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            return sdf.format(Objects.requireNonNull(df.parse(dateString)));
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * Método que devuelve el mes y el día de una fecha dada
     *
     * @param dateString La fecha a formatear
     * @return Un array con el mes y el día de la fecha
     * @since 2023.1
     */

    public static int[] getMonthAndDay(String dateString) {
        Calendar c = Calendar.getInstance();
        int[] monthAndDay = new int[2];
        try {
            Date theDate = new SimpleDateFormat("yyyyMMdd", new Locale("es", "ES")).parse(dateString);
            c.setTime(Objects.requireNonNull(theDate));
            monthAndDay[0] = c.get(Calendar.MONTH) + 1;
            monthAndDay[1] = c.get(Calendar.DAY_OF_MONTH);
            return monthAndDay;
        } catch (ParseException e) {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public static int getDayOfWeek(Integer theDate) {
        String dateString = String.valueOf(theDate);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd", new Locale("en", "US"));
            LocalDate ld = LocalDate.parse(dateString, dtf);
            int dow = ld.getDayOfWeek().getValue();
            return (dow == 7) ? 1 : dow + 1;
        } else {
            return getDayOfWeekOld(theDate);
        }
    }

    public static int getDayOfWeekOld(Integer theDate) {
        String dateString = String.valueOf(theDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        try {
            Date date = sdf.parse(dateString);
            c.setFirstDayOfWeek(Calendar.SUNDAY);
            c.setTime(Objects.requireNonNull(date));
            return c.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            c = Calendar.getInstance();
            return c.get(Calendar.DAY_OF_WEEK);
        }
    }

    /**
     * Formatea una cadena de fecha haciéndola apta para consultas en Firebase
     *
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
     *
     * @param dateString Cadena con la fecha
     * @return La cadena limpia
     * @since 2022.01
     */
    public static String cleanDate(String dateString) {
        return dateString.replaceAll("/", "");
    }

    /**
     * Crea mensajes de error personalizados
     *
     * @param msg    El mensaje de error
     * @param params Los parámetros que se usaron para obtener la información en el contexto
     * @return Una cadena con el mensaje personalizado
     * @since 2022.01
     */
    public static String createErrorMessage(String msg, String params) {
        return String.format("%s\nParámetros: %s", msg, params);
    }

    public static String createErrorMessage(String msg) {
        return String.format(new Locale("es"), "Ha ocurrido el siguiente error:%s%s %s%s %sVersión de la aplicación: %s", LS2, msg, LS2, ERR_REPORT, LS2, VERSION_CODE);
    }

    /**
     * Obtiene las dos cifras del mes desde una cadena de fecha
     *
     * @param date La fecha en formato yyyymmdd
     * @return Una cadena con la parte del mes
     * @since 2022.1
     */
    @SuppressWarnings("unused")
    public static String getMonth(String date) {
        return date.substring(4, 6);
    }

    /**
     * Obtiene las dos cifras del día desde una cadena de fecha
     *
     * @param date La fecha en formato yyyymmdd
     * @return Una cadena con la parte del día
     * @since 2022.1
     */
    public static String getDay(String date) {
        return date.substring(6, 8);
    }

    @SuppressWarnings("unused")
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
        return String.format("%s.", s);
    }

    public static String toUpper(String s) {
        return s.toUpperCase(Locale.getDefault());
    }

    public static String toLower(String s) {
        return s.toLowerCase(Locale.getDefault());
    }

    public static SpannableStringBuilder toH1Red(String sOrigen) {

        RelativeSizeSpan h2 = new RelativeSizeSpan(H1);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(h2), 0,
                spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(ColorUtils.getRed()), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }


}
