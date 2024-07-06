package org.deiverbum.app.core.notifications

import org.deiverbum.app.core.model.data.NewsResource

/**
 * Interface for creating notifications in the app
 */
interface Notifier {
    fun postNewsNotifications(newsResources: List<NewsResource>)
}
