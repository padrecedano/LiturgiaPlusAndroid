package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_GOSPEL_CANTICLE;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class Magnificat extends LHPsalm {

    public Magnificat() {
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle(TITLE_GOSPEL_CANTICLE);
    }
    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_GOSPEL_CANTICLE);
    }
    public Spanned getTexto() {
        String magnificat ="Proclama mi alma la grandeza del Señor,_se alegra mi espíritu en Dios, mi salvador;_porque ha mirado la humillación de su esclava.§Desde ahora me felicitarán todas las generaciones,_porque el Poderoso ha hecho obras grandes por mí:_su nombre es santo,_y su misericordia llega a sus fieles_de generación en generación.§Él hace proezas con su brazo:_dispersa a los soberbios de corazón,_derriba del trono a los poderosos y enaltece a los humildes,_a los hambrientos los colma de bienes_y a los ricos los despide vacíos.§Auxilia a Israel, su siervo,_acordándose de la misericordia_—como lo había prometido a nuestros padres—_en favor de Abrahán y su descendencia por siempre.";
        return Utils.fromHtml(magnificat);
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
    }}
