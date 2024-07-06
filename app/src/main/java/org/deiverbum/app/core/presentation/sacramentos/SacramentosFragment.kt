package org.deiverbum.app.core.presentation.sacramentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.R


@AndroidEntryPoint
class SacramentosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SimpleScreen()
            }
        }
    }

    @Composable
    fun SimpleScreen() {
        Column(Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.terms_conditions),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.app_version_and_name),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(R.string.title_fragment_biblia),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { /* Handle click */ }, Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.accept))
            }
        }
    }
}


/*
class SacramentosFragment : BaseHomeFragment<ComposeSacramentosBinding>() {
    private lateinit var mAdapter: SacramentosParentAdapter
    private lateinit var parentList: List<SacramentosParent>
    private lateinit var childList: List<SacramentoItem>

    override fun constructViewBinding(): ViewBinding =
        ComposeSacramentosBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        prepareItems()
        initUi()
    }

    private fun initUi() {

getViewBinding().composeView.apply {
    // Dispose the Composition when viewLifecycleOwner is destroyed
    setViewCompositionStrategy(
        ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
    )
    setContent {
        // In Compose world
        MaterialTheme {
            Text("Hello Compose*!")
        }
    }
}


        val composeView = getViewBinding().composeView
        composeView.setContent {
            LazyColumn(Modifier.fillMaxSize()) {
                // We use a LazyColumn since the layout manager of the RecyclerView is a vertical LinearLayoutManager

            }
        }

        /*getViewBinding().composeView.setContent {
            LazyColumn(Modifier.fillMaxSize()) {
                // We use a LazyColumn since the layout manager of the RecyclerView is a vertical LinearLayoutManager
            }
        }*/
        /*getViewBinding().recyclerView.run {
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.subtitle = ""
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }*/
    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name!")
    }

    private fun prepareItems() {
        parentList = ArrayList()
        childList = ArrayList()
        val parentList: List<SacramentosParent> = listOf(
            SacramentosParent(
                "Bautismo",
                listOf(
                    SacramentoItem("Bautismo", "Normativa canónica", CIC_BAPTISMUS),
                    SacramentoItem("Bautismo", "En peligro de muerte", FILE_BAPTISMUS),
                )
            ),
            SacramentosParent(
                "Unción de los Enfermos",
                listOf(
                    SacramentoItem("Unción", "Normativa canónica", CIC_UNCTIONIS),
                    SacramentoItem("Unción", "Rito ordinario", UNCTIONIS_ORDINARIUM),
                    SacramentoItem("Unción", "Viático fuera de la Misa", FILE_VIATICUM),
                    SacramentoItem(
                        "Unción",
                        "En inmediato peligro de muerte",
                        FILE_UNCTIONIS_ARTICULO_MORTIS
                    ),
                    SacramentoItem(
                        "Unción",
                        "Sin Viático",
                        FILE_UNCTIONIS_SINE_VIATICUM
                    ),
                    SacramentoItem(
                        "Unción",
                        "Cuando se duda si el enfermo vive",
                        FILE_UNCTIONIS_IN_DUBIO
                    ),//FILE_COMMENDATIONE_MORIENTIUM
                    SacramentoItem(
                        "Unción",
                        "Recomendación del Alma",
                        FILE_COMMENDATIONE_MORIENTIUM
                    ),
                    )
            )
        )

        mAdapter = SacramentosParentAdapter(parentList)

    }
}*/