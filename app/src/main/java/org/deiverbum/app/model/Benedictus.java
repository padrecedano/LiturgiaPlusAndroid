package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import org.deiverbum.app.utils.Utils;

public class Benedictus extends Salmo{

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("CÁNTICO EVANGÉLICO");
    }
    public String getHeaderForRead() {
        return "CÁNTICO EVANGÉLICO.";
    }

    public Spanned getTexto() {
        String s = "Bendito sea el Señor, Dios de Israel,_porque ha visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tiniebla_y en sombra de muerte,_para guiar nuestros pasos_por el camino de la paz.";
        return Utils.fromHtml(s);
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getAntifonaFormatted());
        sb.append(LS2);
        sb.append(getTexto());
        sb.append(LS2);
        sb.append(getFinSalmo());
        sb.append(LS2);
        sb.append(getAntifonaFormatted());
        return sb;
    }


    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(getHeaderForRead());
        sb.append(getAntifona());
        sb.append(getTexto());
        sb.append(getFinSalmo());
        sb.append(getAntifona());
        return sb;
    }
    public SpannableStringBuilder getAntifonaFormatted() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.toRed("Ant. "));
        sb.append(antifona);
        return sb;
    }
}