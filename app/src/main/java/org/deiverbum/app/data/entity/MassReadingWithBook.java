package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.LHOfficeBiblical;
import org.deiverbum.app.model.Evangelio;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
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

/*
    @Relation(
            parentColumn = "readingID",
            entityColumn = "readingFK",
            entity = MassReadingEntity.class
    )
    public MassReadingEntity massReading;
*/
    public MassReading getDomainModel() {
        MassReading theModel=new MassReading();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        //theModel.setTema(massReading.getTema());
        //theModel.setOrden(massReading.getOrden());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }

    public MassReading getDomainModelMisa() {
        MassReading theModel=new MassReading();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        //theModel.setTema(lectura.getTema());
        theModel.setTexto(lectura.getTexto());

        return theModel;
    }

    public Evangelio getDomainModelMisaEvangelio() {
        Evangelio theModel=new Evangelio();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        //theModel.setTema(lectura.getTema());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }


    public LHOfficeBiblical getDomainModelOficio() {
        LHOfficeBiblical theModel=new LHOfficeBiblical();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }
}
