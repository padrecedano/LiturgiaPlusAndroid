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
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.home.navigation.Book
import org.deiverbum.app.feature.home.navigation.BookDetail
import org.deiverbum.app.feature.home.navigation.BookType
import org.deiverbum.app.navigation.UniversalisRouteFromHome
import kotlin.reflect.typeOf


@Serializable
data class TopicRoute(val id: String)

fun NavController.navigateToTopicc(navOptions: NavOptions) =
    navigate(route = TopicRoute, navOptions)

fun NavController.navigateToTopiccc(
    topicId: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = TopicRoute(topicId)) {
        navOptions()
    }
}

fun NavController.navigateToUniversalisFromHome(
    topicId: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = UniversalisRouteFromHome(topicId)) {
        navOptions()
    }
}

fun NavController.navigateToBook(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = BookDetail) {
        navOptions()
    }
}

fun NavController.navigateToTopic(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate("topic_route/1") {
        navOptions()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.topicScreennn(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable<TopicRoute> { backStackEntry ->
        val user: TopicRoute = backStackEntry.toRoute()

        UserDetailsScreen(user)
        /*HomeRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )*/
        //val t=LoremIpsum(10)
        //Text(text = t.values.last())
    }
}

@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.bookScreen(

) {
    composable<BookDetail>(
        typeMap = mapOf(typeOf<Book>() to BookType)
    )

    { backStackEntry ->
        val user: TopicRoute = backStackEntry.toRoute()
        val book = backStackEntry.toRoute<BookDetail>().book
        Text(text = book.title)
        //UserDetailsScreen(user)
        /*HomeRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )*/
        //val t=LoremIpsum(10)
        //Text(text = t.values.last())
    }
}

@Composable
fun UserDetailsScreen(
    user: TopicRoute
) {
    val t = LoremIpsum(10)
    //Text(text = t.values.last())
    Text(text = user.id)
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