package org.deiverbum.app.feature.home.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.deiverbum.app.feature.home.BookViewModel
import org.deiverbum.app.ui.home.HomeScreenBody
import kotlin.reflect.typeOf

object SampleData {
    val books = (0..25).map {
        Book(it, "Book $it")
    }

    fun getBook(id: Int) = books.first { it.id == id }
}

@Serializable
@Parcelize
data class Book(val id: Int, val title: String) : Parcelable

@Composable
fun ListOfBooksScreen(
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(SampleData.books) {
            BookCell(
                modifier = Modifier.clickable(onClick = { onBookClick(it) }),
                book = it,
            )
        }
    }
}

@Composable
fun ListOfBooksScreenn(
    modifier: Modifier = Modifier,
    onBookClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(SampleData.books) {
            BookCell(
                modifier = Modifier.clickable(onClick = { onBookClick(it.id.toString()) }),
                book = it,
            )
        }
    }
}

@Composable
fun BookCell(book: Book, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = book.title,
    )
}

@Composable
fun BookDetailScreenn(
    modifier: Modifier = Modifier,
    book: Book,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Id: ${book.id}")
        Text("Title: ${book.title}")
    }
}

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    bookId: Int,
) {
    val book by remember { mutableStateOf(SampleData.getBook(bookId)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Id: ${book.id}")
        Text("Title: ${book.title}")
    }
}

@Serializable
data object HomeRoute

//fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute, navOptions)
fun NavController.navigateToHomee(navOptions: NavOptions) =
    navigate(route = ListOfBooks, navOptions)

@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.homeScreen(
    onTopicClick: (String) -> Unit,
    //onBackClick: () -> Unit,
) {
    composable<HomeRoute> {
        //Text("home*")
        HomeScreenBody(onTopicClick)
//MainCompose()
        //HomeRoute(onTopicClick)
        //InterestsRouteNew(onTopicClick = onTopicClick)
        //TopicRoute(showBackButton = true, onBackClick =onBackClick, onTopicClick = onTopicClick)
        //InterestsScreen()
        /*UniversalisRoute(
            showBackButton = true,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )*/
    }
}


@Serializable
object ListOfBooks

@Serializable
data class BookDetaill(val id: Int)

@Serializable
data class BookDetail(val book: Book) {
    companion object {
        val typeMap = mapOf(typeOf<Book>() to serializableType<Book>())

        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<BookDetail>(typeMap)
    }
}

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}

inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}

val BookType = object : NavType<Book>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): Book? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Book::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Book {
        return Json.decodeFromString<Book>(value)
    }

    override fun serializeAsValue(value: Book): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Book) {
        bundle.putParcelable(key, value)
    }
}

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
) {
    val book by viewModel.book.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Id: ${book.id}")
        Text("Title: ${book.title}")
    }
}

class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz) as T
        } else {
            @Suppress("DEPRECATION") // for backwards compatibility
            bundle.getParcelable(key)
        }

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putParcelable(key, value)

    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)

    override fun serializeAsValue(value: T): String = Json.encodeToString(serializer, value)

    override val name: String = clazz.name
}