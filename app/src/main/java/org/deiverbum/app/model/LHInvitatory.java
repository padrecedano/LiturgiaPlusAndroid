package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.PRE_ANT;
import static org.deiverbum.app.utils.Constants.TITLE_INVITATORY;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/*
    IDs de los salmos invitatorios:
        LHPsalm 94: ID 315, insertado recientemente
        LHPsalm 99: ID 86
        LHPsalm 66: ID 62
        LHPsalm 23: ID 101
 */
public class LHInvitatory extends LHPsalm {
    public String texto;
    private int id = 1;

    public LHInvitatory() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Determina si el usuario tiene configurado un invitatorio variable
     * y devuelve el texto que corresponda.
     *
     * @param isVariable booleano true o false desde preferencias
     * @return El texto obtenido del archivo correspondiente o el texto por defecto
     * @since 2022.1
     */
    public Spanned getTextoSpan(boolean isVariable) {
        return Utils.fromHtml(getSalmo());
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitle() {
        return TITLE_INVITATORY;
    }

    public String getTitleForRead() {
        return Utils.pointAtEnd(TITLE_INVITATORY);
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_INVITATORY);
    }

    public SpannableStringBuilder getAll(boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.formatSubTitle(getTitle().toLowerCase()));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(PRE_ANT));
        sb.append(getAntifona());
        sb.append(Utils.LS2);
        sb.append(getTextoSpan(hasInvitatorio));
        sb.append(Utils.LS);
        sb.append(getFinSalmo());
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(PRE_ANT));
        sb.append(getAntifona());
        return sb;
    }

    public SpannableStringBuilder getAllForRead(boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getTitleForRead());
        sb.append(getAntifona());
        sb.append(getTextoSpan(hasInvitatorio));
        sb.append(getFinSalmo());
        sb.append(getAntifona());
        return sb;
    }
}
