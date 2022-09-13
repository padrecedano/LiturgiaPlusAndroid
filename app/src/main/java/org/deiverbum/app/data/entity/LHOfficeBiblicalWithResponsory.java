package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaOficio;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgia de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHOfficeBiblicalWithResponsory {
    @Embedded
    public LHOfficeBiblicalEntity lhBiblica;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public BibleReadingWithBook bibliaLectura;

    @Relation(
            parentColumn = "responsoryFK",
            entityColumn = "responsoryID",
            entity = LHResponsorioLargoEntity.class
    )
    public LHResponsorioLargoEntity lhResponsorio;

    public BiblicaOficio getDomainModel(Integer tiempoId) {
        BiblicaOficio theModel= bibliaLectura.getDomainModelOficio();
        theModel.setTema(lhBiblica.tema);
        theModel.setOrden(lhBiblica.orden);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(tiempoId));
        return theModel;
    }
}
