package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.OLD_SEPARATOR;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import com.google.firebase.firestore.PropertyName;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;

@SuppressWarnings("SameReturnValue")
public class SaintLife {
    private Integer saintFK;
    @PropertyName("vida")
    public String longLife;
    @PropertyName("martirologio")
    private String martyrology;
    private String source="";
    @Ignore
    @PropertyName("nombre")
    public String name;
    @Ignore
    @PropertyName("dia")
    private String dia;
    @Ignore
    private String mes;
    private String shortLife;

    public Integer getSaintFK() {
        return saintFK;
    }

    public void setSaintFK(Integer saintFK) {
        this.saintFK = saintFK;
    }

    @PropertyName("vida")
    public String getLongLife() {
        return longLife;
    }

    @PropertyName("vida")
    public void setLongLife(String longLife) {
        this.longLife = longLife;
    }

    //@PropertyName("vida")
    public String getShortLife() {
        return shortLife;
    }

    //@PropertyName("vida")
    public void setShortLife(String shortLife) {
        this.shortLife = shortLife;
    }

    @PropertyName("martirologio")
    public String getMartyrology() {
        return martyrology;
    }

    @PropertyName("martirologio")
    public void setMartyrology(String martyrology) {
        this.martyrology = martyrology;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public SpannableStringBuilder getMartirologioSpan() {
        return Utils.toSmallSize(martyrology);
    }
    public SpannableStringBuilder getVidaSpan() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.fromHtml("<hr>"));
        sb.append(Utils.toH3Red("Vida"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(longLife.replaceAll(OLD_SEPARATOR, "")));
        return sb;
    }

    public StringBuilder getLifeForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append("VIDA.");
        sb.append(Utils.fromHtml(longLife.replaceAll(OLD_SEPARATOR, "")));
        return sb;
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            sb.append(Utils.toH3Red(getMonthName(mes)));
            sb.append(LS2);
            sb.append(Utils.toH2Red(name));
            sb.append(LS2);
            sb.append(getMartirologioSpan());
            sb.append(LS);
            sb.append(getMartirologioTitleSpan());
            sb.append(LS2);
            sb.append(getVidaSpan());
            sb.append(LS2);
            sb.append(Utils.fromHtmlSmall((getSource())));
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public SpannableStringBuilder getMartirologioTitleSpan() {
        return Utils.toSmallSize("(Martirologio Romano)");
    }
    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(getMonthName(mes));
            sb.append(".");
            sb.append(name);
            sb.append(".");
            sb.append(getMartyrology());
            sb.append(getMartirologioTitleForRead());
            sb.append(getLifeForRead());
            sb.append(getSourceForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    private String getSourceForRead() {
        return source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getMartirologioTitleForRead() {
        return "Martirologio Romano.";
    }

    public String getMonthName(String mes) {
        HashMap<Integer, String> monthNames = new HashMap<>();
        monthNames.put(1, "Enero");
        monthNames.put(2, "Febrero");
        monthNames.put(3, "Marzo");
        monthNames.put(4, "Abril");
        monthNames.put(5, "Mayo");
        monthNames.put(6, "Junio");
        monthNames.put(7, "Julio");
        monthNames.put(8, "Agosto");
        monthNames.put(9, "Septiembre");
        monthNames.put(10, "Octubre");
        monthNames.put(11, "Noviembre");
        monthNames.put(12, "Diciembre");
        Integer theMonth=Integer.valueOf(mes);
        return String.format("%s de %s", dia, monthNames.get(theMonth));
    }
}