package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.PRE_ANT;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Invitatorio extends Salmo {
    public String texto;
    private int id = 1;
    private final ArrayList<String> files = new ArrayList<>(
            Arrays.asList("res/raw/invitatorio_1.txt",
                    "res/raw/invitatorio_2.txt",
                    "res/raw/invitatorio_3.txt",
                    "res/raw/invitatorio_4.txt"));

    public Invitatorio() {
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
        try {

            if (!isVariable){id=1;}
            String filePath = files.get(id);

            InputStream in = Objects.requireNonNull(this.getClass().getClassLoader()).getResourceAsStream(filePath);
            if (in != null) {
                byte[] b = new byte[in.available()];
                texto = new String(b);
            } else {
                texto = "No se encontró el texto del invitatorio";
            }
        } catch (IOException e) {
            e.printStackTrace();
            texto = e.getMessage();
        }

        return Utils.fromHtml(texto);
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitle() {
        return "Invitatorio";
    }
    public String getTitleForRead() {
        return "Invitatorio.";
    }

    public Spanned getHeaderForRead() {
        return Utils.fromHtml("<p>Invitatorio.</p>");
    }

    public SpannableStringBuilder getAll(boolean hasInvitatorio) {
        SpannableStringBuilder sb=new SpannableStringBuilder();
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
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getTitleForRead());
        sb.append(getAntifona());
        sb.append(getTextoSpan(hasInvitatorio));
        sb.append(getFinSalmo());
        sb.append(getAntifona());
        return sb;
    }
}