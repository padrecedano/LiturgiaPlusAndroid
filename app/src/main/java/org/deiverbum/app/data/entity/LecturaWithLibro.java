package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.Evangelio;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LecturaWithLibro {
    @Embedded
    public BibliaLecturaEntity lectura;


    @Relation(
            parentColumn = "libroFK",
            entityColumn = "libroId",
            entity = BibliaLibroEntity.class
    )
    public BibliaLibroEntity libro;

    public Biblica getDomainModel() {
        Biblica theModel=new Biblica();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setRef(lectura.getCita());
        //theModel.setTema(lectura.getTema());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }

    public BiblicaMisa getDomainModelMisa() {
        BiblicaMisa theModel=new BiblicaMisa();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setRef(lectura.getCita());
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
        theModel.setRef(lectura.getCita());
        //theModel.setTema(lectura.getTema());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }

    public BiblicaBreve getDomainModelBreve(){
        BiblicaBreve theModel=new BiblicaBreve();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setRef(lectura.getCita());
        //theModel.setVersoInicial(String.valueOf(getDesde()));
        //theModel.setVersoFinal(String.valueOf(getHasta()));
        //theModel.setTema(biblicaOficioWithResponsorio.tema);
        theModel.setTexto(lectura.getTexto());
        //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
        return theModel;
    }

    public BiblicaOficio getDomainModelOficio() {
        BiblicaOficio theModel=new BiblicaOficio();
        theModel.setLibro(libro.getDomainModel());
        theModel.setCapitulo(String.valueOf(lectura.getCapitulo()));
        theModel.setVersoInicial(String.valueOf(lectura.getDesde()));
        theModel.setVersoFinal(String.valueOf(lectura.getHasta()));
        theModel.setRef(lectura.getCita());
        theModel.setTexto(lectura.getTexto());
        return theModel;
    }
}
