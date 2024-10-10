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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.model.data.Alteri
import org.deiverbum.app.core.model.data.UniversalisRequest
import org.deiverbum.app.core.ui.TrackScrollJank
import org.deiverbum.app.core.ui.Universalis
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
 * @param modifier El modificador.
 * @param viewModel Un objeto [UniversalisViewModel] para conectar con el repositorio.
 *
 *
 */

@ExperimentalFoundationApi
@ExperimentalMaterial3AdaptiveApi
@Composable
fun UniversalisFromHomeScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UniversalisViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state = rememberLazyListState()

    TrackScrollJank(scrollableState = state, stateName = "universalis:screen")
    UniversalisFromHomeScreen(
        uiState = uiState,
        modifier = modifier,
        //onReaderClick = {},
        onBackClick = onBackClick,

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
    onBackClick: () -> Unit,
    //onReaderClick: () -> Unit,

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
                    LoadingState(modifier = modifier)
                }

                is UniversalisUiState.UniversalisData -> {
                    item {
                        UniversalisToolbar(
                            showBackButton = true,
                            onBackClick = onBackClick,
                            //onReaderClick = onReaderClick,
                            uiState = uiState.topics,
                            modifier = modifier,
                        )
                    }

                    if (uiState.topics.isNotEmpty()) {
                        universalisBody(
                            universalis = uiState,
                            //onReaderClick = onReaderClick
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
    ) {
    item {
        UniversalisHeader(universalis.selectedTopicId!!, universalis.topics[0].date)
    }
    universalisResourceCards(universalis)
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
    uiState: List<UniversalisRequest>,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
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

        ReaderButton(
            true,
            onClick = {
                showBottomSheet = true
            },
            modifier = Modifier.padding(end = 24.dp),
        )

        if (showBottomSheet) {
            var sb = uiState[0].data[0].getAllForRead()
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
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
) {
    when (universalis) {
        is UniversalisUiState.UniversalisData -> {
            universalisResourceCardItems(
                items = universalis.topics,
                //TODO: Normalizar con respecto a VM y UseCase
                //topicId = universalis.selectedTopicId!!,
                //itemModifier = Modifier.padding(24.dp),
                //onReaderClick = onReaderClick
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

//TODO: Trasladar a otra carpeta
@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    NiaLoadingWheel(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .testTag("universalis:loading"),
        contentDesc = stringResource(id = R.string.generic_loading),
    )
}


/**
 * [UniversalisResourceCardExpanded] card usada en `UniversalisScreen`
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisResourceCardExpanded(
    universalisResource: List<UniversalisRequest>,

    ) {
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
                        var title: String
                        universalisResource[0].id.let {
                            title = when {
                                it == 20 -> {
                                    val sancti =
                                        universalisResource[0].data[0].liturgia!!.liturgiaTypus as Alteri.Sancti
                                    sancti.sanctus.monthName
                                }

                                else -> {
//"TODO: UniversalisResourceCard 75" ERROR cuando no hay homilías
                                    val l = universalisResource[0].data[0].liturgia
                                    if (l == null) {
                                        "Aún no hay datos para esta fecha."
                                    } else {
                                        universalisResource[0].data[0].liturgia!!.tempus!!.externus.toString()
                                    }
                                }
                            }
                        }
                        UniversalisResourceTitle(
                            title,
                            modifier = Modifier.fillMaxWidth((.8f)),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        UniversalisResourceMetaData(universalisResource[0].data[0].liturgia!!.nomen)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Universalis(
                        universalisResource[0].data[0],
                        topicId = universalisResource[0].id,
                        universalisResource[0].dynamic,
                    )
                }
            }
        }
    }
}

@Composable
fun UniversalisResourceTitle(
    newsResourceTitle: String,
    modifier: Modifier = Modifier,
) {
    Text(newsResourceTitle, style = MaterialTheme.typography.headlineSmall, modifier = modifier)
}

@Composable
fun ReaderButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NiaIconToggleButton(
        checked = isBookmarked,
        onCheckedChange = {
            onClick()
        },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = LPlusIcons.ReaderBorder,
                contentDescription = "stringResource(R.string.core_ui_bookmark)",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = LPlusIcons.Reader,
                contentDescription = "stringResource(R.string.core_ui_unbookmark)",
            )
        },
    )
}

@Composable
fun UniversalisResourceMetaData(
    content: String,
) {
    Text(
        text = content,
        style = MaterialTheme.typography.titleMedium,
    )
}

fun LazyListScope.universalisResourceCardItems(
    items: List<UniversalisRequest>,
) = items(
    items = items,
    key = { it.data[0].liturgyFK },
    itemContent = {
        UniversalisResourceCardExpanded(
            universalisResource = items,
        )
    },
)

