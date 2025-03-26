package org.deiverbum.app.core.model.breviarium

import org.deiverbum.app.core.model.biblia.BibleBook
import org.deiverbum.app.core.model.biblia.LectioBiblica

class LHOfficiumLectioPrior(
    override var book: BibleBook,
    override var pericopa: String,
    override var biblica: String,
    override var tema: String = "",
    override var theOrder: Int = 1,
    var responsorium: LHResponsorium
) : LectioBiblica(book, pericopa, biblica, tema, theOrder)