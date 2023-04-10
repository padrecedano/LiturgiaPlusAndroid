package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.BiblieBookEntity;
import org.deiverbum.app.model.MassReading;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class MassReadingWithBook {
    @Embedded
    public BibleReadingEntity lectura;

    @Relation(
            parentColumn = "bookFK",
            entityColumn = "bookID",
            entity = BiblieBookEntity.class
    )
    public BiblieBookEntity libro;

    public MassReading getDomainModel() {
        MassReading theModel = new MassReading();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }

    public MassReading getDomainModelMisa() {
        MassReading theModel = new MassReading();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }
}
