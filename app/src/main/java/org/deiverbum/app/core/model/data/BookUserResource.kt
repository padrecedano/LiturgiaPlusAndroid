package org.deiverbum.app.core.model.data

import org.deiverbum.app.core.model.data.book.Book
import org.deiverbum.app.core.model.data.book.BookTest

/**
 * Un objeto [Book] con información adicional en [UserDataDynamic]
 * la cual sirve por ejemplo para determinar el color de la rúbrica
 * según el tema que se tenga configurado en ese momento.
 */
data class BookUserResource internal constructor(
    val data: BookTest,
    val userData: UserDataDynamic,
)

