package org.deiverbum.app.feature.file

import LPlusIcons
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.core.designsystem.component.TextBase
import org.deiverbum.app.core.designsystem.component.TextBiblicaBrevis
import org.deiverbum.app.core.designsystem.component.TextBiblicaLonga
import org.deiverbum.app.core.designsystem.component.TextCanon
import org.deiverbum.app.core.designsystem.component.TextCanonWithList
import org.deiverbum.app.core.designsystem.component.TextError
import org.deiverbum.app.core.designsystem.component.TextFromList
import org.deiverbum.app.core.designsystem.component.TextHead
import org.deiverbum.app.core.designsystem.component.TextLiberBiblical
import org.deiverbum.app.core.designsystem.component.TextLiberDialog
import org.deiverbum.app.core.designsystem.component.TextLiberMixtus
import org.deiverbum.app.core.designsystem.component.TextLiberMixtusB
import org.deiverbum.app.core.designsystem.component.TextLiberOratio
import org.deiverbum.app.core.designsystem.component.TextLiberOratioo
import org.deiverbum.app.core.designsystem.component.TextLiberPriest
import org.deiverbum.app.core.designsystem.component.TextParagraphusDialog
import org.deiverbum.app.core.designsystem.component.TextParagraphusMixtus
import org.deiverbum.app.core.designsystem.component.TextParagraphusOratio
import org.deiverbum.app.core.designsystem.component.TextParagraphusPreces
import org.deiverbum.app.core.designsystem.component.TextParagraphusPriest
import org.deiverbum.app.core.designsystem.component.TextRationarium
import org.deiverbum.app.core.designsystem.component.TextResponsum
import org.deiverbum.app.core.designsystem.component.TextRubrica
import org.deiverbum.app.core.designsystem.component.TextRubricaNumerus
import org.deiverbum.app.core.designsystem.component.TextVersiculusResponsum
import org.deiverbum.app.core.designsystem.component.UniversalisSingleAppBar
import org.deiverbum.app.core.designsystem.component.textFromList
import org.deiverbum.app.core.designsystem.component.textIndent
import org.deiverbum.app.core.designsystem.theme.LocalCustomColorsPalette
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.alteri.Rosarium
import org.deiverbum.app.core.model.book.Canon
import org.deiverbum.app.core.model.book.CanonWithList
import org.deiverbum.app.core.model.book.IurisChapter
import org.deiverbum.app.core.model.book.LiberBase
import org.deiverbum.app.core.model.book.LiberBaseA
import org.deiverbum.app.core.model.book.LiberBaseC
import org.deiverbum.app.core.model.book.LiberBiblical
import org.deiverbum.app.core.model.book.LiberDialog
import org.deiverbum.app.core.model.book.LiberError
import org.deiverbum.app.core.model.book.LiberMixtus
import org.deiverbum.app.core.model.book.LiberMixtusB
import org.deiverbum.app.core.model.book.LiberOratio
import org.deiverbum.app.core.model.book.LiberOratioo
import org.deiverbum.app.core.model.book.LiberPreces
import org.deiverbum.app.core.model.book.LiberSacramentumNew
import org.deiverbum.app.core.model.book.LiberSection
import org.deiverbum.app.core.model.book.ParagraphusBase
import org.deiverbum.app.core.model.book.ParagraphusBiblicaBrevis
import org.deiverbum.app.core.model.book.ParagraphusBiblicaLonga
import org.deiverbum.app.core.model.book.ParagraphusDialog
import org.deiverbum.app.core.model.book.ParagraphusDivider
import org.deiverbum.app.core.model.book.ParagraphusMixtus
import org.deiverbum.app.core.model.book.ParagraphusOratio
import org.deiverbum.app.core.model.book.ParagraphusPreces
import org.deiverbum.app.core.model.book.ParagraphusPriest
import org.deiverbum.app.core.model.book.ParagraphusRationarium
import org.deiverbum.app.core.model.book.ParagraphusResponsum
import org.deiverbum.app.core.model.book.ParagraphusRubricaNew
import org.deiverbum.app.core.model.book.ParagraphusRubricaNumerus
import org.deiverbum.app.core.model.book.ParagraphusVersiculusResponsum
import org.deiverbum.app.core.model.book.Priest
import org.deiverbum.app.core.model.book.Rubrica
import org.deiverbum.app.core.model.book.Title
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.ui.ContentHeadd
import org.deiverbum.app.core.ui.ContentTitle
import org.deiverbum.app.core.ui.contentSpace
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.calendar.ErrorState
import org.deiverbum.app.feature.home.HomeViewModel
import org.deiverbum.app.feature.universalis.LoadingState
import timber.log.Timber

@ExperimentalFoundationApi
@Composable
fun FileScreenn(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val rubricColor = LocalCustomColorsPalette.current.rubricColor
    Text(LoremIpsum().values.first())
}

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
@OptIn(ExperimentalMaterial3Api::class)
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

    val new = uiState.allData.new.data
    val userData = uiState.allData.userData
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge
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
            /*if ((new.typus == 21) || (new.bookType == 20) || (new.bookType == 3)) {
                FloatingActionButton(onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        //addCategory(Intent.CATEGORY_APP_EMAIL)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(Configuration.MY_EMAIL))
                        //TODO:putExtra(Intent.EXTRA_SUBJECT, "Sobre ${new.title}")
                    }
                    launcher.launch(intent)

                }) {
                    Icon(LPlusIcons.Email, contentDescription = "Enviar eMail")
                }
            }*/
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
                            .padding(innerPadding)
                            .verticalScroll(state = scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp)

                    ) {
                        RenderLiber(new, userData, bodyStyle)
                    }
                }
            }
        }
    }
}

@Composable
fun RenderLiber(liber: LiberBase, userData: UserData, bodyStyle: TextStyle) {
    when (liber) {
        is LiberError -> TextError(liber.msg)
        is LiberBaseA -> {
            RenderIuris(liber, userData, bodyStyle)
        }

        is LiberBaseC -> {
            RenderLiberC(liber, userData, bodyStyle)
        }

        is LiberSacramentumNew -> {
            RenderSacramentum(liber, userData, bodyStyle)
        }
    }
}

@Composable
fun RenderLiberC(liber: LiberBaseC, userData: UserData, style: TextStyle) {
    //ContentLabel(liber.title,1,userData)
    if (liber.shortTitle == "Letanías") {
        ContentHeadd(
            text = liber.title,
            level = 2,
            userData = userData,
            uppercase = false,
            withColor = false
        )
        val litanie = buildAnnotatedString {
            Rosarium.LITANIAE.forEach {
                append(textIndent(it.textus, it.responsum, MaterialTheme.colorScheme.error))
                append(contentSpace(4))
            }
        }
        Text(text = litanie, style = style)
        ContentTitle("Oración", 1, userData, false)

        Text(text = textFromList(Rosarium.oratio.textus), style = style)


    } else {
    ContentHeadd(
        text = liber.title,
        level = 2,
        userData = userData,
        uppercase = false,
        withColor = false
    )
    ContentHeadd(
        text = liber.subTitle,
        level = 3,
        userData = userData,
        uppercase = false,
        withColor = false
    )
    liber.sections.forEach {
        if (it.head.type != "blank") {
            TextHead(it.head, userData)
        }
        it.paragraphus.forEach {
            when (it) {
                is ParagraphusDivider -> HorizontalDivider(
                    color = MaterialTheme.colorScheme.error,
                    thickness = 1.dp
                )

                is ParagraphusDialog -> {
                    TextParagraphusDialog(it, style)
                }

                is ParagraphusPreces -> {
                    TextParagraphusPreces(it, style)
                }

                is ParagraphusMixtus -> {
                    TextParagraphusMixtus(it, style)
                }

                is ParagraphusResponsum -> {
                    TextResponsum(it, style)
                }

                is ParagraphusOratio -> {
                    TextParagraphusOratio(it, style)
                }

                is ParagraphusPriest -> {
                    TextParagraphusPriest(it, style)
                }

                is ParagraphusRubricaNumerus -> {
                    TextRubricaNumerus(it, style)
                }

                is ParagraphusRubricaNew -> {
                    TextRubrica(it, style)
                }

                is ParagraphusVersiculusResponsum -> {
                    TextVersiculusResponsum(it, style)
                }

                is ParagraphusBiblicaBrevis -> {
                    TextBiblicaBrevis(it, style)
                }

                is ParagraphusBiblicaLonga -> {
                    TextBiblicaLonga(it, style)
                }

                is ParagraphusRationarium -> TextRationarium(it, style)
                is ParagraphusBase -> {
                    TextBase(it, style)
                }
            }
        }
    }
    }
}

@Composable
fun RenderSacramentum(liber: LiberSacramentumNew, userData: UserData, bodyStyle: TextStyle) {
    Text(liber.title)
    liber.contents.forEach {
        when (it) {
            is LiberSection -> {
                ContentTitle(it.title, 1, userData, false)
                if (it.subTitle != "") ContentTitle(it.subTitle, 2, userData, false)
                it.contents.forEach {
                    when (it) {
                        //is RubricaNumerus -> TextRubricaNumerus(it, bodyStyle, 3)
                        is Rubrica -> TextFromList(it.txt, bodyStyle, true)
                        is Priest -> TextLiberPriest(it, bodyStyle)
                        is LiberOratio -> TextLiberOratio(it, bodyStyle)
                        is LiberOratioo -> TextLiberOratioo(it, bodyStyle)

                        is LiberBiblical -> TextLiberBiblical(it, bodyStyle)
                        is LiberPreces -> TextLiberDialog(it, bodyStyle)
                        is LiberDialog -> TextLiberDialog(it, bodyStyle)
                        is LiberMixtusB -> TextLiberMixtusB(it, bodyStyle)
                        is LiberMixtus -> TextLiberMixtus(it, bodyStyle)

                    }
                }
            }
        }
    }
}

@Composable
fun RenderIuris(liber: LiberBaseA, userData: UserData, bodyStyle: TextStyle) {
    Text(liber.typus)
    liber.contents.forEach {
        when (it) {
            is Title -> {
                ContentTitle(
                    "Título ${it.n}. ${it.title}",
                    1,
                    userData,
                    false
                )
                it.contents.forEach {
                    when (it) {
                        is IurisChapter -> {
                            ContentTitle(
                                "${it.n}. ${it.title}",
                                2,
                                userData,
                                false,
                                false
                            )
                            it.contents.forEach {
                                when (it.type) {
                                    "canonWithList" -> TextCanonWithList(
                                        it as CanonWithList,
                                        bodyStyle
                                    )

                                    "canon" -> TextCanon(
                                        it as Canon,
                                        bodyStyle
                                    )

                                }
                            }
                        }

                        is Canon -> {
                            TextCanon(it, bodyStyle)
                        }
                        ///is->Canon{}
                    }
                }
            }
        }
    }

}

