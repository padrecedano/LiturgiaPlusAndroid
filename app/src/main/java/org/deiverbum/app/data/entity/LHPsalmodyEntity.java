package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_PSALMODY;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = LH_PSALMODY,
        primaryKeys = {"groupFK", "readingFK", "antiphonFK"},
        foreignKeys =
        {
        @ForeignKey(
                   entity = LHPsalmodyJoinEntity.class,
                   parentColumns = "groupID",
                   childColumns = "groupFK",
                   onDelete = ForeignKey.CASCADE,
                   onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = PsalmEntity.class,
                    parentColumns = "psalmID",
                    childColumns = "readingFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = LHAntiphonEntity.class,
                    parentColumns = "antiphonID",
                    childColumns = "antiphonFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                        entity = LHThemeEntity.class,
                        parentColumns = "themeID",
                        childColumns = "themeFK",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                        entity = EpigraphEntity.class,
                        parentColumns = "epigraphID",
                        childColumns = "epigraphFK",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHPsalmodyEntity {

    @NonNull
    @ColumnInfo(name = "groupFK")
    public Integer grupoFK=0;

    @NonNull
    @ColumnInfo(name = "readingFK")
    public Integer salmoFK=0;

    @NonNull
    @ColumnInfo(name = "order")
    public Integer orden=0;

    @NonNull
    @ColumnInfo(name = "antiphonFK", defaultValue = "0")
    public Integer antifonaId=0;

    @ColumnInfo(name = "themeFK", defaultValue = "NULL")
    public Integer temaFK=0;

    @ColumnInfo(name = "epigraphFK", defaultValue = "NULL")
    public Integer epigrafeFK=0;

    @ColumnInfo(name = "part", defaultValue = "NULL")
    public Integer parte=0;

    public void setGrupoFK(@NonNull Integer grupoFK) {
        this.grupoFK = grupoFK;
    }
    @NonNull
    public Integer getGrupoFK() {
        return grupoFK;
    }

    @NonNull
    public Integer getSalmoFK() {
        return salmoFK;
    }
    public void setSalmoFK(@NonNull Integer salmoFK) {
        this.salmoFK = salmoFK;
    }

    @NonNull
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(@NonNull Integer orden) {
        this.orden = orden;
    }

    @SuppressWarnings("unused")
    public void setAntifonaId(@NonNull Integer antifonaId) {
        this.antifonaId = antifonaId;
    }

    @SuppressWarnings("unused")
    @NonNull
    public Integer getAntifonaId() {
        return antifonaId;
    }

    public String getParte() {
        return  parte==null || parte==0 ? "":String.valueOf(parte);
    }


}

