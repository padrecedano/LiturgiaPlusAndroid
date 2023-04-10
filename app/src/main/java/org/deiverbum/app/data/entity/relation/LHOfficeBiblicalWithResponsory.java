package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalEntity;
import org.deiverbum.app.data.entity.LHResponsoryEntity;
import org.deiverbum.app.model.LHOfficeBiblical;

/**
 * <p>Obtiene los valores para una lectura bíblica de
 * la Liturgy de las Horas,
 * desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
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
            entity = LHResponsoryEntity.class
    )
    public LHResponsoryEntity lhResponsorio;

    public LHOfficeBiblical getDomainModel(Integer tiempoId) {
        LHOfficeBiblical theModel = bibliaLectura.getDomainModelOficio();
        theModel.setTema(lhBiblica.theme);
        theModel.setOrden(lhBiblica.theOrder);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(tiempoId));
        return theModel;
    }
}
