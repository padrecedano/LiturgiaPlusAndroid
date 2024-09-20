package org.deiverbum.app.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.core.presentation.ui.LPlusAppState
import org.deiverbum.app.feature.calendar.navigation.calendarScreen
import org.deiverbum.app.feature.home.navigation.Book
import org.deiverbum.app.feature.home.navigation.BookDetail
import org.deiverbum.app.feature.home.navigation.BookDetailScreen
import org.deiverbum.app.feature.home.navigation.CustomNavType
import org.deiverbum.app.feature.home.navigation.HomeRoute
import org.deiverbum.app.feature.home.navigation.ListOfBooks
import org.deiverbum.app.feature.home.navigation.ListOfBooksScreenn
import org.deiverbum.app.feature.home.navigation.homeScreen
import org.deiverbum.app.feature.home.navigation.parcelableType
import org.deiverbum.app.feature.today.navigation.todayScreen
import org.deiverbum.app.feature.topic.navigation.navigateToUniversalisFromHome
import org.deiverbum.app.feature.topic.navigation.topicScreennn
import org.deiverbum.app.feature.universalis.UniversalisFromHomeScreen
import org.deiverbum.app.feature.universalis.UniversalisViewModel
import kotlin.reflect.typeOf

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@ExperimentalMaterial3AdaptiveApi

@Composable
fun LPlusNavHost(
    appState: LPlusAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    //startDestination: String = INTERESTS_ROUTE,
    startDestination: HomeRoute = HomeRoute,
    //startDestination: ListOfBooks = ListOfBooks,

    onReaderClick: () -> Unit

) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        //forYouScreen(onTopicClick = navController::navigateToTodays)
        //homeScreen(onTopicClick = navController::navigateToInterests)
        homeScreen(
            //onTopicClick = navController::navigateToTopic,
            onTopicClick = navController::navigateToUniversalisFromHome,

            //onBackClick = navController::popBackStack
        )
        //detailScreen()
        composable<ListOfBooks> {
            ListOfBooksScreenn(
                modifier = modifier,
                onBookClick = navController::navigateToUniversalis,
                /*navController.navigate(BookDetail(it))*/
            )
        }
        composable<UniversalisRouteFromHome> {
            TypeSafetyRoute(onReaderClick)
        }
        //onTopicClick = navController::navigateToTopic,
        /*onTopicClick = {
            Timber.d(it.title)
        }*/
        //navController::navigateToHomee,
        //onTopicClick = { navController.navigate(BookDetail(it)) }
        //onTopicClick = {}

        //navController::navigateToHomee(BookDetail(it.id))


            //onBackClick = navController::popBackStack
        //)
        /*bookmarksScreen(
            onTopicClick = navController::navigateToTodays,
            onShowSnackbar = onShowSnackbar,
        )*/
        calendarScreen(
            onDateSelected = {},
            //onShowSnackbar = onShowSnackbar,
        )
        todayScreen(
            {},
            {}
            //onShowSnackbar = onShowSnackbar,
        )
        topicScreennn(true, {}, {})
        /*searchScreen(
            onBackClick = navController::popBackStack,
            onInterestsClick = { appState.navigateToTopLevelDestination(TopLevelDestination.INTERESTS) },
            onTopicClick = navController::navigateToTodays,
        )*/

        composable<BookDetail>(
            //typeMap = mapOf(typeOf<Book>() to parcelableType<Book>())
            typeMap = mapOf(typeOf<Book>() to CustomNavType(Book::class.java, Book.serializer()))

        ) {
            BookDetailScreen(modifier = modifier)
        }

        composable<Book>(
            typeMap = mapOf(typeOf<Book>() to parcelableType<Book>())
        ) {
            BookDetailScreen(modifier = modifier)

        }
        /*composable<BookDetail>(
            typeMap = mapOf(typeOf<Book>() to BookType)
        ) { backStackEntry ->
            val book = backStackEntry.toRoute<BookDetail>().book
        }*/
        /*           composable<BookDetail> { backStackEntry ->
                   val bookDetail = backStackEntry.toRoute<BookDetail>()

                   BookDetailScreenn(
                       modifier = modifier,
                       book = bookDetail.book,
                   )
               }*/
        composable("topic_route/1") {
            val a = ""
            val s = LoremIpsum()
            Text(s.values.single())
        }
    }

}

@Serializable
data class UniversalisRouteFromHome(
    // The ID of the topic which will be initially selected at this destination
    val initialTopicId: String? = null,
    val date: String? = null,
    //val itemUi:ItemUI?=null

)

fun NavController.navigateToUniversalis(
    initialTopicId: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = UniversalisRouteFromHome(initialTopicId), navOptions)
}

@ExperimentalMaterial3AdaptiveApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TypeSafetyRoute(
    onReaderClick: () -> Unit,
    /*onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedTopic: Boolean = false,*/
    viewModel: UniversalisViewModel = hiltViewModel(),
) {
    UniversalisFromHomeScreen(
        onReaderClick = onReaderClick

    )
}

/*{
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            UniversalisUiState.Loading ->
                NiaLoadingWheel(
                    contentDesc = "**",
                )

            is UniversalisUiState.UniversalisData ->
                Text((uiState as UniversalisUiState.UniversalisData).topics[0].data[0].getAllForView())

            is UniversalisUiState.Empty -> Text("empty")
            UniversalisUiState.Error -> TODO()
        }

}
}*/
@Serializable
data class Profile(val id: String)