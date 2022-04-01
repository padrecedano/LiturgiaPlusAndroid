package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 20/3/22
 * @since
 */


@Entity(primaryKeys = {"salmoFK", "salmoId"})

public class SalmoSalmodiaCrossRef {
    @NonNull
    public Integer salmoFK;
    @NonNull
    public Integer salmoId;

}
