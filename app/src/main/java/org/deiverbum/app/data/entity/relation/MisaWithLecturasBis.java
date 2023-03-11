package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyGroupEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.MassReadingWithAll;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.MassReading;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class MisaWithLecturasBis {
    @Embedded
    public LiturgyGroupEntity misaLectura;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "liturgyFK",
            entity = MassReadingEntity.class
    )
    public List<MassReadingWithAll> lectura;

    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID",
            entity = LiturgyEntity.class
    )
    public LiturgyWithTime liturgia;

    @SuppressWarnings("SameReturnValue")
    public MassReading getDomainModel() {
        return null;
    }

    public Liturgy getLiturgia() {
        return liturgia.getDomainModel();
    }

}
