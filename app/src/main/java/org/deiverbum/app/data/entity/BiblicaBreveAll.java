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
public class BiblicaBreveAll {
    @Embedded
    public LHBiblicaBreveJoinEntity lhBiblica;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = LHBiblicaBreveEntity.class
    )
    public LHBiblicaBreveEntity biblica;

    @Relation(
            parentColumn = "responsoryFK",
            entityColumn = "responsoryID",
            entity = LHResponsorioBreveEntity.class
    )
    public LHResponsorioBreveEntity responsorio;

    public BiblicaBreve getDomainModel(Integer tiempoId) {
        BiblicaBreve dm=new BiblicaBreve();
        dm.setTexto(biblica.texto);
        dm.setCita(biblica.cita);
        dm.setResponsorio(responsorio.getDomainModel(tiempoId));
        return dm;
    }

}
