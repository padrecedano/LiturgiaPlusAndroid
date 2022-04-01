package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 20/3/22
 * @since
 */
public class TeamWithMembers {
    @Embedded
    public SalmodiaEntity salmodia;
    @Relation(
            parentColumn = "salmoFK",
            entityColumn = "salmoId",
            entity = SalmoEntity.class,
            associateBy = @Junction(MemberTeamMap.class)
    )
    public List<SalmoEntity> salmoId;


}
