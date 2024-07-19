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

package org.deiverbum.app.feature.universalis.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.deiverbum.app.feature.universalis.UniversalisRoute
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()


@VisibleForTesting
var now = LocalDateTime.now()!!

val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")!!

internal val TOPIC_ID_ARGG = now.format(formatter)//"topicId"
const val TOPIC_ROUTE = "topic_route"

const val TOPIC_ID_ARG = "topicId"
const val UNIVERSALIS_ROUTE_BASE = "universalis_route"
const val UNIVERSALIS_ROUTE = "$UNIVERSALIS_ROUTE_BASE?$TOPIC_ID_ARG={$TOPIC_ID_ARG}"

internal class UniversalisArgs(val topicId: String) {

    constructor(savedStateHandle: SavedStateHandle) :
            this(now.format(formatter))
}

fun NavController.navigateToUniversalis(topicId: String? = null, navOptions: NavOptions? = null) {
    /*val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    val newRoute = "$TOPIC_ROUTE/$encodedId"
    navigate(newRoute) {
        navOptions()
    }*/
    val topicId = "1"
    val route = if (topicId != null) {
        "${UNIVERSALIS_ROUTE_BASE}?${TOPIC_ID_ARG}=$topicId"

    } else {
        UNIVERSALIS_ROUTE_BASE
    }
    val r = "universalis_route?topicId=1"

    navigate(r, navOptions)

}


fun NavGraphBuilder.universalisScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable(
        route = UNIVERSALIS_ROUTE,
        arguments = listOf(
            navArgument(TOPIC_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        ),
    )
    /*composable(
        route = "topic_route/{$TOPIC_ID_ARG}",
        arguments = listOf(
            navArgument(TOPIC_ID_ARG.toString()) { type = NavType.StringType },
        ),
    )*/ {
        UniversalisRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )
    }
}
