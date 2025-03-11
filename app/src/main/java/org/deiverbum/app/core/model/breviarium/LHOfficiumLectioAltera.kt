package org.deiverbum.app.core.model.data.breviarium

import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.deiverbum.app.core.model.data.traditio.PaterOpus

class LHOfficiumLectioAltera(
    @ColumnInfo(name = "source")
    var theSource: String? = null,
    @ColumnInfo(name = "theme")
    var tema: String? = null,
    @Ignore
    var homilia: String = "",
    @JvmField
    @Ignore
    var paterOpus: PaterOpus? = null,
    @Ignore
    var responsorium: LHResponsorium? = null,
    var theOrder: Int? = null
) {

    var groupFK: Int? = null
    var homilyFK: Int? = null
    var responsoryFK: Int? = null

}
