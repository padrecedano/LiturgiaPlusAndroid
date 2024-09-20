package org.deiverbum.app.feature.universalis

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.NiaFilterChip
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.model.data.UniversalisRequest
import org.deiverbum.app.core.ui.ReaderButton
import org.deiverbum.app.core.ui.universalisResourceCardItems
import org.deiverbum.app.feature.tts.TextToSpeechScreenB
import org.deiverbum.app.util.Utils

/**
 * Es llamada desde la pantalla inicial para obtener el contenido
 * del tópico que haya sido elegido.
 *
 * Se encarga de obtener el estado del ViewModel.
 *
 **
 * @param onBackClick Lambda para manejar la navegación hacia atrás.
 * @param onTopicClick Lambda para manejar el tópico específico que fue seleccionado.
 * @param modifier El modificador.
 * @param viewModel Un objeto [UniversalisViewModel] para conectar con el repositorio.
 *
 *
 */

@ExperimentalFoundationApi
@ExperimentalMaterial3AdaptiveApi
@Composable
fun UniversalisFromHomeScreen(
    //showBackButton: Boolean,
    //onBackClick: () -> Unit,
    //onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UniversalisViewModel = hiltViewModel(),
    onReaderClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UniversalisFromHomeScreen(
        uiState = uiState,
        modifier = modifier,
        followTopic = viewModel::followTopic,
        //onReaderClick = onReaderClick,
        onReaderClick = viewModel::onReaderClick,
        onBackClick = {},//listDetailNavigator::navigateBack,
        //onTopicClick=onTopicClick
        onTopicClick = {
            //viewModel.onTopicClick(it)
            //onTopicClick(it)
        },
    )
}


/**
 * Función interna de la pantalla para objetos **`Universalis`**.
 *
 * Aquí se verificar el estado de la UI para mostrar el contenido.
 *
 * Los elementos se organizan del modo siguiente:
 * 1. [UniversalisToolbar] es la barra superior de  la pantalla.
 * 2. [universalisBody] crea el contenido de la pantalla.
 *
 **
 * @param uiState Un objeto [UniversalisUiState] con el estado en el ViewModel.
 * @param modifier El modificador.
 * @param onBackClick Para manejar la navegación hacia atrás.
 *
 *
 */

@ExperimentalFoundationApi
@Composable
internal fun UniversalisFromHomeScreen(
    uiState: UniversalisUiState,
    modifier: Modifier = Modifier,
    followTopic: (String, Boolean) -> Unit,
    onBackClick: () -> Unit,
    onReaderClick: () -> Unit,
    onTopicClick: (String) -> Unit,

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
                UniversalisUiState.Loading -> item {
                    NiaLoadingWheel(
                        modifier = modifier,
                        contentDesc = stringResource(id = R.string.generic_loading),
                    )
                }

                is UniversalisUiState.UniversalisData -> {
                    item {
                        UniversalisToolbar(
                            showBackButton = true,
                            onBackClick = onBackClick,
                            onReaderClick = onReaderClick,
                            uiState = uiState.topics,
                            modifier = modifier,
                        )
                    }

                    if (uiState.topics.isNotEmpty()) {
                        universalisBody(
                            universalis = uiState,
                            onReaderClick = onReaderClick
                        )
                    } else {
                        universalisEmpty()
                    }
                }

                UniversalisUiState.Empty -> universalisEmpty()
                UniversalisUiState.Error -> universalisError()

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

private fun LazyListScope.universalisBody(
    universalis: UniversalisUiState.UniversalisData,
    onReaderClick: () -> Unit,

    ) {
    item {
        UniversalisHeader(universalis.selectedTopicId!!, universalis.topics[0].date)
    }
    universalisResourceCardItems(
        items = universalis.topics,
        topicId = universalis.selectedTopicId!!,
        itemModifier = Modifier.padding(24.dp),
        onReaderClick = onReaderClick
    )
    //universalisResourceCards(universalis, onReaderClick)
}

private fun LazyListScope.universalisEmpty() {
    item {
        UniversalisEmptyScreen()
    }
}

private fun LazyListScope.universalisError() {
    item {
        UniversalisErrorScreen()
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
fun UniversalisToolbar(
    //showBackButton: Boolean, onBackClick: () -> Unit, onFollowClick: () -> Unit, uiState: List<FollowableTopic>, modifier: Modifier) {
    uiState: List<UniversalisRequest>,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    onReaderClick: () -> Unit = {},

    onFollowClick: (Boolean) -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
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
            // Keeps the NiaFilterChip aligned to the end of the Row.
            Spacer(modifier = Modifier.width(1.dp))
        }
        val selected = uiState[0].dynamic.useVoiceReader//true//uiState.isFollowed
        //showBottomSheet = uiState[0].dynamic.useVoiceReader//true//uiState.isFollowed

        ReaderButton(true, onClick = {
            showBottomSheet = true
        })

        NiaFilterChip(
            selected = selected,
            onSelectedChange = {
                showBottomSheet = true
            },
            modifier = Modifier.padding(end = 24.dp),
            //onClick={}
        ) {
            if (selected) {
                Text("FOLLOWING")

            } else {
                Text("NOT FOLLOWING")
            }
        }


        if (showBottomSheet) {
            var sb = uiState[0].data[0].getAllForRead()
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                TextToSpeechScreenB(text = sb)
                //TextToSpeechScreenA(text = sb)
                /*Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }*/
            }
        }

    }
}

// TODO: Could/should this be replaced with [LazyGridScope.newsFeed]?
private fun LazyListScope.universalisResourceCards(
    universalis: UniversalisUiState,
    onReaderClick: () -> Unit,
) {
    when (universalis) {
        is UniversalisUiState.UniversalisData -> {
            universalisResourceCardItems(
                items = universalis.topics,
                //TODO: Normalizar con respecto a VM y UseCase
                topicId = universalis.selectedTopicId!!,
                itemModifier = Modifier.padding(24.dp),
                onReaderClick = onReaderClick
            )
        }

        is UniversalisUiState.Loading -> item {
            NiaLoadingWheel(contentDesc = stringResource(id = R.string.generic_loading))
        }

        else -> item {
            Text("Error")
        }
    }
}

/**
 * Encabezado de la pantalla.
 *
 **
 *
 * @param name El nombre del `Universalis`
 * @param date La fecha.
 */
@Composable
private fun UniversalisHeader(name: String, date: Int) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        Text(name, style = MaterialTheme.typography.displayMedium)
        if (date > 0) {
            Text(
                Utils.formatDate(date.toString(), "yyyyMMdd", "EEEE d 'de' MMMM 'de' yyyy"),
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

/**
 * Se muestra cuando no hay contenido en el estado.
 */

@Composable
private fun UniversalisEmptyScreen() {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        Text(
            text = stringResource(id = R.string.feature_universalis_empty_message),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

/**
 * Se muestra cuando hay error en el estado.
 */
@Composable
private fun UniversalisErrorScreen() {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        Text(
            text = stringResource(id = R.string.feature_universalis_error_message),
            style = MaterialTheme.typography.titleMedium
        )
    }
}