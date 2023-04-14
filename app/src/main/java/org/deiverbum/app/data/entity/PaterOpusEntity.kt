package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.PATER_OPUS;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa a la tabla {@value org.deiverbum.app.utils.Constants#PATER_OPUS}.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = PATER_OPUS,
        foreignKeys =
                {
                        @ForeignKey(
                                entity = PaterEntity.class,
                                parentColumns = "paterID",
                                childColumns = "paterFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value = {"opusName", "paterFK", "volume"}, unique = true)}
)
public class PaterOpusEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "opusID")
    public Integer obraId = 0;

    @NonNull
    @ColumnInfo(name = "opusName")
    public String opusName = "";

    @NonNull
    @ColumnInfo(name = "liturgyName", defaultValue = "")
    public String liturgyName = "";

    //@NonNull
    @ColumnInfo(name = "subTitle", defaultValue = "NULL")
    public String subTitle = "";

    //@NonNull
    @ColumnInfo(name = "volume", defaultValue = "NULL")
    public Integer volumen = 0;

    @ColumnInfo(name = "opusDate", defaultValue = "NULL")
    public Integer opusDate = 0;

    //@NonNull
    @ColumnInfo(name = "editorial", defaultValue = "NULL")
    public String editorial = "";

    //@NonNull
    @ColumnInfo(name = "city", defaultValue = "NULL")
    public String ciudad;

    //@NonNull
    @ColumnInfo(name = "opusYear", defaultValue = "NULL")
    public Integer opusYear;

    @NonNull
    @ColumnInfo(name = "paterFK", defaultValue = "0", index = true)
    public Integer padreFK = 0;

    @NonNull
    @ColumnInfo(name = "typeFK", defaultValue = "0")
    public Integer typeFK = 0;

    @NonNull
    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    public Integer collectionFK = 0;

    @NonNull
    public String getOpusName() {
        return opusName;
    }
}