package org.deiverbum.app.core.model.data.breviarium

import androidx.room.Ignore

data class LHOratio(
    var groupID: Int = 0,
    var prayerFK: Int = 0,
    @Ignore var oratio: String = ""
)