package org.deiverbum.app.feature.tts

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.app.TaskStackBuilder
import org.deiverbum.app.core.presentation.MainActivity

@ExperimentalFoundationApi
class PlaybackService : DemoPlaybackService() {

    companion object {
        private val immutableFlag =
            if (Build.VERSION.SDK_INT >= 23) PendingIntent.FLAG_IMMUTABLE else 0
    }

    override fun getSingleTopActivity(): PendingIntent? {
        return getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            immutableFlag or FLAG_UPDATE_CURRENT
        )
    }

    override fun getBackStackedActivity(): PendingIntent? {
        return TaskStackBuilder.create(this).run {
            addNextIntent(Intent(this@PlaybackService, MainActivity::class.java))
            //addNextIntent(Intent(this@PlaybackService, PlayerActivity::class.java))
            getPendingIntent(0, immutableFlag or FLAG_UPDATE_CURRENT)
        }
    }
}