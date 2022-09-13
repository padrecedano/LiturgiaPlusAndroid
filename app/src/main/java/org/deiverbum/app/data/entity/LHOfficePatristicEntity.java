package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_office_patristic",
        primaryKeys = {"groupFK","homilyFK","responsoryFK"},
        /*indices = {@Index(value = {"grupoFK","pericopaFK","responsorioFK"},
                unique = true)},*/

        foreignKeys =
        {
          @ForeignKey(
                    entity = LHOfficePatristicJoinEntity.class,
                    parentColumns = "groupID",
                    childColumns = "groupFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = HomilyEntity.class,
                    parentColumns = "homilyID",
                    childColumns = "homilyFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = LHResponsoryEntity.class,
                    parentColumns = "responsoryID",
                    childColumns = "responsoryFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        }
)
public class LHOfficePatristicEntity {
    @NonNull
    @ColumnInfo(name = "groupFK")
    public Integer grupoFK=0;

    @NonNull
    @ColumnInfo(name = "homilyFK")
    public Integer homiliaFK=0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String tema="";

    @NonNull
    @ColumnInfo(name = "source", defaultValue = "")
    public String fuente="";

    @NonNull
    @ColumnInfo(name = "order", defaultValue= "1")
    public Integer orden=0;

    @NonNull
    @ColumnInfo(name = "responsoryFK")
    public Integer responsorioFK=0;

}

