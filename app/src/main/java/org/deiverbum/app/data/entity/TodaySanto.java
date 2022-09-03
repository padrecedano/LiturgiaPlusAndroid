package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Oracion;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodaySanto {

    @Embedded
    public Today today;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID" //liturgiaId
    )
    public SaintLifeWithAll santo;

    public SaintLife getDomainModel(){
        return santo.saintLife != null ? santo.getDomainModel() : null;
    }
}
