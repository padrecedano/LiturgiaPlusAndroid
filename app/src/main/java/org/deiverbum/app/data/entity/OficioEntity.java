package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_oficio",
        foreignKeys =
        {
           @ForeignKey(
                        entity = SantoEntity.class,
                        parentColumns = "santoId",
                        childColumns = "santoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = SalmodiaEntity.class,
                    parentColumns = "salmodiaId",
                    childColumns = "salmodiaFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = HimnoEntity.class,
                    parentColumns = "himnoId",
                    childColumns = "himnoFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)/*,
            @ForeignKey(
                        entity = TemaEntity.class,
                        parentColumns = "temaId",
                        childColumns = "temaFK",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                        entity = EpigrafeEntity.class,
                        parentColumns = "epigrafeId",
                        childColumns = "epigrafeFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE)*/
        },
        indices={
                @Index(value={"santoFK","salmodiaFK","himnoFK"},unique =
                        true)}
)
public class OficioEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "oficioId")
    public Integer oficioId;

    @NonNull
    @ColumnInfo(name = "santoFK")
    public Integer santoFK;

    @NonNull
    @ColumnInfo(name = "salmodiaFK")
    public Integer salmodiaFK;

    @NonNull
    @ColumnInfo(name = "himnoFK")
    public Integer himnoFK;


}

