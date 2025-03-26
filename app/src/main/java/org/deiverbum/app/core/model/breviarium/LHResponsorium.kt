package org.deiverbum.app.core.model.breviarium

data class LHResponsorium(
    override var responsorium: String = "",
    override var typus: Int = 0,
    var source: String = ""
) : LHResponsoriumBrevis(responsorium, typus)