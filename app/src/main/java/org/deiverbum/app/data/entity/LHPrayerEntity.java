package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_PRAYER;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_PRAYER,
        indices = {@Index(value = {"groupID", "prayerFK"}, unique = true)},

        foreignKeys =
                {
                        @ForeignKey(
                                entity = PrayerEntity.class,
                                parentColumns = "prayerID",
                                childColumns = "prayerFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)
                }
)

public class LHPrayerEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId = 0;

    @NonNull
    @ColumnInfo(name = "prayerFK", index = true)
    public Integer oracionFK = 0;
}

