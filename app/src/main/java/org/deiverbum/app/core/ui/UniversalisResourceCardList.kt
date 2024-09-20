package org.deiverbum.app.core.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import org.deiverbum.app.core.analytics.LocalAnalyticsHelper
import org.deiverbum.app.core.model.data.UniversalisRequest
import timber.log.Timber

fun LazyListScope.universalisResourceCardItems(
    items: List<UniversalisRequest>,
    itemModifier: Modifier = Modifier,
    topicId: String?,
    onReaderClick: () -> Unit,
) = items(
    items = items,
    key = { it.data[0].liturgyFK },
    itemContent = { universalisResource ->
        val analyticsHelper = LocalAnalyticsHelper.current

        UniversalisResourceCardExpanded(
            topicId = topicId,
            universalisResource = items,
            isBookmarked = true,//userNewsResource.isSaved,
            hasBeenViewed = true,//userNewsResource.hasBeenViewed,
            onToggleBookmark = onReaderClick,//{ /*onToggleBookmark(userNewsResource)*/ },
            onClick = {
                analyticsHelper.logNewsResourceOpened(
                    newsResourceId = universalisResource.data[0].liturgyFK.toString(),
                )
                //launchCustomChromeTab(context, resourceUrl, backgroundColor)
                //onNewsResourceViewed(userNewsResource.id)
            },
            onTopicClick = {},
            modifier = itemModifier,
            onReaderClick = {
                Timber.d("axy")
            }

            //onReaderClick
        )
    },
)
