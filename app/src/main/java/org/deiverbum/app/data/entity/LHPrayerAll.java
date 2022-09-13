package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Oracion;

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

    public Oracion getDomainModel() {
        Oracion theModel=new Oracion();
        theModel.setTexto(prayerEntity.getTexto());
        return theModel;
    }
}
