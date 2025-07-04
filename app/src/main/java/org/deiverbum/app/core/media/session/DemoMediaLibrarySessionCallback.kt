package org.deiverbum.app.core.media.session

import android.content.Context
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionError
import androidx.media3.session.SessionResult
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

/** A [MediaLibraryService.MediaLibrarySession.Callback] implementation. */
open class DemoMediaLibrarySessionCallback(context: Context) :
    MediaLibraryService.MediaLibrarySession.Callback {

    init {
        //MediaItemTree.initialize(context.assets)
    }

    private val commandButtons: List<CommandButton> =
        listOf(
            CommandButton.Builder(CommandButton.ICON_SHUFFLE_OFF)
                .setDisplayName("R.string.exo_controls_shuffle_on_description")
                .setSessionCommand(
                    SessionCommand(
                        CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON,
                        Bundle.EMPTY
                    )
                )
                .build(),
            CommandButton.Builder(CommandButton.ICON_SHUFFLE_ON)
                .setDisplayName("R.string.exo_controls_shuffle_off_description")
                .setSessionCommand(
                    SessionCommand(
                        CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF,
                        Bundle.EMPTY
                    )
                )
                .build(),
        )

    @OptIn(UnstableApi::class) // MediaSession.ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS
    val mediaNotificationSessionCommands =
        MediaSession.ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS.buildUpon()
            .also { builder ->
                // Put all custom session commands in the list that may be used by the notification.
                commandButtons.forEach { commandButton ->
                    commandButton.sessionCommand?.let { builder.add(it) }
                }
            }
            .build()

    // ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS
    // ConnectionResult.AcceptedResultBuilder
    @OptIn(UnstableApi::class)
    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
    ): MediaSession.ConnectionResult {
        if (
            session.isMediaNotificationController(controller) ||
            session.isAutomotiveController(controller) ||
            session.isAutoCompanionController(controller)
        ) {
            // Select the button to display.
            val customButton = commandButtons[if (session.player.shuffleModeEnabled) 1 else 0]
            return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
                .setAvailableSessionCommands(mediaNotificationSessionCommands)
                .setMediaButtonPreferences(ImmutableList.of(customButton))
                .build()
        }
        // Default commands without media button preferences for common controllers.
        return MediaSession.ConnectionResult.AcceptedResultBuilder(session).build()
    }

    @OptIn(UnstableApi::class) // MediaSession.isMediaNotificationController
    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle,
    ): ListenableFuture<SessionResult> {
        if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON == customCommand.customAction) {
            // Enable shuffling.
            session.player.shuffleModeEnabled = true
            // Change the media button preferences to contain the `Disable shuffling` button.
            session.setMediaButtonPreferences(
                session.mediaNotificationControllerInfo!!,
                ImmutableList.of(commandButtons[1]),
            )
            return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
        } else if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF == customCommand.customAction) {
            // Disable shuffling.
            session.player.shuffleModeEnabled = false
            // Change the media button preferences to contain the `Enable shuffling` button.
            session.setMediaButtonPreferences(
                session.mediaNotificationControllerInfo!!,
                ImmutableList.of(commandButtons[0]),
            )
            return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
        }
        return Futures.immediateFuture(SessionResult(SessionError.ERROR_NOT_SUPPORTED))
    }

    /*override fun onGetLibraryRoot(
        session: MediaLibraryService.MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        params: MediaLibraryService.LibraryParams?,
    ): ListenableFuture<LibraryResult<MediaItem>> {
        return Futures.immediateFuture(LibraryResult.ofItem(MediaItem(), params))
    }*/

    /*@OptIn(UnstableApi::class) // SessionError.ERROR_BAD_VALUE
    override fun onGetItem(
        session: MediaLibraryService.MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        mediaId: String,
    ): ListenableFuture<LibraryResult<MediaItem>> {
        MediaItemTree.getItem(mediaId)?.let {
            return Futures.immediateFuture(LibraryResult.ofItem(it, /* params= */ null))
        }
        return Futures.immediateFuture(LibraryResult.ofError(SessionError.ERROR_BAD_VALUE))
    }*/
    /*
        @OptIn(UnstableApi::class) // SessionError.ERROR_BAD_VALUE
        override fun onGetChildren(
            session: MediaLibraryService.MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            parentId: String,
            page: Int,
            pageSize: Int,
            params: MediaLibraryService.LibraryParams?,
        ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
            val children = MediaItemTree.getChildren(parentId)
            if (children.isNotEmpty()) {
                return Futures.immediateFuture(LibraryResult.ofItemList(children, params))
            }
            return Futures.immediateFuture(LibraryResult.ofError(SessionError.ERROR_BAD_VALUE))
        }
    */

    /*
        private fun resolveMediaItems(mediaItems: List<MediaItem>): List<MediaItem> {
            val playlist = mutableListOf<MediaItem>()
            mediaItems.forEach { mediaItem ->
                if (mediaItem.mediaId.isNotEmpty()) {
                    MediaItemTree.expandItem(mediaItem)?.let { playlist.add(it) }
                } else if (mediaItem.requestMetadata.searchQuery != null) {
                    playlist.addAll(MediaItemTree.search(mediaItem.requestMetadata.searchQuery!!))
                }
            }
            return playlist
        }
    */

    companion object {
        private const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON =
            "android.media3.session.demo.SHUFFLE_ON"
        private const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF =
            "android.media3.session.demo.SHUFFLE_OFF"
    }
}