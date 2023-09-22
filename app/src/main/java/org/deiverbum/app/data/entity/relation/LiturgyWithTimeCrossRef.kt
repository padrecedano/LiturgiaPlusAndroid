package org.deiverbum.app.data.entity.relation

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
//@Entity(primaryKeys = ["timeFK", "timeID"])
data class LiturgyWithTimeCrossRef(
    val timeFK: Long,
    val timeID: Long
)