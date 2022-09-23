package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_PATER_NOSTER;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class PadreNuestro {
    private final String texto;

    public PadreNuestro() {
        this.texto="Padre nuestro,~¦que estás en el cielo,~¦santificado sea tu Nombre;~¦" +
                "venga a nosotros tu reino;~¦hágase tu voluntad~¦en la tierra como en el cielo.~¦" +
                "Danos hoy nuestro pan de cada día;~¦perdona nuestras ofensas,~¦" +
                "como también nosotros perdonamos a los que nos ofenden;~¦" +
                "no nos dejes caer en la tentación,~¦y líbranos del mal. Amén.";
    }

    public String getTexto() {
        return this.texto;
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.formatTitle(TITLE_PATER_NOSTER));
        sb.append(LS2);
        sb.append(Utils.fromHtml(getTexto()));
        return sb;
    }


    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        sb.append(Utils.fromHtml(getTexto()));
        return sb;
    }
}