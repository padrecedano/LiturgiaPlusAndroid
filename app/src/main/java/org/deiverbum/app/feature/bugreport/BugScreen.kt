package org.deiverbum.app.feature.bugreport

import LPlusIcons
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.NiaButton
import org.deiverbum.app.core.model.data.BugItem
import org.deiverbum.app.core.model.data.BugReport
import org.deiverbum.app.feature.universalis.UniversalisUiState
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Constants
import timber.log.Timber

@ExperimentalFoundationApi
@Composable
fun BugScreen(
    modifier: Modifier = Modifier,
    bugViewModel: BugViewModel = hiltViewModel(),
    onBackClick: () -> Unit,

    ) {
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var messageSent by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            Timber.d("aaa", it.resultCode)
            messageSent = true
        }
    )
    val bugItems by bugViewModel.bugItems.collectAsStateWithLifecycle()

    val message = remember { mutableStateOf("") }

    val bugReport = remember {
        mutableStateOf(BugReport(message.value, bugItems))
    }

    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            GenericToolbar(title = "T")
            /*UniversalisResourceTitle(
                "Bug Report",
                userData = userData,
                modifier = Modifier.fillMaxWidth((.8f)),
            )*/
            Spacer(modifier = Modifier.weight(1f))
            if (!messageSent) {
                Text(
                    text = stringResource(id = R.string.bug_intro),
                    modifier = Modifier.padding(10.dp)
                )

                Text(text = stringResource(id = R.string.bug_description))
                TextFieldBugDescription(
                    message = message.value,
                    onMessageChanged = { newMessage ->
                        message.value = newMessage
                    }
                )
                Text(text = stringResource(id = R.string.bug_check))
                LazyVerticalGrid(
                    modifier = Modifier.heightIn(max = 1000.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(bugReport.value.bugItems.size) { index ->
                            Row(
                                modifier = Modifier.padding(1.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = bugItems[index].checked,
                                    onCheckedChange = { checked ->
                                        bugViewModel.updateBugItem(bugItems[index], checked)
                                    }
                                )
                                Text(
                                    text = bugItems[index].title,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                )
                Text(
                    text = stringResource(id = R.string.bug_send),
                    modifier = Modifier.padding(10.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    NiaButton(
                        onClick = //bugViewModel::send
                        {
                            val checkedTitles = bugItems
                                .filter(BugItem::checked)
                                .joinToString { it.title }
                            val msg = "Mensaje: \n\n ${message.value}\n\nEn:\n\n ${checkedTitles}."

                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                //addCategory(Intent.CATEGORY_APP_EMAIL)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                data = Uri.parse("mailto:")
                                putExtra(Intent.EXTRA_EMAIL, arrayOf(Configuration.MY_EMAIL))
                                putExtra(Intent.EXTRA_SUBJECT, Constants.ERR_SUBJECT)
                                putExtra(Intent.EXTRA_TEXT, msg)
                            }


                            launcher.launch(intent)
                            //sendEmail(launcher, BugReport(message.value, bugItems))
                        },
                        text = { Text(text = stringResource(R.string.enviar_email)) },
                        leadingIcon = {
                            Icon(
                                imageVector = LPlusIcons.Email,
                                contentDescription = stringResource(R.string.enviar_email)
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .widthIn(364.dp)
                            .fillMaxWidth(),
                    )
                }
            } else {
                Text(text = "Error reportado.")

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Mail(
    message: String,

    ) {
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var messageSent by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            Timber.d("aaa", it.resultCode)
            messageSent = true
        }
    )

    val messagee = remember { mutableStateOf("") }




    NiaButton(
        onClick = //bugViewModel::send
        {

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                //addCategory(Intent.CATEGORY_APP_EMAIL)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(Configuration.MY_EMAIL))
                putExtra(Intent.EXTRA_SUBJECT, Constants.ERR_SUBJECT)
                putExtra(Intent.EXTRA_TEXT, message)
            }


            launcher.launch(intent)
            //sendEmail(launcher, BugReport(message.value, bugItems))
        },
        text = { Text(text = stringResource(R.string.enviar_email)) },
        leadingIcon = {
            Icon(
                imageVector = LPlusIcons.Email,
                contentDescription = stringResource(R.string.enviar_email)
            )
        },
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .widthIn(364.dp)
            .fillMaxWidth(),
    )
}


@Composable
fun TextFieldBugDescription(message: String, onMessageChanged: (String) -> Unit) {
    TextField(
        value = message,
        onValueChange = onMessageChanged,
        label = { Text(stringResource(id = R.string.bug_label)) },
        maxLines = 2,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(200.dp)
    )
}

//@Composable
fun sendEmail(
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    bugReport: BugReport,
) {
    val checkedTitles = bugReport.bugItems.filter(BugItem::checked).joinToString()
    val msg = "aaa" //"Mensaje: \n\n ${bugReport.message}\n\nEn:\n\n $checkedTitles"
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_APP_EMAIL)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    }
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(Configuration.MY_EMAIL))
    intent.putExtra(Intent.EXTRA_SUBJECT, Constants.ERR_SUBJECT)
    intent.putExtra(Intent.EXTRA_TEXT, msg)
    launcher.launch(intent)
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

@Composable
fun GenericToolbar(
    title: String,
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


