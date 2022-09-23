package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.CONTENT_NOTFOUND;

import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import org.deiverbum.app.utils.Numerals;
import org.deiverbum.app.utils.Utils;


public class Liturgy {
    @Ignore
    protected LiturgyTime liturgyTime;
    private Integer week;
    private Integer day;
    private Integer colorFK;

    private String name;

    @Ignore
    public LHInvitatory lhInvitatory;

    @Ignore
    public int typeID;

    @Ignore
    public LHPsalmody lhPsalmody;
    @Ignore
    private BreviaryHour breviaryHour;


    private Integer liturgyID;
    private Integer typeFK;
    private Integer timeFK;

    //@Embedded

    @Ignore
    protected Saint santo;

    //@Embedded
    @Ignore
    protected Today hoy;
    //@Embedded
    @Ignore

    protected MetaLiturgia metaLiturgia;
    @Ignore

    public boolean hasSaint = false;

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

    public Integer getTipoFK() {
        return typeFK;
    }

    public void setTipoFK(Integer tipoFK) {
        this.typeFK = tipoFK;
    }

    public void setTiempoFK(Integer tiempoFK) {
        this.timeFK = tiempoFK;
    }

    public Integer getColorFK() {
        return colorFK;
    }

    public void setColorFK(Integer colorFK) {
        this.colorFK = colorFK;
    }

    //@Embedded


    public LiturgyTime getLiturgiaTiempo() {
        return liturgyTime;
    }

    public void setLiturgiaTiempo(LiturgyTime liturgiaTiempo) {
        this.liturgyTime = liturgiaTiempo;
    }


    public Liturgy() {
    }


    public Integer getLiturgiaId() {
        return liturgyID;
    }

    public void setLiturgiaId(Integer liturgiaId) {
        this.liturgyID = liturgiaId;
    }

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

    public MetaLiturgia getMetaLiturgia() {
        return this.metaLiturgia;
    }

    public Today getHoy() {
        return hoy;
    }

    public void setHoy(Today hoy) {
        this.hoy = hoy;
    }

    @SuppressWarnings("unused")
    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }


    public void setDia(Integer dia) {
        this.day = dia;
    }

    public void setSemana(Integer semana) {
        this.week = semana;
    }

    public int getDia() {
        return this.day;
    }

    public int getSemana() {
        return this.week;
    }

    public void setTiempoFKK(LiturgyTime tiempoFK) {
        //this.tiempoFK=tiempoFK;
    }

    public LiturgyTime getLiturgyTime() {
        return liturgyTime;
    }


    public Saint getSanto() {
        return santo;
    }

    public void setSanto(Saint santo) {
        this.santo = santo;
    }

    public SpannableStringBuilder getAllForRead() {
        //this.idDia=liturgiaFeria.getDia();
        //this.idSemana=liturgiaFeria.getSemana();
        //this.idTiempo=liturgiaFeria.getTiempoId();
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        this.timeFK = liturgyTime.getTiempoId();
        if (
                this.timeFK >= 8
                        || (this.timeFK == 1 && this.day > 16)
                        || this.timeFK == 2
                        || (this.timeFK == 3 && this.week == 0)

                        || this.timeFK == 4
                        || this.timeFK == 5
                        || this.week >= 35
                        || (this.timeFK == 6 && this.week == 6 && this.typeFK < 4)
                        || (this.timeFK == 6 && this.week == 1)
                        || (this.day == 0)
                        || (this.timeFK >= 1 && this.timeFK <= 7 && this.day == 1)

        ) {
            ssb.append(getTimeWithTitleForRead());
        } else {
            ssb.append(getTimeWithWeekAndDay());
        }
        return ssb;
    }


    @NonNull
    public String toString() {
        return
                String.format("Liturgy:%n%d\t%s", liturgyID, getName());
    }

    public String getTituloForRead() {
        return String.format("%s%s", getName(), ".");
    }


    public String getTimeWithWeekAndDay() {
        Numerals ns = new Numerals(false, false, false);
        return String.format("%s. %s de la %s semana.", liturgyTime.getLiturgyName(), Utils.getDayName(this.day), ns.toWords(week, true));
    }

    public String getTimeWithTitleForRead() {
        return String.format("%s. %s", liturgyTime.getLiturgyName(), getTituloForRead());
    }

    public void setInvitatorio(LHInvitatory invitatorio) {
        this.lhInvitatory = invitatorio;
    }

    public SpannableStringBuilder getForView() {
        try {

            if (typeID == 0) {
                return breviaryHour.getMixto().getForView(liturgyTime, false);
            }
            if (typeID == 1) {
                return breviaryHour.getOficio().getForView(liturgyTime, false);
            }
            if (typeID == 2) {
                return breviaryHour.getLaudes().getForView(liturgyTime, false);
            }
            if (typeID == 3 || typeID == 4 || typeID == 5) {
                return breviaryHour.getIntermedia().getForView();
            }
            if (typeID == 6) {
                return breviaryHour.getVisperas().getForView();
            }
            return new SpannableStringBuilder(CONTENT_NOTFOUND);
        } catch (Exception e) {
            return new SpannableStringBuilder(e.getMessage());
        }
    }

    public void setBreviaryHour(BreviaryHour bh) {
        this.breviaryHour = bh;
    }

    public StringBuilder getForRead() {
        try {
            if (typeID == 0) {
                return breviaryHour.getMixto().getForRead(false);
            }
            if (typeID == 1) {
                return breviaryHour.getOficio().getForRead(false);
            }
            if (typeID == 2) {
                return breviaryHour.getLaudes().getForRead(false);
            }
            if (typeID == 3 || typeID == 4 || typeID == 5) {
                return breviaryHour.getIntermedia().getForRead();
            }
            if (typeID == 6) {
                return breviaryHour.getVisperas().getForRead();
            }
            return new StringBuilder(CONTENT_NOTFOUND);

        } catch (Exception e) {
            return new StringBuilder(e.getMessage());
        }
    }
}

