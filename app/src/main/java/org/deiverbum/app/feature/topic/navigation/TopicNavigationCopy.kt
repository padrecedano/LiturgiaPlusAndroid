/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deiverbum.app.feature.topic.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.deiverbum.app.feature.topic.TopicRoute
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

@VisibleForTesting
internal const val TOPIC_ID_ARGg = "topicId"
const val TOPIC_ROUTEe = "topic_route"

internal class TopicArgss(val topicId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[TOPIC_ID_ARG]),
                    URL_CHARACTER_ENCODING
                )
            )
}

fun NavController.navigateToTopicc(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    val newRoute = "$TOPIC_ROUTE/$encodedId"
    navigate(newRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.topicScreenn(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable(
        route = "topic_route/{$TOPIC_ID_ARG}",
        arguments = listOf(
            navArgument(TOPIC_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        TopicRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )
    }
}
