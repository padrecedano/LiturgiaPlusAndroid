package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalEasterEntity;
import org.deiverbum.app.data.entity.PrayerEntity;
import org.deiverbum.app.model.LHOfficeBiblicalEaster;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class LHOfficeEasterAll {
    @Embedded
    public LHOfficeBiblicalEasterEntity biblical;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public BibleReadingWithBook reading;

    @Relation(
            parentColumn = "prayerFK",
            entityColumn = "prayerID",
            entity = PrayerEntity.class
    )
    public PrayerEntity prayer;

    public LHOfficeBiblicalEaster getDomainModel() {
        LHOfficeBiblicalEaster dm=new LHOfficeBiblicalEaster();
        dm.setOrden(biblical.theOrder);
        dm.setTheme(biblical.theme);
        dm.setLibro(reading.libro.getDomainModel());
        dm.setCapitulo(String.valueOf(reading.lectura.getCapitulo()));
        dm.setVersoInicial(String.valueOf(reading.lectura.getDesde()));
        dm.setVersoFinal(String.valueOf(reading.lectura.getHasta()));
        dm.setCita(reading.lectura.getCita());
        dm.setTexto(reading.lectura.getTexto());
        dm.setPrayer(prayer.getDomainModel());
        return dm;
    }
}
