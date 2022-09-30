package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.PrayerEntity;
import org.deiverbum.app.model.Prayer;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHPrayerAll {
    @Embedded
    public LHPrayerEntity lhPrayerEntity;

    @Relation(
            parentColumn = "prayerFK",
            entityColumn = "prayerID",
            entity = PrayerEntity.class
    )
    public PrayerEntity prayerEntity;

    public Prayer getDomainModel() {
        Prayer theModel=new Prayer();
        theModel.setTexto(prayerEntity.getTexto());
        return theModel;
    }
}
