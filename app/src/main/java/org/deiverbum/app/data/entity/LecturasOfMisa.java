package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LecturasOfMisa {
    @Embedded
    public BibliaLecturaEntity salmodia;
    @Relation(
            parentColumn = "pericopaId",
            entityColumn = "pericopaFK",
            entity = MisaLecturaEntity.class
    )
    public MisaLecturaEntity bibliaLectura;

/*
    @Relation(
            parentColumn = "libroId",
            entityColumn = "libroId",
            entity = LecturaWithLibro.class
    )
    public LecturaWithLibro bibliaLecturaa;
*/



    public Biblica getDomainModel(Integer timeId){
        Biblica theModel= new Biblica();
        //theModel.setLibro(String.valueOf(bibliaLectura.libroId));
        /*theModel.setCapitulo(String.valueOf(bibliaLectura.capitulo));
        theModel.setRef(String.valueOf(bibliaLectura.capitulo));
        theModel.setVersoInicial(String.valueOf(bibliaLectura.desde));
        //theModel.setVersoFinal(String.valueOf(bibliaLectura.hasta));
        theModel.setRef(bibliaLectura.getCita());*/
        //theModel.setTexto(bibliaLectura.texto);
        return theModel;
    }

}
