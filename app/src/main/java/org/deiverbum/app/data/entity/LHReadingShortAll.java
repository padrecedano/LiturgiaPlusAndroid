package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgia de las Horas,
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

    public BiblicaBreve getDomainModel(Integer tiempoId) {
        BiblicaBreve dm=new BiblicaBreve();
        dm.setTexto(biblica.texto);
        dm.setCita(biblica.cita);
        dm.setResponsorio(responsorio.getDomainModel(tiempoId));
        return dm;
    }

}
