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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.deiverbum.app.R
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.presentation.today.TodayViewModel
import org.deiverbum.app.databinding.FragmentTodayBinding
import org.deiverbum.app.util.LiturgyHelper


/**
 * Composable that allows the user to select the desired cupcake quantity and expects
 * [onNextButtonClicked] lambda that expects the selected quantity and triggers the navigation to
 * next screen
 */

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val startRoute = "example"
    NavHost(navController, startDestination = startRoute) {
        composable("example/{id}") { backStackEntry ->
            // Creates a ViewModel from the current BackStackEntry
            // Available in the androidx.hilt:hilt-navigation-compose artifact
            val viewModel = hiltViewModel<TodayViewModel>()
            MyScreen(viewModel, 20240304)
        }
        /* ... */
    }
}

@Composable
fun MyScreen(viewModel: TodayViewModel, theDate: Int) {
    val uiState = viewModel.uiState.collectAsState()

    val fecha = (20240301..20240321).random()
    viewModel.loadData(TodayRequest(theDate, 3, false, false))

    //Text("Lorem ipsum")
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


}

@Composable
fun Profile(
    navController: NavController,
    userId: String?
) {
    Text("${userId} Lorem ipsum")
}

@ExperimentalMaterial3Api
@Composable
fun TestScreen(
    //orderUiState: OrderUiState,
    //onComposing: (AppBarState) -> Unit,
    backStackEntry: NavBackStackEntry,

    //viewModel: TodayViewModel,
    onNextButtonClicked: @Composable (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    //val uiState = viewModel.uiState.collectAsState()
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

    var current = LiturgyHelper.liturgyByType(3)
    //val count: Int = LiturgyHelper.liturgyMap.getValue('laudes').toInt()
    val keys = LiturgyHelper.liturgyMap.get(2)// { it == "laudes" }.keys
    val id = backStackEntry.arguments?.getString("id")?.toInt()
    var state by remember { mutableStateOf(id) }

    val titles =
        listOf("Mixto", "Oficio", "Laudes", "Tercia", "Sexta", "Nona", "Vísperas", "Completas")
    Column {
        PrimaryScrollableTabRow(selectedTabIndex = state!!) {
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
                text = "Primary tab ${state?.plus(1)} selected",
                style = MaterialTheme.typography.headlineSmall
            )
            /*
                        when (val state = uiState.value) {
                            is TodayViewModel.TodayUiState.Empty -> Text(text = "Vacío")
                            is TodayViewModel.TodayUiState.Error -> Text(text = state.message)
                            is TodayViewModel.TodayUiState.Loaded -> {
                                val tr=TodayRequest(0,1,false,false)
                                /*val annotatedString = buildAnnotatedString {
                                    append(state.itemState.todayResponse.dataModel.getAllForView())

                                }*/
                                val s=state.itemState.todayResponse.dataModel.getAllForView(tr)

                                AndroidViewBinding(FragmentTodayBinding::inflate) {
                                    tvZoomable.text=s
                                }
                                //Text(text = s.toString())
                            }
                            TodayViewModel.TodayUiState.Loading -> CircularProgressIndicator()
                        }
                        */

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