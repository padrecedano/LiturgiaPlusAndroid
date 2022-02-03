package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.OLD_SEPARATOR;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;

public class Santo {
    private String nombre;
    private String vida;
    private String martirologio;
    private boolean crg;
    private String dia;
    private String mes;

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public Spanned getVidaSmall() {
        if (!vida.equals("")) {
            return Utils.fromHtml("<p><small>" + vida + "</small></p>");
        } else {

            return Utils.fromHtml("");
        }
    }

    public String getVida() {
        return vida;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getMartirologio() {
        return martirologio;
    }

    public void setCrg(boolean crg){
        this.crg=crg;
    }

    public boolean getCrg(){
        return this.crg;
    }

    public void setDia(String dia){
        this.dia=dia;
    }

    public void setMes(String mes){
        this.mes=mes;
    }

    public SpannableStringBuilder getMartirologioSpan() {
        return Utils.toSmallSize(martirologio);
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
        sb.append(Utils.toH3Red(getMonthName()));
        sb.append(LS2);
        sb.append(Utils.toH2Red(nombre));
        sb.append(LS2);
        sb.append(getMartirologioSpan());
        sb.append(LS);
        sb.append(getMartirologioTitleSpan());
        sb.append(LS2);
        sb.append(getVidaSpan());
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMonthName());
        sb.append(".");
        sb.append(nombre);
        sb.append(".");
        sb.append(getMartirologio());
        sb.append(getMartirologioTitleForRead());
        sb.append(getVidaForRead());
        return sb;
    }

    public String getMonthName(){
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
        return String.format("%s de %s",dia,monthNames.get(mes));

    }


}
