package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "todaytest")
public class TodayTest {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "hoy")
    public Integer hoy=0;

    @NonNull
    @ColumnInfo(name = "feriaFK", defaultValue= "0")
    public Integer feriaId=0;

    @ColumnInfo(name = "mLecturasFK", defaultValue= "0")
    public Integer mLecturasFK;

    @ColumnInfo(name = "previoId")
    public Integer previoId;

    @NonNull
    @ColumnInfo(name = "tiempoId")
    public Integer tiempoId=0;

    @ColumnInfo(name = "version", defaultValue= "0")
    public Integer version;

    @NonNull
    @ColumnInfo(name = "invitatorioFK", defaultValue= "0")
    public Integer invitatorioFK=0;

    @NonNull
    @ColumnInfo(name = "oHimnoFK", defaultValue= "0")
    public Integer oHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "oSalmodiaFK", defaultValue= "0")
    public Integer oSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "oficioFK", defaultValue= "0")
    public Integer oficioFK=0;

    @NonNull
    @ColumnInfo(name = "oVersoFK", defaultValue= "0")
    public Integer oVersoFK=0;

    @NonNull
    @ColumnInfo(name = "oBiblicaFK", defaultValue= "0")
    public Integer oBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "oPatristicaFK", defaultValue= "0")
    public Integer oPatristicaFK=0;

    @ColumnInfo(name = "santoFK", defaultValue= "0")
    public Integer santoFK;

    @NonNull
    @ColumnInfo(name = "oOracionFK", defaultValue= "0")
    public Integer oOracionFK=0;

    @NonNull
    @ColumnInfo(name = "oTeDeum", defaultValue= "0")
    public Integer oTeDeum=0;

    @NonNull
    @ColumnInfo(name = "lHimnoFK", defaultValue= "0")
    public Integer lHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "lSalmodiaFK", defaultValue= "0")
    public Integer lSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "lBiblicaFK", defaultValue= "0")
    public Integer lBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "lBenedictusFK", defaultValue= "0")
    public Integer lBenedictusFK=0;

    @NonNull
    @ColumnInfo(name = "lPrecesFK", defaultValue= "0")
    public Integer lPrecesFK=0;

    @NonNull
    @ColumnInfo(name = "lOracionFK", defaultValue= "0")
    public Integer lOracionFK=0;


    @NonNull
    @ColumnInfo(name = "tHimnoFK", defaultValue= "0")
    public Integer tHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "tSalmodiaFK", defaultValue= "0")
    public Integer tSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "tBiblicaFK", defaultValue= "0")
    public Integer tBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "tOracionFK", defaultValue= "0")
    public Integer tOracionFK=0;

    @NonNull
    @ColumnInfo(name = "sHimnoFK", defaultValue= "0")
    public Integer sHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "sSalmodiaFK", defaultValue= "0")
    public Integer sSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "sBiblicaFK", defaultValue= "0")
    public Integer sBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "sOracionFK", defaultValue= "0")
    public Integer sOracionFK=0;

    @NonNull
    @ColumnInfo(name = "nHimnoFK", defaultValue= "0")
    public Integer nHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "nSalmodiaFK", defaultValue= "0")
    public Integer nSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "nBiblicaFK", defaultValue= "0")
    public Integer nBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "nOracionFK", defaultValue= "0")
    public Integer nOracionFK=0;


    @NonNull
    @ColumnInfo(name = "vHimnoFK", defaultValue= "0")
    public Integer vHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "vSalmodiaFK", defaultValue= "0")
    public Integer vSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "vBiblicaFK", defaultValue= "0")
    public Integer vBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "vMagnificatFK", defaultValue= "0")
    public Integer vMagnificatFK=0;

    @NonNull
    @ColumnInfo(name = "vPrecesFK", defaultValue= "0")
    public Integer vPrecesFK=0;

    @NonNull
    @ColumnInfo(name = "vOracionFK", defaultValue= "0")
    public Integer vOracionFK=0;

    public void setVersion(Integer version) {
        this.version = version;
    }
    public Integer getVersion() {
        return version;
    }

    @NonNull
    public Integer getHoy() {
        return hoy;
    }
    public void setHoy(@NonNull Integer hoy) {
        this.hoy = hoy;
    }


    public Integer getPrevioId() {
        return previoId;
    }

    public void setPrevioId(Integer previoId) {
        this.previoId = previoId;
    }

    @NonNull
    public Integer getTiempoId() {
        return tiempoId;
    }


    @SuppressWarnings("unused")
    public boolean getTeDeum() {
        return oTeDeum == 1;
    }
}

