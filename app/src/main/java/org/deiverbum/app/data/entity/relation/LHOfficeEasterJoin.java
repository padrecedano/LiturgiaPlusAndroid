package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHOfficeBiblicalEasterEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.model.LHOfficeBiblicalEaster;
import org.deiverbum.app.model.LHOfficeOfReadingEaster;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class LHOfficeEasterJoin {
    @Embedded
    public LHOfficeBiblicalJoinEntity biblical;
    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = LHOfficeBiblicalEasterEntity.class
    )
    public List<LHOfficeEasterAll> lstEaster;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupID",
            entity = LHPsalmodyJoinEntity.class
    )
    public LHPsalmodyAll entityPsalmody;

    public LHOfficeOfReadingEaster getDomainModel() {
        LHOfficeOfReadingEaster theModel = new LHOfficeOfReadingEaster();
        List<LHOfficeBiblicalEaster> theList = new ArrayList<>();
        for (LHOfficeEasterAll item : lstEaster) {
            theList.add(item.getDomainModel());
        }
        theModel.setBiblicalE(theList);
        theModel.lhPsalmody = entityPsalmody.getDomainModel();
        theModel.sort();
        return theModel;
    }
}
