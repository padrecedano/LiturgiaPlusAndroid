package org.deiverbum.app.core.notifications

import org.deiverbum.app.core.model.data.NewsResource
import javax.inject.Inject

/**
 * Implementation of [Notifier] which does nothing. Useful for tests and previews.
 */
internal class NoOpNotifier @Inject constructor() : Notifier {
    override fun postNewsNotifications(newsResources: List<NewsResource>) = Unit
}
