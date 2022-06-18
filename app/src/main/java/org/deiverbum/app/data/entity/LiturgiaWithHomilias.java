package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Homilia;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LiturgiaWithHomilias {
    @Embedded
    public LiturgiaHomiliaJoinEntity joinEntity;

    @Relation(
            parentColumn = "homiliaFK",
            entityColumn = "homiliaId",
            entity = HomiliaEntity.class
    )
    public HomiliaWithAll homilia;

    public Homilia getDomainModel() {
        Homilia dm=homilia.getDomainModel();
        dm.setTema(joinEntity.getTema());
        return dm;
    }

}
