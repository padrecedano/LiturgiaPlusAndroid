package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.ERR_RESPONSORIO;
import static org.deiverbum.app.utils.Constants.RESP_A;
import static org.deiverbum.app.utils.Constants.RESP_R;
import static org.deiverbum.app.utils.Constants.RESP_V;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;

import org.deiverbum.app.utils.Utils;

public class Responsorio {
    private String texto;
    private int forma;
    private String ref;


    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getForma() {
        return this.forma;
    }

    public void setForma(int forma) {
        this.forma = forma;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public SpannableStringBuilder getHeader() {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        sb.append(Utils.toRed(String.format("%s%s", "Responsorio     ",getRef())));
        //String s= String.format("%" + getRef().length() + "s", getRef());
        //sb.append(Utils.toRed(s));
        sb.append(LS2);
        //sb.append(styledResultText);
        //sb.append(LS2);
        return sb;
    }


    /**
     * <p>Método que crea la cadena completa de un responsorio dado.</p>
     *
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     * @since 2022.01
     */

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String[] respArray = texto.split("\\|");
        StringBuilder s = new StringBuilder();

        sb.append(getHeader());
        switch (forma) {
            case 1:
                if (respArray.length == 3) {
                    s.append(RESP_R);
                    s.append(respArray[0]);
                    s.append(RESP_A);
                    s.append(respArray[1]);
                    s.append(BRS);
                    s.append(RESP_V);
                    s.append(respArray[2]);
                    s.append(BRS);
                    s.append(RESP_R);
                    s.append(Character.toUpperCase(respArray[1].charAt(0)));
                    s.append(respArray[1].substring(1));
                    s.append(BRS);
                    sb.append(Utils.fromHtml(s.toString()));
                }
                break;

            case 2:
                s.append(RESP_R);
                s.append(respArray[0]);
                s.append(RESP_A);
                s.append(respArray[1]);
                s.append(BRS);
                s.append(RESP_V);
                s.append(respArray[2]);
                s.append(BRS);
                s.append(RESP_R);
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
                    s.append(RESP_V);
                    s.append(respArray[0]);
                    s.append(BR);
                    s.append(RESP_R);
                    s.append(respArray[0]);
                    s.append(BRS);
                    s.append(RESP_V);
                    s.append(respArray[1]);
                    s.append(BR);
                    s.append(RESP_R);
                    s.append(respArray[2]);
                    s.append(BRS);
                    s.append(RESP_V);
                    s.append(respArray[3]);
                    s.append(BR);
                    s.append(RESP_R);
                    s.append(respArray[0]);
                    s.append(BRS);
                    sb.append(Utils.fromHtml(s.toString()));

                }
                break;


            case 6001020:
                if (respArray.length == 3) {
                    s.append(RESP_V);
                    s.append(respArray[0]);
                    s.append(BR);
                    s.append(RESP_R);
                    s.append(respArray[0]);
                    s.append(BRS);
                    s.append(RESP_V);
                    s.append(respArray[1]);
                    s.append(BR);
                    s.append(RESP_R);
                    s.append(respArray[0]);
                    s.append(BRS);
                    s.append(RESP_V);
                    s.append(respArray[2]);
                    s.append(BR);
                    s.append(RESP_R);
                    s.append(respArray[0]);
                    s.append(BRS);
                    sb.append(Utils.fromHtml(s.toString()));

                }
                break;


            case 4:
                s.append(RESP_V);
                s.append(respArray[0]);
                s.append(BR);
                s.append(RESP_R);
                s.append(respArray[0]);
                s.append(BRS);
                s.append(RESP_V);
                s.append(respArray[1]);
                s.append(BR);
                s.append(RESP_R);
                s.append(respArray[0]);
                s.append(BRS);
                s.append(RESP_V);
                s.append(respArray[2]);
                s.append(BR);
                s.append(RESP_R);
                s.append(respArray[0]);
                s.append(BRS);
                sb.append(Utils.fromHtml(s.toString()));
                break;

            case 201:
                s.append(RESP_V);
                s.append(respArray[0]);
                s.append(BR);
                s.append(RESP_R);
                s.append(respArray[1]);
                s.append(BRS);
                sb.append(Utils.fromHtml(s.toString()));

                break;


            default:
                sb.append(ERR_RESPONSORIO);
                sb.append(BR);
                sb.append("Tamaño del responsorio: ");
                sb.append(String.valueOf(respArray.length));
                sb.append(" Código forma: ");
                sb.append(String.valueOf(forma));
                sb.append(BR);
                break;
        }
        return sb;

    }

    /**
     * Método que crea la cadena completa de un responsorio dado destinado a la lectura de voz
     *
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    public String getAllForRead() {
        String[] respArray = texto.split("\\|");
        StringBuilder s = new StringBuilder();
        switch (forma) {
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

}
