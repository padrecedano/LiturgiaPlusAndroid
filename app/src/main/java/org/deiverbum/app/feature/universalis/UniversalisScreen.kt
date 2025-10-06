package org.deiverbum.app.feature.universalis

import LPlusIcons
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deiverbum.app.R
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.analytics.LocalAnalyticsHelper
import org.deiverbum.app.core.data.repository.logUniversalisTtsEvent
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.NiaTab
import org.deiverbum.app.core.designsystem.component.NiaTabRow
import org.deiverbum.app.core.designsystem.component.TextError
import org.deiverbum.app.core.designsystem.component.UniversalisSingleAppBar
import org.deiverbum.app.core.designsystem.component.UniversalisTopAppBar
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.alteri.AlteriSanctii
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.configuration.UserDataDynamic
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.core.ui.TrackScrollJank
import org.deiverbum.app.core.ui.UniversalisBodyForView
import org.deiverbum.app.core.ui.universalisBodyForRead
import org.deiverbum.app.feature.calendar.ErrorState
import org.deiverbum.app.feature.tts.TtsMediaViewModel
import org.deiverbum.app.feature.tts.TtsViewModel
import org.deiverbum.app.feature.tts.navigation.navigateToTts
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
@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterial3AdaptiveApi
@Composable
fun UniversalisFromHomeScreen(
    onBackClick: () -> Unit,
    navController: NavController, // <--- PASO 4: Aceptar navController aquí

    modifier: Modifier = Modifier,
    viewModel: UniversalisViewModel = hiltViewModel(),
    //viewModelTts: TtsViewModel = hiltViewModel()
    viewModelTts: TtsViewModel,

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val state = rememberLazyListState()
    TrackScrollJank(scrollableState = state, stateName = "universalis:screen")
    UniversalisFromHomeScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        viewModelTts = viewModelTts,
        navController = navController, // <--- PASO 5: Pasar navController a la pantalla
        //analyticsHelper=analyticsHelper
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

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
internal fun UniversalisFromHomeScreen(
    uiState: UniversalisUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModelTts: TtsViewModel,
    navController: NavController,
) {
    val analyticsHelper = LocalAnalyticsHelper.current

    when (uiState) {
        /*UniversalisUiState.Empty -> {
            NoDataScaffold(
                onBackClick = onBackClick,
                title = "Vacío",
            )
        }*/

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
                universalisResource = uiState.topics,
                analyticsHelper = analyticsHelper,
                viewModelTts = viewModelTts,
                navController = navController
            )
            //}
        }

        is UniversalisUiState.UniversalisError -> {
            NoDataScaffold(
                onBackClick = onBackClick,
                title = "Error",
                uiState = uiState,
                //ErrorState(uiState.message)
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

@RequiresApi(Build.VERSION_CODES.O)
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

        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
        val titles = listOf("Topics", "People")
        NiaTabRow(selectedTabIndex = selectedTabIndex) {
            titles.forEachIndexed { index, title ->
                NiaTab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) },
                )
            }
        }

        if (showBottomSheet) {
            //val sb = uiState[0].data.getAllForRead()
            //viewModelSimpleMedia.loadData("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3")
            viewModelTts.loadData(
                //"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
                "sb.toString()"
            )

            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                content = {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -12.dp)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.TopCenter),
                                text = "Title of ModalBottomSheet"
                            )
                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(y = -12.dp),
                                onClick = { showBottomSheet = false }
                            ) {
                                Icon(
                                    imageVector = LPlusIcons.Reader,
                                    contentDescription = "item",
                                )
                            }
                        }
                    }

                }
            )

            //TextToSpeechScreenB(text = sb)
            //TextToSpeechScreenA(text = sb)
            //ExoPlayerView(text = sb)
            //ExoPlayerVieww(text = sb)
            //Media3TtsView(sb)

            //ScreenTts(viewModelSimpleMedia,sb)
            //ScreenTtsPlayer(viewModelTts, sb)
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

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisResourceCardExpanded(
    typusId: Int,
    resource: UniversalisResource,
    userData: UserData
) {
    Column {
        Spacer(modifier = Modifier.height(14.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            UniversalisBodyForView(resource)
        }
    }
}

/**
 * [UniversalisResourceCardExpanded] card usada en `UniversalisScreen`
 */

//@ExperimentalStdlibApi
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisResourceData(
    universalisResource: UniversalisResource,
    onBackClick: () -> Unit,
    viewModelTts: TtsViewModel,// = hiltViewModel(),
    analyticsHelper: AnalyticsHelper,
    navController: NavController,
    //navController: NavController, // Para la navegación

) {
    //val viewModelTts: TtsViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    universalisResource.data
    val typusId = universalisResource.id
    val userData = universalisResource.dynamic
    val subTitle = when (universalisResource.id) {
        -1 -> {
            "Error"
        }

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
                title = universalisResource.metaData.liturgia,
                subtitle = subTitle,
                onReaderClick = {
                    val read = universalisBodyForRead(universalisResource)
                    val textToSpeak = read.text

                    if (textToSpeak.isNotBlank()) {
                        analyticsHelper.logUniversalisTtsEvent(universalisResource.title) // Tu log

                        // 1. Carga el texto en el ViewModel compartido
                        //    Asume que loadAndPlayText también podría iniciar la reproducción
                        //    o al menos preparar el motor TTS.
                        viewModelTts.loadAndPlayText(textToSpeak)

                        // 2. Navega a la pantalla TTS usando la ruta tipada
                        //navController.to("reader_graph")
                        navController.navigateToTts()
                    } else {
                        // Manejar caso de texto vacío si es necesario
                        Log.w("ReaderButton", "Texto para leer está vacío.")
                        // Podrías mostrar un Snackbar o Toast
                    }
                },
                /*onReaderClick = {
                    showBottomSheet = true
                },*/
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
                        if (universalisResource.id == -1) {
                            ErrorState("")
                        } else {
                            //universalisResource.data.liturgia.liturgiaTypus
                            UniversalisResourceCardExpanded(
                                resource = universalisResource,
                                typusId = typusId,
                                userData = userData
                            )
                        }
                    }
                }
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,

                content = {
                    analyticsHelper.logUniversalisTtsEvent(universalisResource.title)
                    universalisBodyForRead(universalisResource)
                    //viewModelTts.loadData(read.text)
                    //ScreenTtsPlayer(viewModelTts)
                },
            ) //{
            //Timber.d("aaa-",plainTextFromHTML.text)
//Text(plainTextFromHTML.text)
            //ScreenTtsPlayer(viewModelTts)
            //}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoDataScaffold(
    title: String,
    uiState: UniversalisUiState.UniversalisError,
    onBackClick: () -> Unit,
) {
    rememberScrollState()

    Scaffold(
        topBar = {
            UniversalisSingleAppBar(
                title = title,
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
                .padding(30.dp)
                .fillMaxSize()
                .testTag("universalis:empty"),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //UniversalisEmptyScreen()
            //val text=uiState.

            TextError(
                buildString {
                    append(uiState.message)
                    append("\n\nFecha: ${uiState.date}")
                    append("\n\nLocalización: ${uiState.topic}")
                }
            )


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










