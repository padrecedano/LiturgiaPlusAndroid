package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_GOSPEL_CANTICLE;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class LHGospelCanticle extends LHPsalm {
    @Ignore
    public Integer tipo;

    public LHGospelCanticle() {}

    public void setTipo(Integer tipo) {
        this.tipo=tipo;
    }


    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle(TITLE_GOSPEL_CANTICLE);
    }
    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_GOSPEL_CANTICLE);
    }

    public Spanned getTexto() {
        return Utils.fromHtml(getText());
    }

    public String getText() {
        if(tipo==2) {

            return "Bendito sea el Señor, Dios de Israel,_porque ha " +
                    "visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tinieblas_y en sombras de muerte,_para guiar nuestros pasos_por el camino de la paz.";
        }else if (tipo==6){
            return "Proclama mi alma la grandeza del Señor,_se alegra mi " +
                "espíritu en Dios, mi salvador;_porque ha mirado la humillación de su esclava.§Desde ahora me felicitarán todas las generaciones,_porque el Poderoso ha hecho obras grandes por mí:_su nombre es santo,_y su misericordia llega a sus fieles_de generación en generación.§Él hace proezas con su brazo:_dispersa a los soberbios de corazón,_derriba del trono a los poderosos y enaltece a los humildes,_a los hambrientos los colma de bienes_y a los ricos los despide vacíos.§Auxilia a Israel, su siervo,_acordándose de la misericordia_—como lo había prometido a nuestros padres—_en favor de Abrahán y su descendencia por siempre.";

        }else{
            return "";
        }
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