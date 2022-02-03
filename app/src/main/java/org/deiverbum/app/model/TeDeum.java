package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class TeDeum {
    public boolean status;
    public String texto;

    public TeDeum() {
    }
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public final String getTexto() {
        String teDeum = "<p>Señor, Dios eterno, alegres te cantamos, <br />a ti nuestra alabanza, <br />a ti, Padre del cielo, te aclama la creación. <br /><br />Postrados ante ti, los ángeles te adoran <br />y cantan sin cesar: <br /><br />Santo, santo, santo es el Señor, <br />Dios del universo; <br />llenos están el cielo y la tierra de tu gloria. <br /><br />A ti, Señor, te alaba el coro celestial de los apóstoles, <br />la multitud de los profetas te enaltece, <br />y el ejército glorioso de los mártires te aclama. <br /><br />A ti la Iglesia santa, <br />por todos los confines extendida, <br />con júbilo te adora y canta tu grandeza: <br /><br />Padre, infinitamente santo, <br />Hijo eterno, unigénito de Dios, <br />Santo Espíritu de amor y de consuelo. <br /><br />Oh Cristo, tú eres el Rey de la gloria, <br />tú el Hijo y Palabra del Padre, <br />tú el Rey de toda la creación. <br /><br />Tú, para salvar al hombre, <br />tomaste la condición de esclavo <br />en el seno de una virgen. <br /><br />Tú destruiste la muerte <br />y abriste a los creyentes las puertas de la gloria. <br /><br />Tú vives ahora, <br />inmortal y glorioso, en el reino del Padre. <br /><br />Tú vendrás algún día, <br />como juez universal. <br /><br />Muéstrate, pues, amigo y defensor <br />de los hombres que salvaste. <br /><br />Y recíbelos por siempre allá en tu reino, <br />con tus santos y elegidos. <br /><br />Salva a tu pueblo, Señor, <br />y bendice a tu heredad. <br /><br />Sé su pastor, <br />y guíalos por siempre. <br /><br />Día tras día te bendeciremos <br />y alabaremos tu nombre por siempre jamás. <br /><br />Dígnate, Señor, <br />guardarnos de pecado en este día. <br /><br />Ten piedad de nosotros, Señor, <br />ten piedad de nosotros. <br /><br />Que tu misericordia, Señor, venga sobre nosotros, <br />como lo esperamos de ti. <br /><br />A ti, Señor, me acojo, <br />no quede yo nunca defraudado.</p>";
        return teDeum;
    }

    public final Spanned getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        if (status) {
            sb.append(getHeader());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(getTexto()));
        }
        return sb;
    }

    /**
     * @deprecated desde la versión 2022.01 - Usar en su lugar {@link TeDeum#getAll()}
     * @return Contenido del TeDeum
     */
    @Deprecated
    public final Spanned getTextoSpan() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        if (status) {
            sb.append(getHeader());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(getTexto()));
        }
        return sb;
    }

    //TODO quitar la propiedad texto de la respuesta de API,
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String ifApplicable() {
        return "";
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("TE DEUM");
    }

    public String getAllForRead() {
        StringBuilder sb=new StringBuilder();
        if (status) {
            sb.append(getHeader());
            sb.append(".");
            sb.append(Utils.fromHtml(getTexto()));
        }
        return sb.toString();
    }
}

