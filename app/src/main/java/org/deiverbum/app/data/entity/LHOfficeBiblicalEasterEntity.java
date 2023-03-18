package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_OFFICE_BIBLICAL_EASTER;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.deiverbum.app.model.LHOfficeBiblicalEaster;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_OFFICE_BIBLICAL_EASTER,
        primaryKeys = {"groupFK","readingFK","psalmodyFK","prayerFK"},

        foreignKeys =
        {
           @ForeignKey(
                   entity = LHOfficeBiblicalJoinEntity.class,
                   parentColumns = "groupID",
                   childColumns = "groupFK",
                   onDelete = ForeignKey.CASCADE,
                   onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = BibleReadingEntity.class,
                    parentColumns = "readingID",
                    childColumns = "readingFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = LHPsalmodyJoinEntity.class,
                    parentColumns = "groupID",
                    childColumns = "psalmodyFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = PrayerEntity.class,
                    parentColumns = "prayerID",
                    childColumns = "prayerFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        }
)

public class LHOfficeBiblicalEasterEntity {

    @NonNull
    @ColumnInfo(name = "groupFK", index = true)
    public Integer groupFK =0;

    @NonNull
    @ColumnInfo(name = "readingFK", index = true)
    public Integer readingFK =0;

    @NonNull
    @ColumnInfo(name = "psalmodyFK", index = true)
    public Integer psalmodyFK =0;

    @NonNull
    @ColumnInfo(name = "prayerFK", index = true)
    public Integer prayerFK =0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String theme ="";

    @NonNull
    @ColumnInfo(name = "theOrder", defaultValue= "1")
    public Integer theOrder =1;

    public LHOfficeBiblicalEaster getDomainModel() {
        LHOfficeBiblicalEaster dm=new LHOfficeBiblicalEaster();
        dm.setTheme(theme);
        return dm;
    }
}

