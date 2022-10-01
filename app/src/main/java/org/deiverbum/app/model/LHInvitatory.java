package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.LS2;
import static org.deiverbum.app.utils.Constants.PRE_ANT;
import static org.deiverbum.app.utils.Constants.TITLE_INVITATORY;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

/*
    IDs de los salmos invitatorios:
        LHPsalm 94: ID 315, insertado recientemente
        LHPsalm 99: ID 86
        LHPsalm 66: ID 62
        LHPsalm 23: ID 101
 */
public class LHInvitatory extends LHPsalm {
    @Ignore
    public boolean isMultiple;

    public LHInvitatory() {
    }

    /**
     * Determina si el usuario tiene configurado un invitatorio variable
     * y devuelve el texto que corresponda.
     *
     * @return El texto obtenido del archivo correspondiente o el texto por defecto
     * @since 2022.1
     */
    public Spanned getTextoSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        if (isMultiple) {
            ssb.append(getRef());
            ssb.append(LS2);
            ssb.append(Utils.fromHtml(getSalmo()));
        } else {
            ssb.append(Utils.toRed("Salmo 94."));
            ssb.append(LS2);
            ssb.append(Utils.fromHtml(getUnique()));
        }
        return ssb;
    }

    @SuppressWarnings("SameReturnValue")
    public String getTitle() {
        return TITLE_INVITATORY;
    }

    public String getTitleForRead() {
        return Utils.pointAtEnd(TITLE_INVITATORY);
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_INVITATORY);
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.formatSubTitleToLower(getTitle()));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(PRE_ANT));
        sb.append(getAntifona());
        sb.append(Utils.LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        sb.append(getFinSalmo());
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(PRE_ANT));
        sb.append(getAntifona());
        return sb;
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getTitleForRead());
        sb.append(getAntifona());
        sb.append(getTextoSpan());
        sb.append(getFinSalmo());
        sb.append(getAntifona());
        return sb;
    }

    public String getUnique() {
        return "Venid, aclamemos al Señor,_demos vítores a la Roca que nos salva;_entremos a su presencia dándole gracias,_aclamándolo con cantos.§Porque el Señor es un Dios grande,_soberano de todos los dioses,_tiene en su mano las simas de la tierra,_son suyas las cumbres de los montes. _Suyo es el mar, porque él lo hizo,_la tierra firme que modelaron sus manos.§Venid, postrémonos por tierra,_bendiciendo al Señor, creador nuestro. _Porque él es nuestro Dios,_y nosotros su pueblo,_el rebaño que él guía.§Ojalá escuchéis hoy su voz:_«No endurezcáis el corazón como en Meribá, _como el día de Masá en el desierto:_cuando vuestros padres me pusieron a prueba,_y dudaron de mí, aunque habían visto mis obras.§Durante cuarenta años_aquella generación me repugnó, y dije:_“Es un pueblo de corazón extraviado,_que no reconoce mi camino;_por eso he jurado en mi cólera_que no entrarán en mi descanso”».";
    }
}