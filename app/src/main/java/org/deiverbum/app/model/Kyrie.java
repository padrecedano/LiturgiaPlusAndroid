package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

import java.util.Random;

public class Kyrie {

    @SerializedName("introduccion")
    @Expose
    private String introduccion;
    @SerializedName("texto")
    @Expose
    private String texto;
    @SerializedName("conclusion")
    @Expose
    private String conclusion;

    @SuppressWarnings("unused")
    private String tipo;
    private int kyrieType;

    public SpannableStringBuilder getIntroduccionForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.fromHtml("<p>EXAMEN DE CONCIENCIA.</p>"));
        String[] introArray = introduccion.split("\\|");
        if (introArray.length == 3) {

            ssb.append(Utils.fromHtml("<p>" + introArray[1] + "</p>"));


        } else {
            //ssb.append(String.valueOf(introArray.length));
            ssb.append(introduccion);

        }
        return ssb;
    }

    public SpannableStringBuilder getIntroduccion() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.formatTitle("EXAMEN DE CONCIENCIA."));
        ssb.append(Utils.LS2);

        String[] introArray = introduccion.split("\\|");
        if (introArray.length == 3) {
            ssb.append(Utils.toSmallSizeRed(introArray[0]));
            ssb.append(Utils.LS2);
            ssb.append(introArray[1]);
            ssb.append(Utils.LS2);
            ssb.append(Utils.toSmallSizeRed(introArray[2]));

        } else {
            ssb.append(introduccion);

        }
        return ssb;
    }

    @SuppressWarnings("unused")
    public void setIntroduccion(String introduccion) {
        this.introduccion = introduccion;
    }

    public SpannableStringBuilder getTexto() {
        kyrieType = new Random().nextInt(3);
        return Utils.getKyrie(kyrieType);
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getConclusionForRead() {
        return "El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna. Amén.";


    }
    public SpannableStringBuilder getConclusion() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        ssb.append(Utils.toSmallSizeRed("Pueden usarse otras invocaciones penitenciales."));
        ssb.append(Utils.LS);
        ssb.append(Utils.toSmallSizeRed("Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos:"));
        ssb.append(Utils.LS2);

        ssb.append(Utils.toRed("V. "));
        ssb.append("El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.");
        ssb.append(Utils.LS2);
                ssb.append(Utils.toRed("R. "));
        ssb.append("Amén.");


        return ssb;
        //        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getTipo() {
        return tipo;
    }

    public SpannableStringBuilder getTextoForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        switch (kyrieType) {
            case 0:
                String text = "<p>Yo confieso ante Dios todopoderoso <br />" +
                        "y ante vosotros, hermanos <br />" +
                        "que he pecado mucho <br />" +
                        "de pensamiento, palabra, obra y omisión: <br />" +
                        "por mi culpa, por mi culpa, por mi gran culpa. <br /><br />" +
                        "Por eso ruego a santa María, siempre Virgen, <br />" +
                        "a los ángeles, a los santos y a vosotros, hermanos, <br />" +
                        "que intercedáis por mí ante Dios, nuestro Señor.</p>";

                ssb.append(Utils.fromHtml(text));

                break;
            case 1:
                ssb.append(Utils.fromHtml("<p>Señor, ten misericordia de nosotros.</p>"));
                ssb.append(Utils.fromHtml("<p>Porque hemos pecado contra ti.</p>"));
                ssb.append(Utils.fromHtml("<p>Muéstranos, Señor, tu misericordia.</p>"));
                ssb.append(Utils.fromHtml("<p>Y danos tu salvación.</p>"));

                break;
            case 2:
                ssb.append(Utils.fromHtml("<p>Tú que has sido enviado a sanar los corazones afligidos: Señor, ten piedad.</p>"));
                ssb.append(Utils.fromHtml("<p>Señor, ten piedad.</p>"));
                ssb.append(Utils.fromHtml("<p>Tú que has venido a llamar a los pecadores: Cristo, ten piedad.</p>"));
                ssb.append(Utils.fromHtml("<p>Cristo, ten piedad.</p>"));
                ssb.append(Utils.fromHtml("<p>Tú que estás sentado a la derecha del Padre para interceder por nosotros: Señor, ten piedad.</p>"));
                ssb.append(Utils.fromHtml("<p>Señor, ten piedad.</p>"));


            default:
                ssb.append("");
                break;
        }


        return ssb;

    }


    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getIntroduccion());
        sb.append(Utils.LS2);
        sb.append(getTexto());
        sb.append(Utils.LS2);
        sb.append(getConclusion());
        return sb;
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getIntroduccionForRead());
        sb.append(getTextoForRead());
        sb.append(getConclusionForRead());
        return sb;
    }
}
