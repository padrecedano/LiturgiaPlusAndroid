package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Comentario;
import org.deiverbum.app.model.ComentarioBiblico;
import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.Patristica;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class BibleHomilyWithAll {
    @Embedded
    public BibleHomilyJoinEntity bibliaLecturaEntity;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homiliaId",
            entity = HomiliaEntity.class
    )

    public HomiliaWithAll homilia;
/*
    @Relation(
            parentColumn = "obraFK",
            entityColumn = "obraId",
            entity = ObraEntity.class
    )
    public ObraWithPadre obraWithPadre;
*/


    public Homilia getDomainModelsk() {
        Homilia theModel=new Homilia();
        //theModel.setTema(lhPatristica.getTema());
        //theModel.setHomilia(homilia.getTexto());
        //theModel.setPadre(obraWithPadre.padre.getPadre());
        //theModel.setObra(obraWithPadre.obra.getObra());
        //theModel.setTema(homilia.g);
        //theModel.setFuente(String.valueOf(homilia.getNumero()));
        return theModel;
    }
    public ComentarioBiblico getDomainModel() {
        ComentarioBiblico theModel=new ComentarioBiblico();
        /*if(themeEntity!=null) {
            theModel.setCita(themeEntity.getBiblical());
            theModel.setTema(themeEntity.getTheological());
            theModel.setRef(themeEntity.getReference());
        }*/
        theModel.setPadre(homilia.obraWithPadre.getPadre());
        theModel.setObra(homilia.obraWithPadre.obra.getObra());
        theModel.setTexto(homilia.homilia.getTexto());
        theModel.setFecha(String.valueOf(homilia.homilia.fecha));

        return theModel;
    }

    public Comentario getDomainModels() {
        Comentario theModel=new Comentario();
        /*if(themeEntity!=null) {
            theModel.setCita(themeEntity.getBiblical());
            theModel.setTema(themeEntity.getTheological());
            theModel.setRef(themeEntity.getReference());
        }*/
        theModel.setPadre(homilia.obraWithPadre.getPadre());
        theModel.setObra(homilia.obraWithPadre.obra.getObra());
        theModel.setTexto(homilia.homilia.getTexto());
        //theModel.setFecha(String.valueOf(homilia.homilia.fecha));

        return theModel;
    }

}
