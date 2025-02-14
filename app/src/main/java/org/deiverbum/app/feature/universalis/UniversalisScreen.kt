package org.deiverbum.app.feature.universalis

import LPlusIcons
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import androidx.media3.common.util.UnstableApi
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.UniversalisSingleAppBar
import org.deiverbum.app.core.designsystem.component.UniversalisTopAppBar
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.AlteriSanctii
import org.deiverbum.app.core.model.data.UniversalisResource
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.ui.TrackScrollJank
import org.deiverbum.app.core.ui.UniversalisBody
import org.deiverbum.app.feature.tts.ScreenTtsPlayer
import org.deiverbum.app.feature.tts.TtsMediaViewModel
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

//@ExperimentalStdlibApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterial3AdaptiveApi
@Composable
fun UniversalisFromHomeScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UniversalisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state = rememberLazyListState()
    TrackScrollJank(scrollableState = state, stateName = "universalis:screen")
    UniversalisFromHomeScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick
    )
}

/**
 * Función interna de la pantalla para objetos **`Universalis`**.
 *
 * Aquí se verifica el estado de la UI para mostrar el contenido.
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

//@ExperimentalStdlibApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
internal fun UniversalisFromHomeScreen(
    uiState: UniversalisUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    when (uiState) {
        UniversalisUiState.Empty -> {
            NoDataScaffold(
                onBackClick = onBackClick,
                title = "Vacío",
            )
        }

        UniversalisUiState.Loading -> LoadingState(modifier = modifier)
        is UniversalisUiState.UniversalisData -> {
            //TODO: Revisar condicional
            /*if (uiState.topics.isNullOrEmpty()) {
                NoDataScaffold(
                    onBackClick = onBackClick,
                    title = uiState.selectedTopicId,
                )
            } else {*/
            //Text(uiState.topics[0].data.rosarium!!.getForView().toString())
            //return
                UniversalisResourceData(
                    onBackClick = onBackClick,
                    universalisResource = uiState.topics //TODO: Quitar listOf
                )
            //}
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

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisToolbar(
    uiState: List<UniversalisResource>,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    viewModelTts: TtsMediaViewModel = hiltViewModel()
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
            Spacer(modifier = Modifier.width(1.dp))
        }

        ReaderButton(
            true,
            onClick = {
                showBottomSheet = true
                //viewModelSong::onUiEvents
            },
            modifier = Modifier.padding(end = 24.dp),
        )

        if (showBottomSheet) {
            val sb = uiState[0].data.getAllForRead()
            //viewModelSimpleMedia.loadData("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3")
            viewModelTts.loadData(
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
                sb.toString()
            )

            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {

                //TextToSpeechScreenB(text = sb)
                //TextToSpeechScreenA(text = sb)
                //ExoPlayerView(text = sb)
                //ExoPlayerVieww(text = sb)
                //Media3TtsView(sb)

                //ScreenTts(viewModelSimpleMedia,sb)
                ScreenTtsPlayer(viewModelTts, sb)
                //TtsMediaScreen(viewModelTts,{})
                /*
                BottomPlayerTab(
                    selectedSong = selectedSong!!,
                    onUIEvents = viewModelSong::onUiEvents,
                    onBottomTabClick = {
                        Timber.d("a","a")
                    }
                )*/
                //Media3PlayerView(videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
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
    typusId: Int,
    resource: UniversalisResource,
    userData: UserData
    ) {

    Column {
        Box(
            modifier = Modifier.padding(7.dp),
        ) {
            Column {
                //Spacer(modifier = Modifier.height(12.dp))
                var title = ""
                var meta = ""
                val data = resource.data
                typusId.let {
                    when {
                        it == 20 -> {
                            val sancti = data.liturgia!!.liturgiaTypus as AlteriSanctii
                            title = data.liturgia!!.nomen
                            meta = sancti.sanctus.monthName
                        }

                        it == 30 -> {

                            title = "Rosario"
                            meta = "Viernes"
                        }

                        else -> {
                            if (data == null) {
                                title = "Aún no hay datos para esta fecha."
                                meta = ""
                            } else {
                                title = data.liturgia!!.tempus!!.externus.toString()
                                meta = data.liturgia!!.nomen
                            }
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (data.todayDate > 0) {
                        Text(
                            Utils.formatDate(
                                data.todayDate.toString(),
                                "yyyyMMdd",
                                "EEEE d 'de' MMMM 'de' yyyy"
                            ),
                            //modifier = Modifier.padding(top = 10.dp),
                            style = NiaTypography.bodyLarge,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row {

                    //UniversalisHeader("universalis.selectedTopicId!!", data.todayDate)
                    UniversalisResourceTitle(
                        title,
                        userData.dynamic,
                        modifier = Modifier.fillMaxWidth((.8f)),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    UniversalisResourceMetaData(meta)
                }
                Spacer(modifier = Modifier.height(14.dp))
                if (typusId == 301) {
                    Text("Rosario")
                } else {

                UniversalisBody(
                    data = resource.data,
                    topicId = typusId,
                    userData = userData,
                )
                }
            }
        }
    }
}

/**
 * [UniversalisResourceCardExpanded] card usada en `UniversalisScreen`
 */

//@ExperimentalStdlibApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisResourceData(
    universalisResource: UniversalisResource,
    onBackClick: () -> Unit,
    viewModelTts: TtsMediaViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val universalis = universalisResource.data
    val typusId = universalisResource.id
    val userData = universalisResource.dynamic
    val subTitle = when (universalisResource.id) {
        20 -> {
            val sancti = universalisResource.data.liturgia!!.liturgiaTypus as AlteriSanctii
            sancti.sanctus.monthName
            //universalisResource.data[0].liturgia.liturgiaTypus
        }

        else -> Utils.formatDate(
            universalisResource.date.toString(),
            "yyyyMMdd",
            "d '-' MMMM yyyy"
        )
    }
    Scaffold(
        topBar = {
            UniversalisTopAppBar(
                userData = userData.dynamic,
                title = universalisResource.title,
                subtitle = subTitle,
                onReaderClick = {
                    showBottomSheet = true
                },
                navigationIcon = LPlusIcons.ArrowBack,
                readerIcon = LPlusIcons.Reader,
                calendarIcon = LPlusIcons.Calendar,

                navigationIconContentDescription = "Navigation icon",
                actionIcon = LPlusIcons.MoreVert,
                actionIconContentDescription = "Action icon",
                onNavigationClick = { onBackClick() }
            )
        },
    ) { innerPadding ->

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column {
                Box(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(state = scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp)

                    ) {
                        UniversalisResourceCardExpanded(
                            resource = universalisResource,
                            typusId = typusId,
                            userData = userData
                        )
                    }
                }
            }
        }
        if (showBottomSheet) {
            val sb = universalis.getAllForRead()
            viewModelTts.loadData(
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
                sb.toString()
            )
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                ScreenTtsPlayer(viewModelTts, sb)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoDataScaffold(
    title: String?,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            UniversalisSingleAppBar(
                title = title!!,
                navigationIcon = LPlusIcons.ArrowBack,
                readerIcon = LPlusIcons.Reader,
                calendarIcon = LPlusIcons.Calendar,
                navigationIconContentDescription = "Navigation icon",
                actionIcon = LPlusIcons.MoreVert,
                actionIconContentDescription = "Action icon",
                onNavigationClick = { onBackClick() }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UniversalisEmptyScreen()
        }
    }
}

@Composable
fun UniversalisResourceTitle(
    resourceTitle: String,
    userData: UserDataDynamic,
    modifier: Modifier = Modifier,
) {
    val typography = getPersonalizedTypography(userData.fontSize)
    Text(resourceTitle, style = typography.headlineSmall, modifier = modifier)
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










