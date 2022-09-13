package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.BiblicaOficio;
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
    public BiblicaMisa getDomainModel() {
        BiblicaMisa theModel=new BiblicaMisa();
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

    public BiblicaMisa getDomainModelMisa() {
        BiblicaMisa theModel=new BiblicaMisa();
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


    public BiblicaOficio getDomainModelOficio() {
        BiblicaOficio theModel=new BiblicaOficio();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setCita(lectura.getCita());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }
}
