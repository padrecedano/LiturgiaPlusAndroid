package org.deiverbum.liturgiacatolica.utils;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.deiverbum.liturgiacatolica.utils.Constants.BR;
import static org.deiverbum.liturgiacatolica.utils.Constants.BRS;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_SM_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_SM_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.ERR_RESPONSORIO;
import static org.deiverbum.liturgiacatolica.utils.Constants.FIN_SALMO;
import static org.deiverbum.liturgiacatolica.utils.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.utils.Constants.NBSP_SALMOS;
import static org.deiverbum.liturgiacatolica.utils.Constants.OBIEN;
import static org.deiverbum.liturgiacatolica.utils.Constants.PRECES_IL;
import static org.deiverbum.liturgiacatolica.utils.Constants.PRECES_R;
import static org.deiverbum.liturgiacatolica.utils.Constants.PRE_ANT;
import static org.deiverbum.liturgiacatolica.utils.Constants.RESP_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.RESP_R;
import static org.deiverbum.liturgiacatolica.utils.Constants.RESP_V;

// Created by cedano on 14/12/16.


public class Utils {
    //Ojo solución a fromHTML deprecated... ver: http://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }



    /**
     * Método que da formato a algunos textos recibidos desde el servidor <br />
     * He creado una especie de contrato entre la API y la APP para reducir el volúmen de datos <br />
     * que se transmite y formatear el contenido de forma local. <br />
     * El reemplazo de caracteres es como sigue: <br />
     * § : salto de párrafo <br />
     * _ : salto de línea y sangría (para los salmos y algunos himnos)
     * ~ : salto de línea sin sangría
     * ¦ : DIFERENCIAR UNO CON SALTO Y OTRO SIN SALTO varios espacios en blanco (una especia de tabulador), se usa en algunas referencias de textos
     * ∞ : la rúbrica <i>Se pueden añadir algunas intenciones libres.</i>
     * ≠ : salto de línea y sangría con un guión rojo, para las preces
     * ℇ : rúbrica litúrgica "O bien"
     * † : cruz en antífonas
     * ƞ : la N. de color rojo que sustituye el nombre del Papa o del Obispo
     * Ʀ : la R./ de color rojo (responsorio)
     * Ɽ : la R. de color rojo (responsorio sin ./)
     * ⟨ : paréntesis de apertura en rojo
     * ⟩ : paréntesis de cierre en rojo
     * ã : (T. P. Aleluya.):
     * ≀ : NBSP4 + brs
     *
     * @param sOrigen El texto del salmo como es recibido del servidor (los saltos de línea vienen indicados por '_' y de párrafo por '§'
     * @return Una cadena con el salmo formateado.
     */

    public String getFormato(String sOrigen) {
        String sFormateado;
//α β γ δ ε ϝ ϛ ζ η θ ι κ λ μ ν ξ ο π ϟ ϙ ρ σ τ υ φ χ ψ ω ϡ
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫ ∬ ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿

        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟

        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿

        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟

        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿

*/
        sFormateado = sOrigen
                .replace("_", NBSP_SALMOS)
                .replace("§", BRS)
                .replace("~", BR)
                .replace("¦", NBSP_4)
                .replace("⊣", BR + NBSP_4)
                .replace("≠", PRECES_R)
                .replace("∞", PRECES_IL)
                .replace("ℇ", OBIEN)
                .replace("†", CSS_RED_A+" † "+CSS_RED_Z)
                .replace("ƞ", CSS_RED_A+" N. "+CSS_RED_Z)
                .replace("Ɽ", CSS_RED_A+" R. "+CSS_RED_Z)
                .replace("⟨", CSS_RED_A+"("+CSS_RED_Z)
                .replace("⟩", CSS_RED_A+")"+CSS_RED_Z)
                .replace("ⱱ", CSS_RED_A+"V/."+CSS_RED_Z)
                .replace("ⱴ", CSS_RED_A+"R/."+CSS_RED_Z)
                .replace("Ʀ", CSS_RED_A+" R/. "+CSS_RED_Z+BRS);


        return sFormateado;
    }

    /**
     * Método que organiza todos los componentes de un salmo dado, evaluando los que son nulos para evitar espacios vacíos
     * En el caso de las antífonas, llama a su vez al método getAntifonaLimpia() para limpiar la segunda parte de la misma
     * @param sOrden    El orden del salmo: 1, 2, 3
     * @param sAntifona El texto de la antífona
     * @param sRef      La referencia del salmo
     * @param sTema     El tema (presente sólo en algunos salmos)
     * @param sIntro    El texto introductorio (presente sólo en algunos salmos)
     * @param sSalmo    El texto del salmo propiamente dicho
     * @return Una cadena formateda con el salmo completo
     */

    public String getSalmoCompleto(String sOrden, String sAntifona, String sRef, String sTema, String sIntro, String sParte, String sSalmo)
    {
        if (sRef != null && !sRef.isEmpty()) {
            sRef = CSS_RED_A + sRef + CSS_RED_Z + BRS;

        } else {
            sRef = "";
        }


        if (sTema != null && !sTema.isEmpty()) {
            sTema = CSS_RED_A + CSS_SM_A + sTema.toUpperCase() + CSS_SM_Z + CSS_RED_Z + BRS;

        } else {
            sTema = "";
        }

        if (sIntro != null && !sIntro.isEmpty()) {
            sIntro = CSS_SM_A + getFormato(sIntro) + CSS_SM_Z + BRS;

        } else {
            sIntro = "";
        }

        if (sParte != null && !sParte.isEmpty()) {
            sParte = CSS_RED_A + sParte + CSS_RED_Z + BRS;

        } else {
            sParte = "";
        }


        return CSS_RED_A + PRE_ANT + sOrden + ". " + CSS_RED_Z + getFormato(sAntifona) + BRS + getFormato(sRef) +
                sTema + sIntro + sParte + getFormato(sSalmo) + BRS + FIN_SALMO + BRS +
                CSS_RED_A + PRE_ANT + CSS_RED_Z + getAntifonaLimpia(sAntifona) + BRS;
    }

    /**
     * Método que limpia la segunda parte de la antífona, en el caso del símblo †
     *
     * @param sAntifona Una cadena con el texto de la antífona
     * @return La misma cadena, pero sin el referido símbolo
     */

    public String getAntifonaLimpia(String sAntifona) {
        return sAntifona.replace("†", "");
    }

        /**
         * Método que crea las preces *** terminar descripción luego
         *
         * @param introArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
         * @param sPreces    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
         * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
         */

    public String getPreces(String introArray[], String sPreces)
    {
        String sFinal;
        sFinal = introArray[0] + BRS + "<i>" + introArray[1] + "</i>" + BRS + getFormato(sPreces) +BRS + introArray[2];
        return sFinal;
    }

    /**
     * Método que crea las preces *** terminar descripción luego
     *
     * @param sOrigen Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    public String getHimnos(String sOrigen)
    {
        String sFinal = "";
        if (!isNull(sOrigen)) {

            String[] himnoArray = sOrigen.split("\\|");
            String[] himno1Array = himnoArray[0].split("\\_");
            String[] himno2Array = himnoArray[1].split("\\_");
            switch (Integer.parseInt(himno1Array[0])) {
                case 0:
                    sFinal = CSS_RED_A + "En los domingos y solemnidades:" + CSS_RED_Z +
                            BRS + getFormato(himno1Array[1]);
                    break;
                default:
                    break;
            }

            switch (Integer.parseInt(himno2Array[0])) {
                case 2:
                    sFinal = sFinal + BRS + CSS_RED_A + "O bien, fuera de los domingos y de las solemnidades:" + CSS_RED_Z +
                            BRS + getFormato(himno2Array[1]);
                    break;
                default:
                    break;
            }
        } else {
            sFinal = "";
        }
        return sFinal;
    }

    /**
     * Método que crea la cadena completa de un responsorio dado
     *
     * @param respArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param nForma    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    public String getResponsorio(String respArray[], int nForma)
    {
        String sResponsorio;
        switch (nForma) {
// Modificar los case, usando el modelo: 6001230
            case 1:
                if (respArray.length == 3) {
                    sResponsorio =
                            RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                    RESP_V + respArray[2] + BRS +
                                    RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);
                } else {

                    sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + getHoy() + BRS;


                }
                break;

            case 2:
                //0-1-2-1
                sResponsorio =
                        RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                RESP_V + respArray[2] + BRS +
                                RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);

                break;


            case 6001230:
                //6 partes distribuidas así: 0-0-1-2-3-0, de ahí el código 6001230... si no, imposible entenderse
                // Suele ser el modelo de responsorio para Laudes
                if (respArray.length == 4) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[2] + BRS +
                                    RESP_V + respArray[3] + BR +
                                    RESP_R + respArray[0] + BRS;
                } else {
                    sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + getHoy() + BRS;
                }
                break;


            case 6001020:
                //6 partes distribuidas así: 0-0-1-0-2-0, de ahí el código 6001230... si no, imposible entenderse
                // Suele ser el modelo de responsorio para Laudes
                if (respArray.length == 3) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[2] + BR +
                                    RESP_R + respArray[0] + BRS;
                } else {
                    sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + getHoy() + BRS;
                }
                break;


            case 4:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[1] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[2] + BR +
                                RESP_R + respArray[0] + BRS;
                break;

            case 31:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[1] + BRS;
                break;


            default:
                sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + getHoy() + BRS;
                break;
        }
        return sResponsorio;


    }

    /**
     * Método que evalua si una cadena no tiene datos <br />
     *
     * @param sOrigen La cadena a evaluar
     * @return Verdadero o Falso.
     */

    public Boolean isNull(String sOrigen)
    {
        return !(sOrigen != null && !sOrigen.isEmpty() && !sOrigen.equals("null"));
    }

    /**
     * Método que devuelve la fecha del sistema
     *
     * @return Una cadena con la fecha
     */

    public String getHoy() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return format.format(new Date());
    }


    /**
     * Método que devuelve la fecha del sistema en forma MMDD para santoral
     *
     * @return Una cadena con la fecha
     */

    public String getHoyMMdd() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("MMdd", Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * Método que devuelve la hora del sistema <br />
     *
     * @param strFormato Formato de la hora deseado
     * @return strHora Cadena con la hora formateada.
     */

    public String getHora(String strFormato) {

        Calendar objCalendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormato);

        return simpleDateFormat.format(objCalendar.getTime());

    }


    /**
     * Método que devuelve la fecha del sistema en forma legible
     *
     * @return Una cadena con la fecha
     */

    public String getFecha() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM yyyy", Locale.getDefault());
        return format.format(new Date());
    }


    public void mensajeTemporal(Context context) {
//        Context context = getApplicationContext();
        CharSequence text = "Este módulo se encuentra en fase de desarrollo... perdona las molestias.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void setFabric(String strEvento, String strTag, String strFechaHoy) {

        Answers.getInstance().logCustom(new CustomEvent(strEvento)
                .putCustomAttribute(strTag, strFechaHoy));


    }


}
