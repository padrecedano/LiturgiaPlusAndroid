package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.firebase.firestore.PropertyName;

import org.deiverbum.app.utils.Numerals;
import org.deiverbum.app.utils.Utils;

import java.util.Locale;


public class Liturgy {
    @Ignore
    public LHInvitatory lhInvitatory;
    @Ignore
    @PropertyName("typeId")
    public int typeID;
    @Ignore
    protected LiturgyTime liturgyTime;
    @Ignore
    protected Saint santo;
    @Ignore
    protected Today today;
    @Ignore
    protected boolean hasSaint;
    private Integer week;
    private Integer day;
    private Integer colorFK;
    private String name;
    @Ignore
    private BreviaryHour breviaryHour;
    private Integer liturgyID;
    private Integer typeFK;
    private Integer timeFK;

    public Liturgy() {
    }

    public Integer getLiturgyID() {
        return liturgyID;
    }

    public void setLiturgyID(Integer liturgyID) {
        this.liturgyID = liturgyID;
    }

    public Integer getTypeFK() {
        return typeFK;
    }

    public void setTypeFK(Integer typeFK) {
        this.typeFK = typeFK;
    }

    public Integer getTimeFK() {
        return timeFK;
    }

    public void setTimeFK(Integer timeFK) {
        this.timeFK = timeFK;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTiempoFK() {
        return timeFK;
    }

    public void setTiempoFK(Integer tiempoFK) {
        this.timeFK = tiempoFK;
    }

    public Integer getTipoFK() {
        return typeFK;
    }

    public void setTipoFK(Integer tipoFK) {
        this.typeFK = tipoFK;
    }

    public Integer getColorFK() {
        return colorFK;
    }

    public void setColorFK(Integer colorFK) {
        this.colorFK = colorFK;
    }

    public LiturgyTime getLiturgiaTiempo() {

        return liturgyTime;
    }

    public void setLiturgiaTiempo(LiturgyTime liturgiaTiempo) {
        this.liturgyTime = liturgiaTiempo;
    }

    public Integer getLiturgiaId() {
        return liturgyID;
    }

    public void setLiturgiaId(Integer liturgiaId) {
        this.liturgyID = liturgiaId;
    }

    @SuppressWarnings("unused")
    public Integer getColorId() {
        return colorFK;
    }

    public void setColorId(Integer colorFK) {
        this.colorFK = colorFK;
    }

    public String getNombre() {
        return name != null ? name : "***";
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }


    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public int getDia() {
        return this.day;
    }

    public void setDia(Integer dia) {
        this.day = dia;
    }

    public int getSemana() {
        return this.week;
    }

    public void setSemana(Integer semana) {
        this.week = semana;
    }

    @SuppressWarnings("unused")
    public LiturgyTime getLiturgyTime() {
        return liturgyTime;
    }

    public Saint getSanto() {
        return santo;
    }

    public void setSanto(Saint santo) {
        this.santo = santo;
    }

    public String getTitleForRead() {
        try {
            this.timeFK = liturgyTime.getTimeID();
            if (
                    this.timeFK >= 8 ||
                            this.timeFK == 1 && this.day > 16 ||
                            this.timeFK == 2 ||
                            this.timeFK == 3 && this.week == 0 ||
                            this.timeFK == 4 ||
                            this.timeFK == 5 ||
                            this.week >= 35 ||
                            this.timeFK == 6 && this.week == 6 && this.typeFK < 4 ||
                            this.timeFK == 6 && this.week == 1 && this.day != 1 ||
                            this.day == 0

            ) {
                return getTimeWithTitleForRead();
            } else {
                return getTimeWithWeekAndDay();
            }
        } catch (Exception e) {
            return Utils.createErrorMessage(e.getMessage());
        }
    }

    @NonNull
    public String toString() {
        return
                String.format(new Locale("es"), "Liturgy:%n%d\t%s", liturgyID, getName());
    }

    public String getTituloForRead() {
        return String.format(new Locale("es"), "%s%s", getName(), ".");
    }

    public String getTimeWithWeekAndDay() {
        Numerals ns = new Numerals(false, false, false);
        return String.format(new Locale("es"), "%s. %s de la %s semana.", liturgyTime.getLiturgyName(), Utils.getDayName(this.day), ns.toWords(week, true));
    }

    public String getTimeWithTitleForRead() {
        return String.format(new Locale("es"), "%s. %s", liturgyTime.getLiturgyName(), getTituloForRead());
    }

    public void setInvitatorio(LHInvitatory invitatorio) {
        this.lhInvitatory = invitatorio;
    }

    public BreviaryHour getBreviaryHour() {
        return this.breviaryHour;
    }

    public void setBreviaryHour(BreviaryHour bh) {
        this.breviaryHour = bh;
    }

    /**
     * Devuelve el saludo inicial de la liturgia
     *
     * @return El texto formateado para la vista
     * @since 2023.1
     */
    public SpannableStringBuilder getSaludoInicial() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append(Utils.toRed("V/. "));
        ssb.append("En el nombre del Padre, y del Hijo, y del Espíritu Santo.");
        ssb.append(LS);
        ssb.append(Utils.toRed("R/. "));
        ssb.append("Amén.");
        ssb.append(LS2);
        return ssb;
    }

    public void setHasSaint(boolean hasSaint) {
        this.hasSaint = hasSaint;
    }
}