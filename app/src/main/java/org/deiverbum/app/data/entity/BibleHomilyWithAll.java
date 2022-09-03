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
            entityColumn = "homilyFK",
            entity = BibleHomilyThemeEntity.class
    )

    public BibleHomilyThemeEntity themeEntity;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyID",
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


    public ComentarioBiblico getDomainModel() {
        ComentarioBiblico theModel=new ComentarioBiblico();
        if(themeEntity!=null) {
            theModel.setCita(themeEntity.getBiblical());
            theModel.setTema(themeEntity.getTheological());
            theModel.setRef(themeEntity.getReference());
        }
        theModel.setPadre(homilia.obraWithPadre.getPadre());
        theModel.setObra(homilia.obraWithPadre.obra.getObra());
        theModel.setTexto(homilia.homilia.getTexto());
        theModel.setFecha(String.valueOf(homilia.homilia.fecha));

        return theModel;
    }


}
