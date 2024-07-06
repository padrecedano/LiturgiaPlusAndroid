@file:OptIn(ExperimentalLayoutApi::class)

package org.deiverbum.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deiverbum.app.core.model.data.ui.ItemUI
import org.deiverbum.app.core.model.data.ui.ItemUICollection

@Composable
//fun MainScreen(gameViewModel: InitialSyncViewModel = viewModel()) {
fun HomeScreenBody(
    onNextButtonClicked: (String) -> Unit
    //onNextButtonClicked: @Composable (Int) -> Unit,


) {

    val click = onNextButtonClicked
    var breviariumItems = listOf(ItemUI(0, "Laudes + Lecturas Oficio", 1, ""))
    var screenItems = ItemUICollection("Breviario", breviariumItems)
    val a = ChildItem("a", listOf(ItemUI(0, "Laudes + Lecturas Oficio", 1, "")))
    val b = ChildItem(
        "b", listOf
            (
            ItemUI(1, "Oficio", 2, ""),
            ItemUI(2, "Laudes", 2, "")
        )
    )
    val c = ChildItem(
        "c",
        listOf(
            ItemUI(3, "Tercia", 2, ""),
            ItemUI(4, "Sexta", 2, ""),
            ItemUI(5, "Nona", 2, "")
        )
    )
    val d = ChildItem(
        "d",
        listOf(
            ItemUI(6, "Vísperas", 2, ""),
            ItemUI(7, "Completas", 2, ""),
        )
    )

    val e = ChildItem(
        "e",
        listOf(
            ItemUI(8, "Comentarios 8", 2, ""),
            ItemUI(9, "Homilías 9", 2, ""),
            ItemUI(10, "Lecturas 10", 2, ""),

            )
    )
    /*    val b=ChildItem("b",listOf("Oficio","Laudes"))
    val c=ChildItem("c",listOf("Tercia","Sexa","Nona"))
    val d=ChildItem("d",listOf("Vísperas","Completas"))
    val e=ChildItem("a",listOf("Lecturas","Comentarios","Homilías","Santos"))
    val f=ChildItem("a",listOf("Santo del Día","Oraciones","Calendario"))
    val g=ChildItem("a",listOf("Bautismo","Unción Enfermos"))
*/
    val mainItems = listOf(
        //MainItem("Sacramentos", listOf(g)
        MainItem("Breviario", listOf(a, b, c, d)),
        MainItem("Misa de hoy", listOf(e)),

        /*MainItem("Misa de hoy", listOf(e)) ,
        MainItem("Más ...", listOf(f)),
        MainItem("Sacramentos", listOf(g))*/
    )
    //flowColl(mainItems, { onNextButtonClicked() })
    //flowColl(mainItems)

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
        items(mainItems) { item ->
            if (item.parent == "Breviario") {
                Text(item.parent, fontSize = 26.sp)
                //FlowRow(modifier = rowModifier) {
                item.childs.forEach {
                    if (it.group == "a") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                SelectQuantityButton(it) { onNextButtonClicked(it.title) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "b") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                SelectQuantityButton(it) { onNextButtonClicked(it.title) }

                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "c") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                SelectQuantityButton(it) { onNextButtonClicked(it.title) }

                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "d") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                //assistChip(it)
                                SelectQuantityButton(it) { onNextButtonClicked(it.title) }

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
                            SelectQuantityButton(it) { onNextButtonClicked(it.title) }

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


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun assistChip(num: String) {
    AssistChip(
        onClick = {
            //onNextButtonClicked(3)
        },
        label = { Text(num) },
        leadingIcon = {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Localized description",
                Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
}


/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun SelectQuantityButton(
    itemUI: ItemUI,
    onClick: () -> Unit
    //modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = { onClick() },
        //onClick = { click() },
        label = { Text(itemUI.title) }
        //modifier = modifier.widthIn(min = 250.dp)
    )
}


data class MainItem(var parent: String, var childs: List<ChildItem>) {}
data class ChildItem(var group: String, var items: List<ItemUI>) {}






