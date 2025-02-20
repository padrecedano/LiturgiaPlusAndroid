package org.deiverbum.app.feature.file

import LPlusIcons
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.core.designsystem.component.UniversalisSingleAppBar
import org.deiverbum.app.core.designsystem.component.ZoomableText
import org.deiverbum.app.core.designsystem.theme.LocalCustomColorsPalette
import org.deiverbum.app.core.ui.bookRender
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.calendar.ErrorState
import org.deiverbum.app.feature.universalis.LoadingState
import org.deiverbum.app.util.Configuration
import timber.log.Timber

/**
 * Pantalla para mostrar contenido proveniente de archivos locales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 */

@ExperimentalFoundationApi
@Composable
fun FileScreen(
    modifier: Modifier = Modifier,
    viewModel: FileViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val rubricColor = LocalCustomColorsPalette.current.rubricColor
    FileScreen(
        onBackClick = onBackClick,
        modifier = modifier,
        uiState = uiState,
        rubricColor = rubricColor
    )
}

@ExperimentalFoundationApi
@Composable
internal fun FileScreen(
    modifier: Modifier,
    uiState: FileViewModel.FileUiState,
    rubricColor: Color,
    onBackClick: () -> Unit
) {
    when (uiState) {
        FileViewModel.FileUiState.Empty -> {
            EmptyState()
        }

        is FileViewModel.FileUiState.Error -> {
            ErrorState("")
        }

        is FileViewModel.FileUiState.Loaded -> {
            FileScaffold(
                uiState = uiState.itemState,
                rubricColor = rubricColor,
                onBackClick = { onBackClick() },
            )
        }

        FileViewModel.FileUiState.Loading -> {
            LoadingState()
        }
    }
}

/**
 * Scaffold para archivos locales. Mostrará el icono de eMail cuando sea necesario.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FileScaffold(
    onBackClick: () -> Unit,
    uiState: FileItemUiState,
    rubricColor: Color,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            Timber.d("aaa", it.resultCode)
        }
    )
    val scrollState = rememberScrollState()
    val new = uiState.allData.new.data[0]
    Scaffold(
        topBar = {
            UniversalisSingleAppBar(
                title = new.shortTitle,
                navigationIcon = LPlusIcons.ArrowBack,
                readerIcon = LPlusIcons.Reader,
                calendarIcon = LPlusIcons.Calendar,
                navigationIconContentDescription = "Navigation icon",
                actionIcon = LPlusIcons.MoreVert,
                actionIconContentDescription = "Action icon",
                onNavigationClick = { onBackClick() }
            )
        },

        floatingActionButton = {
            if ((new.bookType == 21) || (new.bookType == 20) || (new.bookType == 3)) {
                FloatingActionButton(onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        //addCategory(Intent.CATEGORY_APP_EMAIL)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(Configuration.MY_EMAIL))
                        putExtra(Intent.EXTRA_SUBJECT, "Sobre ${new.title}")
                    }
                    launcher.launch(intent)

                }) {
                    Icon(LPlusIcons.Email, contentDescription = "Enviar eMail")
                }
            }
        }
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
                            .padding(innerPadding),
                        //.verticalScroll(state = scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp)

                    ) {
                        val text = bookRender(
                            new,
                            1,
                            userData = uiState.allData.userData.dynamic,
                            rubricColor
                        )
                        ZoomableText(text, uiState.allData.userData)

                        //Text(text)
                        /*if ((new.bookType == 21) || (new.bookType == 20)) {
                            Mail("Sobre ${new.title}")
                        }*/
                    }
                }
            }
        }
    }
}

