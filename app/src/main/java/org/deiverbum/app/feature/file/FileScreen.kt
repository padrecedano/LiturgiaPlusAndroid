package org.deiverbum.app.feature.file

import LPlusIcons
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.presentation.file.FileItemUiState
import org.deiverbum.app.core.presentation.file.FileViewModel
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.calendar.ErrorState
import org.deiverbum.app.feature.universalis.LoadingState
import org.deiverbum.app.feature.universalis.UniversalisResourceTitle
import org.deiverbum.app.feature.universalis.UniversalisUiState

@ExperimentalFoundationApi
@Composable
fun FileScreen(
    onFileClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    fileName: String?,
    viewModel: FileViewModel = hiltViewModel(),

    ) {

    //LaunchedEffect(fileName)
    //{ viewModel.setFileRequestt(fileName) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FileScreen(modifier = modifier, uiState = uiState)

}

@ExperimentalFoundationApi
@Composable
internal fun FileScreen(
    modifier: Modifier,
    uiState: FileViewModel.FileUiState
) {

    val state = rememberLazyListState()
    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            }

            when (uiState) {
                FileViewModel.FileUiState.Empty -> item { EmptyState() }
                is FileViewModel.FileUiState.Error -> item { ErrorState() }
                is FileViewModel.FileUiState.Loaded -> item {
                    FileToolbar(uiState = uiState)
                    FileResourceCardExpanded(uiState.itemState)
                }

                FileViewModel.FileUiState.Loading -> item { LoadingState() }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = 1
        val scrollbarState = state.scrollbarState(
            itemsAvailable = itemsAvailable,
        )
        state.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = state.rememberDraggableScroller(
                itemsAvailable = itemsAvailable,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                """.trimIndent(),
            )
        }
    }
}

/**
 * Barra superior de la pantalla.
 * Maneja la navegación hacia atrás y la lectura de voz.
 **
 *
 * @param uiState Un objeto [UniversalisUiState] con el estado en el ViewModel.
 * @param modifier El modificador.
 * @param onBackClick Para manejar la navegación hacia atrás.
 *
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileToolbar(
    uiState: FileViewModel.FileUiState,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
    ) {
        if (showBackButton) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = LPlusIcons.ArrowBack,
                    contentDescription = stringResource(
                        id = R.string.core_ui_back,
                    ),
                )
            }
        } else {
            Spacer(modifier = Modifier.width(1.dp))
        }
    }
}

/**
 * Muestra el cuerpo del contenido, con la ayuda de [universalisResourceCards].
 *
 **
 * @param name Es el nombre del `Universalis`.
 * @param description La fecha.
 * @param universalis Los datos, obtenidos desde el objeto `UniversalisData`.
 *
 * @see [UniversalisHeader]
 * @see [universalisResourceCards]
 *
 */

private fun LazyListScope.fileBody(
    universalis: UniversalisUiState.UniversalisData,
) {
    item {
        //UniversalisHeader(universalis.selectedTopicId!!, universalis.topics[0].date)
    }
    //fileResourceCards(universalis)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileResourceCardExpanded(itemState: FileItemUiState) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        UniversalisResourceTitle(
                            itemState.allData[0].title,
                            modifier = Modifier.fillMaxWidth((.8f)),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.height(14.dp))
                        //var lipsum = LoremIpsum()
                        //Text(lipsum.values.last())
                        itemState.allData.forEach {
                            Text(text = it.text.toString())
                        }
                    }
                }
            }
        }
    }
}

