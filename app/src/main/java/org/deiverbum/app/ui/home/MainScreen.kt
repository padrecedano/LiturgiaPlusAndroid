@file:OptIn(ExperimentalLayoutApi::class)

package org.deiverbum.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SubdirectoryArrowRight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deiverbum.app.model.LiturgiaItem

@ExperimentalMaterial3Api
@Composable
//fun MainScreen(gameViewModel: InitialSyncViewModel = viewModel()) {
fun MainScreen(
    //orderUiState: TodayItemUiState,
    //onComposing: (AppBarState) -> Unit,
    //onNextButtonClicked: (String) -> Unit
    onNextButtonClicked: (String) -> Unit
) {

    val listA =
        listOf(LiturgiaItem(0, "Lecturas Oficio+Laudes", Icons.Rounded.SubdirectoryArrowRight))
    val listB = listOf(
        LiturgiaItem(1, "Oficio", Icons.Rounded.SubdirectoryArrowRight),
        LiturgiaItem(2, "Laudes", Icons.Rounded.SubdirectoryArrowRight),

        )
    val listC = listOf(
        LiturgiaItem(3, "Tercia", Icons.Rounded.SubdirectoryArrowRight),
        LiturgiaItem(4, "Sexta", Icons.Rounded.SubdirectoryArrowRight),
        LiturgiaItem(5, "Nona", Icons.Rounded.SubdirectoryArrowRight),

        )
    val listD = listOf(
        LiturgiaItem(6, "Vísperas", Icons.Rounded.SubdirectoryArrowRight),
        LiturgiaItem(7, "Completas", Icons.Rounded.SubdirectoryArrowRight),

        )
    val listBreviario = listOf(
        ChildItemUI("a", listA),
        ChildItemUI("b", listB),
        ChildItemUI("c", listC),
        ChildItemUI("d", listD),

        )
    val a = MainItemUI("Breviario", listBreviario)

    val mainItemUIs = listOf(a)


    val chipModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(8.dp))
    //   .background(Color.Green)

    val rowModifier = Modifier
        //.padding(8.dp)
        .padding(horizontal = 8.dp, vertical = 12.dp)
        .heightIn(max = 500.dp)
        .clip(RoundedCornerShape(8.dp))

    val colModifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Color.Red)
    LazyColumn(modifier = rowModifier) {
        items(mainItemUIs) { item ->
            if (item.parent == "Breviario") {
                Text(item.parent, fontSize = 26.sp)
                //FlowRow(modifier = rowModifier) {
                item.childs.forEach {
                    if (it.group == "a") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                MainButton(it) { onNextButtonClicked(it.title) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "b") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                MainButton(it) { onNextButtonClicked(it.title) }

                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "c") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                MainButton(it) { onNextButtonClicked(it.title) }

                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "d") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                MainButton(it) { onNextButtonClicked(it.title) }

                                Spacer(modifier = chipModifier)
                            }
                        }
                    }

                    Divider()
                }
            } else {


                //if (item.parent == "Misa") {
                Text(item.parent, fontSize = 26.sp)
                //FlowRow(modifier = rowModifier) {
                item.childs.forEach {
                    //    if(it.group=="a") {
                    FlowRow(modifier = rowModifier) {
                        it.items.forEach {
                            //assistChip(it.title)
                            MainButton(it) { onNextButtonClicked(it.title) }

                            Spacer(modifier = chipModifier)
                        }
                    }
                    //    }


                    Divider()
                }
            }

            /*if (item.parent == "Traditio") {
                Text(item.parent, fontSize = 26.sp)
                FlowRow(modifier = rowModifier) {
                    item.childs.forEach {
                        //assistChip(it)
                        Spacer(modifier = chipModifier)
                    }
                    Divider()
                }
            }*/
        }
    }

    //assistChip(num = "Lorem ipsum")
}


/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@ExperimentalMaterial3Api
@Composable
fun MainButton(
    itemUI: LiturgiaItem,
    onClick: () -> Unit
    //modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = { onClick() },
        //onClick = { click() },
        leadingIcon = {
            Icon(
                Icons.Rounded.SubdirectoryArrowRight,
                contentDescription = ""
            )
        },
        label = { Text(itemUI.title) }
        //modifier = modifier.widthIn(min = 250.dp)
    )
}


data class MainItemUI(var parent: String, var childs: List<ChildItemUI>) {}
data class ChildItemUI(var group: String, var items: List<LiturgiaItem>) {}