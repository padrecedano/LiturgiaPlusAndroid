package org.deiverbum.app.core.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidViewBinding
import org.deiverbum.app.R
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.presentation.today.TodayViewModel
import org.deiverbum.app.databinding.FragmentTodayBinding


/**
 * Composable that allows the user to select the desired cupcake quantity and expects
 * [onNextButtonClicked] lambda that expects the selected quantity and triggers the navigation to
 * next screen
 */
@ExperimentalMaterial3Api
@Composable
fun UniversalisScreen(
    //orderUiState: OrderUiState,
    //onComposing: (AppBarState) -> Unit,
    canNavigateBack: Boolean,


    viewModel: TodayViewModel,
    quantityOptions: List<Pair<Int, Int>>,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    /*
        LaunchedEffect(key1 = true) {
            onComposing(
                AppBarState(
                    //title = "My Screen A*",
                    navigationIcon =

                    {
                        if(canNavigateBack){
                            androidx.compose.material3.IconButton(onClick = { /* do something */ }) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        }},

                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null
                            )
                        }
                    }
                )
            )
        }
    */

    var state by remember { mutableStateOf(3) }

    val titles =
        listOf("Mixto", "Oficio", "Laudes", "Tercia", "Sexta", "Nona", "Vísperas", "Completas")
    Column {
        PrimaryScrollableTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
                )
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(
                text = "Primary tab ${state + 1} selected",
                style = MaterialTheme.typography.headlineSmall
            )

            when (val state = uiState.value) {
                is TodayViewModel.TodayUiState.Empty -> Text(text = "Vacío")
                is TodayViewModel.TodayUiState.Error -> Text(text = state.message)
                is TodayViewModel.TodayUiState.Loaded -> {
                    val tr = TodayRequest(0, 1, false, false)
                    /*val annotatedString = buildAnnotatedString {
                        append(state.itemState.todayResponse.dataModel.getAllForView())

                    }*/
                    val s = state.itemState.todayResponse.dataModel.getAllForView(tr)

                    AndroidViewBinding(FragmentTodayBinding::inflate) {
                        tvZoomable.text = s
                    }
                    //Text(text = s.toString())
                }

                TodayViewModel.TodayUiState.Loading -> CircularProgressIndicator()
            }

            /*Text(
                text = "orderUiState",
                style = MaterialTheme.typography.headlineSmall
            )*/
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        }
        /*Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            quantityOptions.forEach { item ->
                SelectQuantityButton(
                    labelResourceId = item.first,
                    onClick = { onNextButtonClicked(item.second) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } */
    }
}


/*
@Preview
@Composable
fun StartOrderPreview() {
    CupcakeTheme {
        StartOrderScreen(
            quantityOptions = DataSource.quantityOptions,
            onNextButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}*/