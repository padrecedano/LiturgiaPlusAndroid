package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.OLD_SEPARATOR;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;

@SuppressWarnings("SameReturnValue")
public class Saint {

    public Integer saintID;
    @Ignore
    public Integer liturgyFK;
    public Integer typeFK;
    public String theDay;
    public String theMonth;
    //@Ignore
    protected String theName;
    @Ignore
    private String vida;
    @SuppressWarnings("unused")
    //private String martirologio;
    @Ignore
    private boolean crg;

    public String getTheName() {
        return theName;
    }

    public void setTheName(String nombre) {
        this.theName = nombre;
    }

    public Spanned getVidaSmall() {
        if (!vida.equals("")) {
            return Utils.fromHtml("<p><small>" + vida + "</small></p>");
        } else {
            return Utils.fromHtml("");
        }
    }

    public Spanned getVida() {
        return Utils.fromHtml(vida);
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getMartirologio() {
        return "";//martirologio;
    }

    @SuppressWarnings("unused")
    public boolean getCrg() {
        return this.crg;
    }

    @SuppressWarnings("unused")
    public void setCrg(boolean crg) {
        this.crg = crg;
    }

    public void setDay(String dia) {
        this.theDay = dia;
    }

    public void setMonth(String mes) {
        this.theMonth = mes;
    }

    public SpannableStringBuilder getMartirologioSpan() {
        return Utils.toSmallSize("martirologio");
    }

    public SpannableStringBuilder getMartirologioTitleSpan() {
        return Utils.toSmallSize("(Martirologio Romano)");
    }

    public String getMartirologioTitleForRead() {
        return "Martirologio Romano.";
    }

    public SpannableStringBuilder getVidaSpan() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.fromHtml("<hr>"));
        sb.append(Utils.toH3Red("Vida"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(vida.replaceAll(OLD_SEPARATOR, "")));
        return sb;
    }

    public StringBuilder getVidaForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append("VIDA.");
        sb.append(Utils.fromHtml(vida.replaceAll(OLD_SEPARATOR, "")));
        return sb;
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            sb.append(Utils.toH3Red(getMonthName()));
            sb.append(LS2);
            sb.append(Utils.toH2Red(theName));
            sb.append(LS2);
            sb.append(getMartirologioSpan());
            sb.append(LS);
            sb.append(getMartirologioTitleSpan());
            sb.append(LS2);
            sb.append(getVidaSpan());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(getMonthName());
            sb.append(".");
            sb.append(theName);
            sb.append(".");
            sb.append(getMartirologio());
            sb.append(getMartirologioTitleForRead());
            sb.append(getVidaForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public String getMonthName() {
        HashMap<String, String> monthNames = new HashMap<>();
        monthNames.put("01", "Enero");
        monthNames.put("02", "Febrero");
        monthNames.put("03", "Marzo");
        monthNames.put("04", "Abril");
        monthNames.put("05", "Mayo");
        monthNames.put("06", "Junio");
        monthNames.put("07", "Julio");
        monthNames.put("08", "Agosto");
        monthNames.put("09", "Septiembre");
        monthNames.put("10", "Octubre");
        monthNames.put("11", "Noviembre");
        monthNames.put("12", "Diciembre");
        return String.format("%s de %s", theDay, monthNames.get(theMonth));
    }

    @SuppressWarnings("unused")
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
        Integer theMonth = Integer.valueOf(mes);
        return String.format("%s de %s", theDay, monthNames.get(theMonth));
    }
}