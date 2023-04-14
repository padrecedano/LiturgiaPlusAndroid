package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.ERR_RESPONSORIO;
import static org.deiverbum.app.utils.Constants.LS2;
import static org.deiverbum.app.utils.Constants.TITLE_RESPONSORY_SHORT;
import static org.deiverbum.app.utils.Utils.LS;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.Locale;

public class LHResponsoryShort {

    protected Integer responsoryID;
    protected String text;
    protected Integer type;

    public Integer getResponsoryID() {
        return responsoryID;
    }

    public void setResponsoryID(Integer responsoryID) {
        this.responsoryID = responsoryID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public SpannableStringBuilder getHeader(int hourId) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (hourId < 3 || hourId > 5) {
            sb.append(Utils.toRed(String.format(new Locale("es"), "%s%s%s", Utils.toUpper(TITLE_RESPONSORY_SHORT), "     ", "getRef()")));
        }
        return sb;
    }

    public SpannableStringBuilder getHeader() {
        if (this.type > 0) {
            return Utils.formatTitle(TITLE_RESPONSORY_SHORT);
        } else {
            return Utils.toRed("En lugar del responsorio breve, se dice la siguiente antífona:");
        }
    }

    /**
     * <p>Método que crea la cadena completa de un responsorio dado.</p>
     *
     * @param hourId El ID de la hora
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     * @since 2022.01
     */

    public SpannableStringBuilder getAll(int hourId) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String[] respArray = text.split("\\|");
        StringBuilder s = new StringBuilder();
        if (hourId < 3 || hourId > 5) {
            sb.append(getHeader());
            s.append(BRS);
        }

        switch (type) {
            case 0:
                sb.append(LS2);
                sb.append(text);
                sb.append(LS);

                break;
            case 1:
                if (respArray.length == 3) {
                    s.append(Utils.toRedFont("R. "));
                    s.append(respArray[0]);
                    s.append(Utils.toRedFont(" * "));
                    s.append(respArray[1]);
                    s.append(BRS);
                    s.append(Utils.toRedFont("V. "));
                    s.append(respArray[2]);
                    s.append(BRS);
                    s.append(Utils.toRedFont("R. "));
                    s.append(Character.toUpperCase(respArray[1].charAt(0)));
                    s.append(respArray[1].substring(1));
                    s.append(BRS);
                    sb.append(Utils.fromHtml(s.toString()));
                }
                break;

            case 2:
                s.append(Utils.toRedFont("R. "));
                s.append(respArray[0]);
                s.append(Utils.toRedFont(" * "));
                s.append(respArray[1]);
                s.append(BRS);
                s.append(Utils.toRedFont("V. "));
                s.append(respArray[2]);
                s.append(BRS);
                s.append(Utils.toRedFont("R. "));
                s.append(Character.toUpperCase(respArray[1].charAt(0)));
                s.append(respArray[1].substring(1));
                s.append(BRS);
                sb.append(Utils.fromHtml(s.toString()));
                break;

            /*
             *6 partes distribuidas así: 0-0-1-2-3-0, de ahí el código 6001230...
             *Suele ser el modelo de responsorio para Laudes
             */
            case 6001230:
                if (respArray.length == 4) {
                    s.append(Utils.toRedFont("V. "));
                    s.append(respArray[0]);
                    s.append(BR);
                    s.append(Utils.toRedFont("R. "));
                    s.append(respArray[0]);
                    s.append(BRS);
                    s.append(Utils.toRedFont("V. "));
                    s.append(respArray[1]);
                    s.append(BR);
                    s.append(Utils.toRedFont("R. "));
                    s.append(respArray[2]);
                    s.append(BRS);
                    s.append(Utils.toRedFont("V. "));
                    s.append(respArray[3]);
                    s.append(BR);
                    s.append(Utils.toRedFont("R. "));
                    s.append(respArray[0]);
                    s.append(BRS);
                    sb.append(Utils.fromHtml(s.toString()));

                }
                break;

            case 6001020:
                if (respArray.length == 3) {
                    s.append(Utils.toRedFont("V. "));
                    s.append(respArray[0]);
                    s.append(BR);
                    s.append(Utils.toRedFont("R. "));
                    s.append(respArray[0]);
                    s.append(BRS);
                    s.append(Utils.toRedFont("V. "));
                    s.append(respArray[1]);
                    s.append(BR);
                    s.append(Utils.toRedFont("R. "));
                    s.append(respArray[0]);
                    s.append(BRS);
                    s.append(Utils.toRedFont("V. "));
                    s.append(respArray[2]);
                    s.append(BR);
                    s.append(Utils.toRedFont("R. "));
                    s.append(respArray[0]);
                    s.append(BRS);
                    sb.append(Utils.fromHtml(s.toString()));
                }
                break;

            case 4:
                s.append(Utils.toRedFont("V. "));
                s.append(respArray[0]);
                s.append(BR);
                s.append(Utils.toRedFont("R. "));
                s.append(respArray[0]);
                s.append(BRS);
                s.append(Utils.toRedFont("V. "));
                s.append(respArray[1]);
                s.append(BR);
                s.append(Utils.toRedFont("R. "));
                s.append(respArray[0]);
                s.append(BRS);
                s.append(Utils.toRedFont("V. "));
                s.append(respArray[2]);
                s.append(BR);
                s.append(Utils.toRedFont("R. "));
                s.append(respArray[0]);
                s.append(BRS);
                sb.append(Utils.fromHtml(s.toString()));
                break;

            case 201:
                s.append(Utils.toRedFont("V. "));
                s.append(respArray[0]);
                s.append(BR);
                s.append(Utils.toRedFont("R. "));
                s.append(respArray[1]);
                s.append(BRS);
                sb.append(Utils.fromHtml(s.toString()));

                break;

            default:
                sb.append(Utils.fromHtml(ERR_RESPONSORIO));
                sb.append(LS);
                sb.append("Tamaño del responsorio: ");
                sb.append(String.valueOf(respArray.length));
                sb.append(" Código forma: ");
                sb.append(String.valueOf(type));
                sb.append(LS);
                break;
        }
        return sb;
    }

    /**
     * Devuelve el texto del LHResponsoryShort Breve con formato
     * y adaptación adecuada para la lectura de voz
     *
     * @return Una cadena formateada con el responsorio
     */
    public String getAllForRead() {
        String[] respArray = text.split("\\|");
        StringBuilder s = new StringBuilder();
        s.append(Utils.pointAtEnd(TITLE_RESPONSORY_SHORT));
        switch (type) {

            case 0:
                s.append(text);
                break;

            case 1:

                if (respArray.length == 3) {
                    s.append(respArray[0]);
                    s.append(respArray[1]);
                    s.append(respArray[2]);
                    s.append(respArray[1]);
                }
                break;

            case 2:
                s.append(respArray[0]);
                s.append(respArray[1]);
                s.append(BRS);
                s.append(respArray[2]);
                s.append(BRS);
                s.append(respArray[1]);
                break;

            case 6001230:

                if (respArray.length == 4) {
                    s.append(respArray[0]);
                    s.append(respArray[0]);
                    s.append(respArray[1]);
                    s.append(respArray[2]);
                    s.append(respArray[3]);
                    s.append(respArray[0]);
                }
                break;

            case 6001020:

                if (respArray.length == 3) {
                    s.append(respArray[0]);
                    s.append(respArray[0]);
                    s.append(respArray[1]);
                    s.append(respArray[0]);
                    s.append(respArray[2]);
                    s.append(respArray[0]);
                }
                break;

            case 4:
                s.append(respArray[0]);
                s.append(respArray[0]);
                s.append(respArray[1]);
                s.append(respArray[0]);
                s.append(respArray[2]);
                s.append(respArray[0]);
                break;

            case 201:
                s.append(respArray[0]);
                s.append(respArray[1]);
                break;

            default:
                s.append(ERR_RESPONSORIO);
                break;
        }
        return s.toString();
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */

    public void normalizeByTime(int calendarTime) {
        this.text = Utils.replaceByTime(text, calendarTime);
    }
}