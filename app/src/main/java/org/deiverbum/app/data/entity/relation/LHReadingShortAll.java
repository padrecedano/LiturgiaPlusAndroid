package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHReadingShortEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LHResponsoryShortEntity;
import org.deiverbum.app.model.BiblicalShort;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgy de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHReadingShortAll {
    @Embedded
    public LHReadingShortJoinEntity lhBiblica;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = LHReadingShortEntity.class
    )
    public LHReadingShortEntity biblica;

    @Relation(
            parentColumn = "responsoryFK",
            entityColumn = "responsoryID",
            entity = LHResponsoryShortEntity.class
    )
    public LHResponsoryShortEntity responsorio;

    public BiblicalShort getDomainModel(Integer tiempoId) {
        BiblicalShort dm=new BiblicalShort();
        dm.setReadingID(biblica.lecturaId);
        dm.setTexto(biblica.texto);
        dm.setCita(biblica.cita);
        dm.setResponsorio(responsorio.getDomainModel(tiempoId));
        return dm;
    }

}
