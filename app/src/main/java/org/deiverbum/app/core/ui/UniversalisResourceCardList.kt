package org.deiverbum.app.core.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import org.deiverbum.app.core.model.data.UserUniversalisResource

/**
 * Función de extensión para mostrar una [List] de [UniversalisResourceCardExpanded] respaldada por una lista de
 * [UserUniversalisResource]s.
 *
 * [onToggleBookmark] servirá para cambiar entre una celebración y otra,
 * por ejemplo en el caso de ferias y memorias libres.
 */

fun LazyListScope.userUniversalisResourceCardItems(
    items: List<UserUniversalisResource>,
    onToggleBookmark: (item: UserUniversalisResource) -> Unit,
    itemModifier: Modifier = Modifier,
) = items(
    items = items,
    key = { 0 },
    itemContent = { userNewsResource ->

    UniversalisResourceCardExpanded(
            isBookmarked = false,
            userNewsResource = userNewsResource,
            onToggleBookmark = { /*onToggleBookmark(userNewsResource)*/ },
            modifier = itemModifier,
        )
    },
)