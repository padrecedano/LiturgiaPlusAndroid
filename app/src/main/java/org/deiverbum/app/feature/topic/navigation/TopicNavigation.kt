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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.calendar.navigation.CalendarRoute
import org.deiverbum.app.feature.home.HomeScreen


@Serializable
data class TopicRoute(val id: String)

@ExperimentalLayoutApi
@ExperimentalCoroutinesApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.calendarScreen(
    //onTopicClick: () -> Unit,
    onDateSelected: (Long) -> Unit,
    onTopicClick: (String?) -> Unit
) {
    composable<CalendarRoute> {
        val t = LoremIpsum()
        //Text(t.values.last())
        //(Text("Calendar Navigation\nUniversalisRouteFromHome")
        HomeScreen(onTopicClick)
        //CalendarScreen(onTopicClick = onTopicClick,onDateSelected={})
        //DatePickerDocked(onValueChange=onTopicClick)
        /*InterestsRouteInHome(
            onTopicClick = onTopicClick,
            onBackClick = {},
            showBackButton = true
        )*/
    }
}


fun NavController.navigateToTopic(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate("topic_route/1") {
        navOptions()
    }
}




@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.topicScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onReaderClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable<TopicRoute> {
        /*HomeRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )*/
        /*UniversalisRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
            onReaderClick = onReaderClick
        )*/
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.topicScreenn(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onReaderClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable<TopicRoute> {
        /*HomeRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )*/
        /*UniversalisRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
            onReaderClick = onReaderClick
        )*/
    }
}